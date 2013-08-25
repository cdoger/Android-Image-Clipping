package com.cd.clipping;

import android.graphics.PointF;

public class BezierPoint extends PointF {
	public static final int NORMAL_POINT = 0;
	public static final int CONTROL_POINT = 1;
	private static final int SENSITIVITY = 20;
	public float left;
	public float top;
	private float right;
	private float bottom;
	private boolean isSelected = false;

	public BezierPoint(float x, float y) {
		super(x, y);
		left = x - BezierCurve.RADIUS - SENSITIVITY;
		top = y - BezierCurve.RADIUS - SENSITIVITY;
		right = x + BezierCurve.RADIUS + SENSITIVITY;
		bottom = y + BezierCurve.RADIUS + SENSITIVITY;
	}

	public BezierPoint() {
	}

	public void move(float newX, float newY) {
		if (isSelected) {
			this.x = newX;
			this.y = newY;
			left = x - BezierCurve.RADIUS - SENSITIVITY;
			top = y - BezierCurve.RADIUS - SENSITIVITY;
			right = x + BezierCurve.RADIUS + SENSITIVITY;
			bottom = y + BezierCurve.RADIUS + SENSITIVITY;
		}
	}

	public boolean isSelected(float x, float y) {
		isSelected = x > left && x < right && y < bottom && y > top;
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
