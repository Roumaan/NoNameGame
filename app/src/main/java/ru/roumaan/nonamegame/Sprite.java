package ru.roumaan.nonamegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {
    private Bitmap bitmap;
    private List<Rect> frames;
    private int frameWidth;
    private int frameHeight;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;

    private double x;
    private double y;

    public Sprite(double x, double y,
                  Rect initialFrame, Bitmap bitmap) {

        this.x = x;
        this.y = y;

        this.bitmap = bitmap;
        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);
        this.bitmap = bitmap;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 100;
        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
    }

    public void update (int ms) {

        timeForCurrentFrame += ms;

        if (timeForCurrentFrame >= frameTime) {

            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;

        }

    }

    public void draw (Canvas canvas) {

        Paint p = new Paint();

        Rect destination = new Rect((int)x, (int)y, (int)(x + frameWidth),
                (int)(y + frameHeight));

        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, p);

    }

    public void addFrame (Rect frame) {
        frames.add(frame);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
