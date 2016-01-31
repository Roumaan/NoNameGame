package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.util.List;
import java.util.Random;

public class EgyptBackground implements Background {
    private DisplayMetrics displaymetrics;
    private Paint mPaint = new Paint();
    private Context context;
    private Bitmap silverGradeBitmap;
    private Bitmap bronzeGradeBitmap;
    private Bitmap defeatBitmap;
    private Bitmap bitmap;
    private Sprite background;

    private boolean shaking;
    private int grade;



    public EgyptBackground(Context context) {
        this.context = context;
        Bitmap goldGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_gold_background);
        /*
        silverGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_gold_background);
        bronzeGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_gold_background);
        defeatBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_gold_background);
        */

        displaymetrics = context.getResources().getDisplayMetrics();
        goldGradeBitmap = Bitmap.createScaledBitmap(goldGradeBitmap, (displaymetrics.widthPixels + 20) * 5, (displaymetrics.heightPixels + 20) * 3, false);
        //silverGradeBitmap = Bitmap.createScaledBitmap(goldGradeBitmap/*silverGradeBitmap*/, (displaymetrics.widthPixels + 20) * 5, (displaymetrics.heightPixels + 20) * 3, false);
        //bronzeGradeBitmap = Bitmap.createScaledBitmap(goldGradeBitmap/*bronzeGradeBitmap*/, (displaymetrics.widthPixels + 20) * 5, (displaymetrics.heightPixels + 20) * 3, false);
        //defeatBitmap = Bitmap.createScaledBitmap(goldGradeBitmap/*defeatBitmap*/, (displaymetrics.widthPixels + 20) * 5, displaymetrics.heightPixels + 20, false);

        bitmap = goldGradeBitmap;
        grade = 3;


        int w = bitmap.getWidth()/5;
        int h = bitmap.getHeight()/3;

        Rect firstFrame = new Rect(0, 0, w, h);

        background = new Sprite(0, 0, firstFrame, bitmap);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }


                background.addFrame(new Rect(j * w, i * h, j * w + w, i * h + h));

            }
        }
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

        background.setXY(-xSubtrahend, -ySubtrahend);
        background.draw(canvas);

    }

    @Override
    public void update(int ms) {
        background.update(ms);
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

        background.setBitmap(bitmap);
    }


}
