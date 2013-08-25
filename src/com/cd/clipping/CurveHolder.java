package com.cd.clipping;

import java.util.LinkedList;

import android.content.Context;

public class CurveHolder {
	LinkedList<BezierCurve> curves = new LinkedList<BezierCurve>();
	private int screenWidth;
	private int screenHeight;

	public CurveHolder(int screenWidth, int screenHeight, Context context) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void addPoints() {
		// Upper middle
		final float x1 = screenWidth / 2;
		final float y1 = 30;
		BezierPoint control1 = new BezierPoint(x1, y1);
		BezierPoint point2 = new BezierPoint(x1 + 40, y1 - 10);
		BezierPoint point1 = new BezierPoint(x1 - 40, y1 - 10);
		// Upper right
		BezierPoint control2 = new BezierPoint(x1 + 150, 30);
		BezierPoint point3 = point2;
		BezierPoint point4 = new BezierPoint(19 * (screenWidth / 20),
				screenHeight / 2);
		// Bottom right
		BezierPoint control3 = new BezierPoint(19 * (screenWidth / 20),
				screenHeight - 30);
		BezierPoint point5 = point4;
		BezierPoint point6 = new BezierPoint(screenWidth / 2 + 40,
				screenHeight - 30);
		// Bottom middle
		BezierPoint control4 = new BezierPoint(screenWidth / 2,
				screenHeight - 30);
		BezierPoint point7 = point6;
		BezierPoint point8 = new BezierPoint(screenWidth / 2 - 40,
				screenHeight - 30);
		// Bottom left
		BezierPoint control5 = new BezierPoint(screenWidth / 20,
				screenHeight - 30);
		BezierPoint point9 = point8;
		BezierPoint point10 = new BezierPoint(30, screenHeight / 2);
		// Upper left
		BezierPoint control6 = new BezierPoint(x1 - 150, 30);
		BezierPoint point11 = point10;
		BezierPoint point12 = point1;

		BezierCurve c1 = new BezierCurve(point1, point2, control1);
		BezierCurve c2 = new BezierCurve(point3, point4, control2);
		BezierCurve c3 = new BezierCurve(point5, point6, control3);
		BezierCurve c4 = new BezierCurve(point7, point8, control4);
		BezierCurve c5 = new BezierCurve(point9, point10, control5);
		BezierCurve c6 = new BezierCurve(point11, point12, control6);
		curves.add(c1);
		curves.add(c2);
		curves.add(c3);
		curves.add(c4);
		curves.add(c5);
		curves.add(c6);

	}

}
