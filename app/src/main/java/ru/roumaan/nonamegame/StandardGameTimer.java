package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class StandardGameTimer extends  GameTimer{

    public StandardGameTimer(Context context, int startTime, int width, int height) {
        this.startTime = startTime;
        this.width = width;
        this.height = height;

        remainingTimeNum = this.startTime;
        remainingPercent = 100;

        remainingTimeStripX = (int) (width * 0.122);
        endingTimeStripX = (int) (width * 0.893);

        timeStripsW = (int) (width * 0.786);
        timeStripsH = (int) (height * 0.047);

        remainingTimeStripBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.remaining_time);
        remainingTimeStripBitmap = Bitmap.createScaledBitmap(remainingTimeStripBitmap, timeStripsW, timeStripsH, false);

        endingTimeStripBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.ending_time);
        endingTimeStripBitmap = Bitmap.createScaledBitmap(endingTimeStripBitmap, timeStripsW, timeStripsH, false);

        zeroPercentPoz = remainingTimeStripX;
        oneHundredPercentPoz = endingTimeStripX;


        prepare();
    }

}
