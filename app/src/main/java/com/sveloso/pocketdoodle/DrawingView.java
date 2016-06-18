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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Veloso on 6/15/2016.
 */
public class DrawingView extends View {

    private PocketDoodleManager sPocketDoodleManager;

    public DrawingView (Context context) {
        this(context, null);
    }

    public DrawingView (Context context, AttributeSet attrs) {
        super(context, attrs);

        sPocketDoodleManager = PocketDoodleManager.get(context);

        if (sPocketDoodleManager.getPaint() == null) {
            Paint paint = new Paint();
            int startingColor = ContextCompat.getColor(context, R.color.colorBlack);
            paint.setColor(startingColor);
            sPocketDoodleManager.setPaint(paint);
        }

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(0xfff8efe0);
        sPocketDoodleManager.setBackgroundPaint(backgroundPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(sPocketDoodleManager.getBackgroundPaint());

        drawShapes(canvas);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        String mode = sPocketDoodleManager.getMode();
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
                currentLine.setColor(sPocketDoodleManager.getPaint());
                sPocketDoodleManager.setCurrentLine(currentLine);
                sPocketDoodleManager.addLine(currentLine);
                break;
            case MotionEvent.ACTION_UP:
                sPocketDoodleManager.setCurrentLine(null);
                break;
            case MotionEvent.ACTION_MOVE:
                if (sPocketDoodleManager.getCurrentLine() != null) {
                    sPocketDoodleManager.getCurrentLine().addPoint(originPoint);
                    // Makes DrawingView invalid,
                    // causing it to redraw itself
                    // and its children - calling onDraw
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                sPocketDoodleManager.setCurrentLine(null);
                break;
        }
    }

    private void handleBoxEvent(MotionEvent event) {
        PointF originPoint = new PointF(event.getX(), event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Box currentBox = new Box(originPoint);
                currentBox.setColor(sPocketDoodleManager.getPaint());
                sPocketDoodleManager.setCurrentBox(currentBox);
                sPocketDoodleManager.addBox(currentBox);
                break;
            case MotionEvent.ACTION_UP:
                sPocketDoodleManager.setCurrentBox(null);
                break;
            case MotionEvent.ACTION_MOVE:
                if (sPocketDoodleManager.getCurrentBox() != null) {
                    sPocketDoodleManager.getCurrentBox().setCurrent(originPoint);
                    // Makes DrawingView invalid,
                    // causing it to redraw itself
                    // and its children - calling onDraw
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                sPocketDoodleManager.setCurrentBox(null);
                break;
        }
    }

    private void handleEraserEvent (MotionEvent event) {
        PointF originPoint = new PointF(event.getX(), event.getY());

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Eraser currentEraser = new Eraser(originPoint);
                sPocketDoodleManager.setCurrentEraser(currentEraser);
                sPocketDoodleManager.addEraser(currentEraser);
                break;
            case MotionEvent.ACTION_UP:
                sPocketDoodleManager.setCurrentEraser(null);
                break;
            case MotionEvent.ACTION_MOVE:
                Eraser moveEraser = new Eraser(originPoint);
                sPocketDoodleManager.setCurrentEraser(moveEraser);
                sPocketDoodleManager.addEraser(moveEraser);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                sPocketDoodleManager.setCurrentEraser(null);
                break;
        }
    }

    private void drawShapes(Canvas canvas) {
        for (Shape s : sPocketDoodleManager.getShapes()) {
            if (s instanceof Line) {
                drawLine((Line) s, canvas);
            } else if (s instanceof Box) {
                drawBox ((Box) s, canvas);
            } else if (s instanceof Eraser) {
                drawEraser((Eraser) s, canvas);
            }
        }
    }

    private void drawLine(Line l, Canvas canvas) {
        List<PointF> currentLinePoints = l.getPoints();
        // Initialize origin point of each line
        PointF previousPoint = currentLinePoints.get(0);
        // Make a line from point 1 to point 2, point 2 to point 3, and so on...
        for (int i = 0; i < currentLinePoints.size(); i++) {
            if (i != 0) {
                PointF currentPoint = currentLinePoints.get(i);
                canvas.drawLine(previousPoint.x, previousPoint.y,
                        currentPoint.x, currentPoint.y, l.getColor());
                previousPoint = currentPoint;
            }
        }
    }

    private void drawBox(Box currentBox, Canvas canvas) {
        float left = Math.min(currentBox.getOrigin().x, currentBox.getCurrent().x);
        float right = Math.max(currentBox.getOrigin().x, currentBox.getCurrent().x);
        float top = Math.min(currentBox.getOrigin().y, currentBox.getCurrent().y);
        float bottom = Math.max(currentBox.getOrigin().y, currentBox.getCurrent().y);

        canvas.drawRect(left, top, right, bottom,
                currentBox.getColor());
    }

    private void drawEraser(Eraser currentEraser, Canvas canvas) {
        float left = Math.min(currentEraser.getOrigin().x, currentEraser.getCurrent().x);
        float right = Math.max(currentEraser.getOrigin().x, currentEraser.getCurrent().x);
        float top = Math.min(currentEraser.getOrigin().y, currentEraser.getCurrent().y);
        float bottom = Math.max(currentEraser.getOrigin().y, currentEraser.getCurrent().y);

        canvas.drawRect(left, top, right, bottom,
                sPocketDoodleManager.getBackgroundPaint());
    }

    public boolean saveDoodle(String name){
        // Make this into a bitmap
        Bitmap bitmap = this.getDrawingCache();
        // Get the path to android storage
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Make a new file
        File file = new File(path + File.separator + name + ".png");
        try {
            // If the file doesn't exist
            if(!file.exists()) {
                // Make a new file
                file.createNewFile();
            }
            // Place bitmap into the file
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            ostream.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
