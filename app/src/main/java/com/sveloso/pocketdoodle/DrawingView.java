package com.sveloso.pocketdoodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
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
        drawBoxes(canvas);
        drawErasers(canvas);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        String mode = mPocketDoodleManager.getMode();
        switch (mode) {
            case "Line":
                handleLineEvent(event);
                break;
            case "Box":
                handleBoxEvent(event);
                break;
            case "Eraser":
                handleEraserEvent(event);
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

    private void handleBoxEvent(MotionEvent event) {
        PointF originPoint = new PointF(event.getX(), event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Box currentBox = new Box(originPoint);
                currentBox.setColor(mPocketDoodleManager.getPaint());
                mPocketDoodleManager.setCurrentBox(currentBox);
                mPocketDoodleManager.addBox(currentBox);
                System.out.println("Origin at " + originPoint.x + " , " + originPoint.y);
                break;
            case MotionEvent.ACTION_UP:
                mPocketDoodleManager.setCurrentBox(null);
                System.out.println("Finished a box");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPocketDoodleManager.getCurrentBox() != null) {
                    mPocketDoodleManager.getCurrentBox().setCurrent(originPoint);
                    // Makes DrawingView invalid,
                    // causing it to redraw itself
                    // and its children - calling onDraw
                    System.out.println("Current at " + originPoint.x + " , " + originPoint.y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mPocketDoodleManager.setCurrentBox(null);
                break;
        }
    }

    private void handleEraserEvent (MotionEvent event) {
        PointF originPoint = new PointF(event.getX(), event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Eraser currentEraser = new Eraser(originPoint);
                mPocketDoodleManager.setCurrentEraser(currentEraser);
                mPocketDoodleManager.addEraser(currentEraser);
                break;
            case MotionEvent.ACTION_UP:
                mPocketDoodleManager.setCurrentEraser(null);
                break;
            case MotionEvent.ACTION_MOVE:
                Eraser moveEraser = new Eraser(originPoint);
                mPocketDoodleManager.setCurrentEraser(moveEraser);
                mPocketDoodleManager.addEraser(moveEraser);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                mPocketDoodleManager.setCurrentEraser(null);
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

    private void drawBoxes(Canvas canvas) {
        // Go through all made boxes and draw them
        for (Box currentBox : mPocketDoodleManager.getBoxes()) {
            float left = Math.min(currentBox.getOrigin().x, currentBox.getCurrent().x);
            float right = Math.max(currentBox.getOrigin().x, currentBox.getCurrent().x);
            float top = Math.min(currentBox.getOrigin().y, currentBox.getCurrent().y);
            float bottom = Math.max(currentBox.getOrigin().y, currentBox.getCurrent().y);

            canvas.drawRect(left, top, right, bottom,
                            currentBox.getColor());
        }
    }

    private void drawErasers(Canvas canvas) {
        // Go through all made boxes and draw them
        for (Eraser currentEraser : mPocketDoodleManager.getErasers()) {
            float left = Math.min(currentEraser.getOrigin().x, currentEraser.getCurrent().x);
            float right = Math.max(currentEraser.getOrigin().x, currentEraser.getCurrent().x);
            float top = Math.min(currentEraser.getOrigin().y, currentEraser.getCurrent().y);
            float bottom = Math.max(currentEraser.getOrigin().y, currentEraser.getCurrent().y);

            canvas.drawRect(left, top, right, bottom,
                    mPocketDoodleManager.getBackgroundPaint());
        }
    }

    public void saveDoodle(){
        // Make this into a bitmap
        Bitmap bitmap = this.getDrawingCache();
        // Get the path to android storage
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Make a new file
        File file = new File(path+File.separator+"name"+".png");
        try {
            // If the file doesn't exist
            if(!file.exists()) {
                // Make a new file
                file.createNewFile();
            }
            // Place bitmap into the file
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            System.out.println("Saved pic!");
            ostream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
