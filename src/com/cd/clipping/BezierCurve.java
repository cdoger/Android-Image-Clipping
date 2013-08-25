package com.cd.clipping;

import android.graphics.Color;
import android.graphics.Paint;

public class BezierCurve {
	public static final int RADIUS = 15;
	public BezierPoint point1;
	public BezierPoint point2;
	public BezierPoint controlPoint;
	// private MultiplePath path;
	public Paint controlPaint;
	public Paint normalPaint;

	public BezierCurve(BezierPoint point1, BezierPoint point2,
			BezierPoint controlPoint) {
		this.point1 = point1;
		this.point2 = point2;
		this.controlPoint = controlPoint;
		// path = new MultiplePath();
		normalPaint = new Paint();
		normalPaint.setColor(Color.argb(120, 0, 255, 0));
		normalPaint.setAntiAlias(true);
		controlPaint = new Paint();
		controlPaint.setColor(Color.argb(120, 255, 0, 0));
		controlPaint.setAntiAlias(true);
	}

	// public MultiplePath createPath() {
	// path.reset();
	// path.points.clear();
	// path.moveTo(point1.x, point1.y);
	// path.quadTo(controlPoint.x, controlPoint.y, point2.x, point2.y);
	// return path;
	// }
	// public Path createPath2() {
	// Path normalPath = new Path();
	// normalPath .reset();
	// normalPath .moveTo(point1.x, point1.y);
	// normalPath .quadTo(controlPoint.x, controlPoint.y, point2.x, point2.y);
	// return normalPath ;
	// }

	public boolean checkPoints(float x, float y) {
		boolean sthSelected = false;
		sthSelected = point1.isSelected(x, y);

		if (!sthSelected)
			sthSelected = point2.isSelected(x, y);
		if (!sthSelected)
			sthSelected = controlPoint.isSelected(x, y);

		return sthSelected;
	}

	public void move(float x, float y) {
		point1.move(x, y);
		point2.move(x, y);
		controlPoint.move(x, y);
	}

	public void resetSelected() {
		point1.setSelected(false);
		point2.setSelected(false);
		controlPoint.setSelected(false);
	}

	// public MultiplePath getPath() {
	// return path;
	// }

	public BezierCurve() {
	}

	// public void setPath() {
	// path.moveTo(point1.x, point1.y);
	// path.quadTo(controlPoint.x, controlPoint.y, point2.x, point2.y);
	// }

}
