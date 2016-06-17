package com.sveloso.pocketdoodle;

import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Veloso on 6/16/2016.
 */
public class Box {

    private PointF mOrigin;
    private PointF mCurrent;
    private Paint mColor;


    public Box (PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public Paint getColor() {
        return mColor;
    }

    public void setColor(Paint color) {
        mColor = color;
    }
}
