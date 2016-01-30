package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;



public class StandartBackground implements Background {
    Paint mPaint = new  Paint();
    Context context;
    Bitmap bitmap;
    public StandartBackground(Context context) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        bitmap = Bitmap.createScaledBitmap(bitmap, displaymetrics.widthPixels, displaymetrics.heightPixels, false);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }

    @Override
    public void update(int ms) {

    }

    @Override
    public void gradeDecrease() {

    }
}
