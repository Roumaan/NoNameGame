package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public class GameTimer {
    Paint mPaint = new  Paint();
    Bitmap remainingTimeBitmap;
    Bitmap endingTimeBitmap;
    double remainingPercent;
    int oneHundredPercentPoz;
    int zeroPercentPoz;
    int remainingTime;
    int startTime;

    DisplayMetrics displaymetrics;

    public GameTimer(Context context, int startTime) {
        this.startTime = startTime;
        remainingTime = this.startTime;
        remainingPercent = 100;
        displaymetrics = context.getResources().getDisplayMetrics();

        remainingTimeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.remaining_time);


        remainingTimeBitmap = Bitmap.createScaledBitmap(remainingTimeBitmap, (int) (displaymetrics.widthPixels * 0.7), (int) (displaymetrics.heightPixels * 0.04), false);

        endingTimeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.ending_time);

        endingTimeBitmap = Bitmap.createScaledBitmap(endingTimeBitmap, (int) (displaymetrics.widthPixels * 0.77), (int) (displaymetrics.heightPixels * 0.04), false);

        oneHundredPercentPoz = (int) (displaymetrics.widthPixels * 0.84);
        zeroPercentPoz = (int) (displaymetrics.widthPixels * 0.14);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(remainingTimeBitmap, (int)(displaymetrics.widthPixels * 0.14), (int) (displaymetrics.heightPixels*0.045), mPaint);
        canvas.drawBitmap(endingTimeBitmap, (int)((oneHundredPercentPoz - zeroPercentPoz) / (100/remainingPercent)), (int) (displaymetrics.heightPixels*0.045), mPaint);
    }

    public void update(int remainingTime) {
        this.remainingTime = remainingTime;
        remainingPercent = this.remainingTime*100/startTime;
    }

    public void gradeDecrease() {
    }
}
