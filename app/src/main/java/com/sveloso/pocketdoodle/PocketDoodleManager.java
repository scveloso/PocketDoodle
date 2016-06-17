package com.sveloso.pocketdoodle;

import android.content.Context;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veloso on 6/15/2016.
 */
public class PocketDoodleManager {

    private static PocketDoodleManager sPocketDoodleManager;
    private Context mContext;

    private Paint mPaint;
    private Paint mBackgroundPaint;
    private String mMode;           // whether user is sketching, making lines, making boxes, etc.

    private Line mCurrentLine;
    private List<Line> mLines = new ArrayList<>();

    private Box mCurrentBox;
    private List<Box> mBoxes = new ArrayList<>();

    public static PocketDoodleManager get(Context context) {
        if (sPocketDoodleManager == null) {
            sPocketDoodleManager = new PocketDoodleManager(context);
        }
        return sPocketDoodleManager;
    }

    private PocketDoodleManager (Context context) {
        mContext = context.getApplicationContext();
        // Default mode is line
        mMode = "Box";
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public Paint getBackgroundPaint() {
        return mBackgroundPaint;
    }

    public void setBackgroundPaint(Paint backgroundPaint) {
        mBackgroundPaint = backgroundPaint;
    }

    public String getMode() {
        return mMode;
    }

    public void setMode(String mode) {
        mMode = mode;
    }



    public Line getCurrentLine() {
        return mCurrentLine;
    }

    public void setCurrentLine(Line currentLine) {
        mCurrentLine = currentLine;
    }

    public List<Line> getLines() {
        return mLines;
    }

    public void addLine(Line l) {
        mLines.add(l);
    }

    public Box getCurrentBox() {
        return mCurrentBox;
    }

    public void setCurrentBox(Box currentBox) {
        mCurrentBox = currentBox;
    }

    public List<Box> getBoxes() {
        return mBoxes;
    }

    public void addBox(Box b) {
        mBoxes.add(b);
    }
}
