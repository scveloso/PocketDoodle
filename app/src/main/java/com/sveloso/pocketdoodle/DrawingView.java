package com.sveloso.pocketdoodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veloso on 6/15/2016.
 */
public class DrawingView extends View {

    private PocketDoodleManager mPocketDoodleManager;

    public DrawingView (Context context) {
        this(context, null);
    }

    public DrawingView (Context context, AttributeSet attrs) {
        super(context, attrs);

        mPocketDoodleManager = PocketDoodleManager.get(context);

        if (mPocketDoodleManager.getPaint() == null) {
            Paint paint = new Paint();
            int startingColor = ContextCompat.getColor(context, R.color.colorBlack);
            paint.setColor(startingColor);
            mPocketDoodleManager.setPaint(paint);
        }

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(0xfff8efe0);
        mPocketDoodleManager.setBackgroundPaint(backgroundPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mPocketDoodleManager.getBackgroundPaint());

        drawLines(canvas);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        String mode = mPocketDoodleManager.getMode();
        switch (mode) {
            case "Line":
                handleLineEvent(event);
                break;
            default:
                super.onTouchEvent(event);
        }
        return true;
    }

    private void handleLineEvent(MotionEvent event) {
        PointF originPoint = new PointF(event.getX(), event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Line currentLine = new Line(originPoint);
                currentLine.setColor(mPocketDoodleManager.getPaint());
                mPocketDoodleManager.setCurrentLine(currentLine);
                mPocketDoodleManager.addLine(currentLine);
                break;
            case MotionEvent.ACTION_UP:
                mPocketDoodleManager.setCurrentLine(null);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPocketDoodleManager.getCurrentLine() != null) {
                    mPocketDoodleManager.getCurrentLine().addPoint(originPoint);
                    // Makes DrawingView invalid,
                    // causing it to redraw itself
                    // and its children - calling onDraw
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mPocketDoodleManager.setCurrentLine(null);
                break;
        }
    }

    private void drawLines(Canvas canvas) {
        // Go through all made lines
        for (Line currentLine : mPocketDoodleManager.getLines()) {
            // Get each lines' points
            List<PointF> currentLinePoints = currentLine.getPoints();
            // Initialize origin point of each line
            PointF previousPoint = currentLinePoints.get(0);
            // Make a line from point 1 to point 2, point 2 to point 3, and so on...
            for (int i = 0; i < currentLinePoints.size(); i++) {
                if (i != 0) {
                    PointF currentPoint = currentLinePoints.get(i);
                    canvas.drawLine(previousPoint.x, previousPoint.y,
                                    currentPoint.x, currentPoint.y, currentLine.getColor());
                    previousPoint = currentPoint;
                }
            }
        }
    }
}
