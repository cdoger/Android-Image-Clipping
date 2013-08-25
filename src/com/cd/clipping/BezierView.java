package com.cd.clipping;

import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BezierView extends View {
	private static final String TAG = "BezierView";
	private float mLastTouchX = 0;
	private float mLastTouchY = 0;
	BezierCurve curve = new BezierCurve();
	BezierPoint point1 = new BezierPoint();
	BezierPoint point2 = new BezierPoint();
	BezierPoint control1 = new BezierPoint();
	CurveHolder curveHolder;
	private float mPosX;
	private float mPosY;
	private Paint paint;
	// private MultiplePath paths;
	private Path paths;
	private Paint mPaint;
	private boolean sthSelected = false;
	private Iterator<BezierCurve> iterator;
	private int mScreenWidth;
	private int mScreenHeight;
	private Bitmap imageToClip;
	private boolean curveEnabled = true;
	Paint dummyPaint;
	private Path dummyPath;

	public BezierView(final Context context) {
		super(context);
		int x1 = 200, y1 = 200;
		control1 = new BezierPoint(x1, y1);
		point2 = new BezierPoint(x1 + 100, y1 - 50);
		point1 = new BezierPoint(x1 - 40, y1 - 10);
		dummyPath = new Path();
		dummyPath.moveTo(point1.x, point1.y);
		dummyPath.quadTo(control1.x, control1.y, point2.x, point2.y);
		paint = new Paint();
		dummyPaint = new Paint();
		// paths = new MultiplePath();
		paths = new Path();
		imageToClip = BitmapFactory.decodeResource(getResources(),
				R.drawable.yellow_ball);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GREEN);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(2.5f);
		paint.setAntiAlias(true);
		post(new Runnable() {

			@Override
			public void run() {
				Rect hitRect = new Rect();
				getHitRect(hitRect);
				mScreenWidth = hitRect.right - hitRect.left;
				mScreenHeight = hitRect.bottom - hitRect.top;
				Log.i(TAG, "w, h" + mScreenWidth + " " + mScreenHeight);
				curveHolder = new CurveHolder(mScreenWidth, mScreenHeight,
						context);
				curveHolder.addPoints();
			}
		});
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(6);
		mPaint.setTextSize(16);
		mPaint.setTextAlign(Paint.Align.RIGHT);
	}

	public void resetPath() {
		curveHolder.curves.clear();
		curveHolder.addPoints();
		invalidate();
	}

	boolean first = true;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		if (isCurveEnabled()) {
			iterator = curveHolder.curves.iterator();
			paths.reset(); // Don't forget to reset the paths
			first = true;
			for (; iterator.hasNext();) {
				curve = iterator.next();
				if (first) {
					first = false;
					paths.moveTo(curve.point1.x, curve.point1.y);
				}
				paths.quadTo(curve.controlPoint.x, curve.controlPoint.y,
						curve.point2.x, curve.point2.y);
			}
			canvas.drawPath(paths, paint);
		}
		canvas.clipPath(paths);
		canvas.translate(mPosX, mPosY);
		if (imageToClip != null) {
			canvas.translate((mScreenWidth - imageToClip.getWidth()) / 2,
					(mScreenHeight - imageToClip.getHeight()) / 2);
			canvas.drawBitmap(imageToClip, 0, 0, dummyPaint);
		}
		canvas.restore();
		if (isCurveEnabled()) {
			iterator = curveHolder.curves.iterator();
			for (; iterator.hasNext();) {
				BezierCurve curve = (BezierCurve) iterator.next();
				canvas.drawCircle(curve.point1.x, curve.point1.y,
						BezierCurve.RADIUS, curve.normalPaint);
				canvas.drawCircle(curve.point2.x, curve.point2.y,
						BezierCurve.RADIUS, curve.normalPaint);
				canvas.drawCircle(curve.controlPoint.x, curve.controlPoint.y,
						BezierCurve.RADIUS, curve.controlPaint);
			}
		}
	}

	public boolean isCurveEnabled() {
		return curveEnabled;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getActionMasked();
		Iterator<BezierCurve> iterator = curveHolder.curves.iterator();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {

			final float x = ev.getX();
			final float y = ev.getY();
			mLastTouchX = x;
			mLastTouchY = y;
			for (; iterator.hasNext();) {
				BezierCurve curve = (BezierCurve) iterator.next();
				if (!sthSelected)
					sthSelected = curve.checkPoints(x, y);
				else {
					break;
				}
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			final float x = ev.getX();
			final float y = ev.getY();

			if (!sthSelected) {
				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;
				mPosX += dx;
				mPosY += dy;
			}
			for (; iterator.hasNext();) {
				BezierCurve curve = (BezierCurve) iterator.next();
				curve.move(x, y);
			}

			invalidate();
			mLastTouchX = x;
			mLastTouchY = y;
			break;
		}
		case MotionEvent.ACTION_UP:
			for (; iterator.hasNext();) {
				BezierCurve curve = (BezierCurve) iterator.next();
				curve.resetSelected();
			}
			sthSelected = false;
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}

	public void disableCurve() {
		setCurveEnabled(false);
		invalidate();
	}

	public void enableCurve() {
		setCurveEnabled(true);
		invalidate();
	}

	public void setCurveEnabled(boolean curveEnabled) {
		this.curveEnabled = curveEnabled;
	}

}
