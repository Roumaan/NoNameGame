package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.Random;

public class EgyptBackground implements Background {
    DisplayMetrics displaymetrics;
    Paint mPaint = new Paint();
    Context context;
    Bitmap goldGradeBitmap;
    Bitmap silverGradeBitmap;
    Bitmap bronzeGradeBitmap;
    Bitmap defeatBitmap;
    Bitmap bitmap;

    boolean shaking;
    int grade;



    public EgyptBackground(Context context) {
        this.context = context;
        goldGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        silverGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        bronzeGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        defeatBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);

        displaymetrics = context.getResources().getDisplayMetrics();
        goldGradeBitmap = Bitmap.createScaledBitmap(goldGradeBitmap, displaymetrics.widthPixels + 20, displaymetrics.heightPixels + 20, false);
        silverGradeBitmap = Bitmap.createScaledBitmap(silverGradeBitmap, displaymetrics.widthPixels + 20, displaymetrics.heightPixels + 20, false);
        bronzeGradeBitmap = Bitmap.createScaledBitmap(bronzeGradeBitmap, displaymetrics.widthPixels + 20, displaymetrics.heightPixels + 20, false);
        defeatBitmap = Bitmap.createScaledBitmap(defeatBitmap, displaymetrics.widthPixels + 20, displaymetrics.heightPixels + 20, false);

        bitmap = goldGradeBitmap;
        grade = 3;
    }

    @Override
    public void draw(Canvas canvas) {
        int xSubtrahend = 10;
        int ySubtrahend = 10;

        if (shaking) {
            Random random = new Random();
            xSubtrahend = random.nextInt(20);
            ySubtrahend = random.nextInt(20);
        }
        canvas.drawBitmap(bitmap, displaymetrics.widthPixels-xSubtrahend, displaymetrics.heightPixels-ySubtrahend, mPaint);
    }

    @Override
    public void update(int ms) {

    }

    @Override
    public void gradeDecrease() {
        grade--;
        shaking = true;

        switch (grade) {
            case 2:
                bitmap = silverGradeBitmap;
                break;
            case 1:
                bitmap = bronzeGradeBitmap;
                break;
            case 0:
                bitmap = defeatBitmap;
                break;
        }
    }
}
