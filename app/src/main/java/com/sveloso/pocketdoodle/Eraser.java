package com.sveloso.pocketdoodle;

import android.graphics.PointF;

/**
 * Created by Veloso on 6/17/2016.
 */
public class Eraser extends Shape{
    private PointF mOrigin;
    private PointF mCurrent;

    public Eraser (PointF origin) {
        mOrigin = new PointF(origin.x - 25, origin.y - 25);
        mCurrent = new PointF(origin.x + 25, origin.y + 25);
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

}