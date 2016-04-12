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

    private double drawX;
    private double drawY;

    private double x;
    private double y;

    private double velocityX;
    private double velocityY;

    private int padding;

    private int xShakeStrength;
    private int yShakeStrength;

    private boolean isShaking;

    public Sprite(double x, double y, double velocityX, double velocityY,
                  Rect initialFrame, Bitmap bitmap) {

        drawX = x;
        drawY = y;

        this.x = x;
        this.y = y;

        this.velocityX = velocityX;
        this.velocityY = velocityY;

        this.bitmap = bitmap;
        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);
        this.bitmap = bitmap;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;

        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();

        this.padding = 20;

        for (int i = 1; i < bitmap.getHeight()/frameHeight; i++) {
            for (int j = 1; j < bitmap.getWidth()/frameWidth; j++) {
                if(i == 0 && j == 0) {
                    j=1;
                }

                Rect frame = new Rect(frameWidth*j, frameHeight*i, frameWidth*(j+1), frameHeight*i+1);
                addFrame(frame);
            }
        }
    }

    public void update (int ms) {

        timeForCurrentFrame += ms;

        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }

        x = x + velocityX * (ms/1000.0);
        y = y + velocityY * (ms/1000.0);

        drawX = x;
        drawY = y;

        if (isShaking) {
            drawX -= xShakeStrength;
            drawY -= yShakeStrength;
            isShaking = false;
        }
    }

    public void draw (Canvas canvas) {

        Paint p = new Paint();

        Rect destination = new Rect((int)drawX, (int)drawY, (int)(drawX + frameWidth),
                (int)(drawY + frameHeight));

        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, p);

    }

    public Rect getBoundingBoxRect () {
        return new Rect((int)x+padding,
                (int)y+padding,
                (int)(x + frameWidth - 2 *padding),
                (int)(y + frameHeight - 2* padding));
    }

    public void shakeIt(int xStrength, int yStrength) {
        xShakeStrength = xStrength;
        yShakeStrength = yStrength;
        isShaking = true;
    }

    public boolean intersect (Sprite s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;

    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public double getVx() {
        return velocityX;
    }

    public void setVx(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVy() {
        return velocityY;
    }

    public void setVy(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame%frames.size();
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    public int getFramesCount () {
        return frames.size();
    }

    public void addFrame (Rect frame) {
        frames.add(frame);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
