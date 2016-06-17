package com.sveloso.pocketdoodle;

import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veloso on 6/15/2016.
 */
public class Line {

    private List<PointF> mPoints;
    private Paint mColor;

    public Line(PointF origin) {
        mPoints = new ArrayList<>();
        mPoints.add(origin);
    }

    public void addPoint(PointF newPoint) {
        mPoints.add(newPoint);
    }

    public List<PointF> getPoints() {
        return mPoints;
    }

    public Paint getColor() {
        return mColor;
    }

    public void setColor(Paint color) {
        this.mColor = color;
    }
}
