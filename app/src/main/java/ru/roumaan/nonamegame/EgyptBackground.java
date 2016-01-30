package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public class EgyptBackground implements Background {
    Paint mPaint = new Paint();
    Context context;
    Bitmap goldGradeBitmap;
    Bitmap silverGradeBitmap;
    Bitmap bronzeGradeBitmap;
    Bitmap bitmap;


    public EgyptBackground(Context context) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        bitmap = Bitmap.createScaledBitmap(bitmap, displaymetrics.widthPixels + 10, displaymetrics.heightPixels + 10, false);
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
