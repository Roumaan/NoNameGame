package ru.roumaan.nonamegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

abstract public class GameTimer {
    Paint mPaint = new  Paint();
    Bitmap remainingTimeStripBitmap;
    Bitmap endingTimeStripBitmap;

    Sprite remainingTimeStrip;
    Sprite endingTimeStrip;

    double remainingPercent;

    double remainingTimeStripX;
    double remainingTimeStripY;

    double endingTimeStripX;
    double endingTimeStripY;

    int timeStripsW;
    int timeStripsH;

    double oneHundredPercentPoz;
    double zeroPercentPoz;

    int remainingTimeNum;
    int startTime;

    int width;
    int height;

    public void  prepare () {
        Rect timeStripsInitialFrame = new Rect(0, 0, timeStripsW, timeStripsH);

        remainingTimeStrip = new Sprite(remainingTimeStripX, remainingTimeStripY, 0, 0, timeStripsInitialFrame, remainingTimeStripBitmap );
        endingTimeStrip = new Sprite(endingTimeStripX, endingTimeStripY, 0, 0, timeStripsInitialFrame, endingTimeStripBitmap);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(remainingTimeStripBitmap, (int)(width * 0.122), (int) (height*0.039), mPaint);
        canvas.drawBitmap(endingTimeStripBitmap, (int)((oneHundredPercentPoz - zeroPercentPoz) / (100/remainingPercent)), (int) (height*0.039), mPaint);
    }

    public void update(int ms, int remainingTime) {
        this.remainingTimeNum = remainingTime;
        remainingPercent = this.remainingTimeNum*100/startTime;

        remainingTimeStrip.update(ms);
        endingTimeStrip.update(ms);
    }

}
