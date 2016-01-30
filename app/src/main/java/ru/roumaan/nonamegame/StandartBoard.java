package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.Random;

public class StandartBoard implements Board {
    Paint mPaint = new  Paint();
    Context context;
    Bitmap[] symbols_bitmaps = new Bitmap[5];
    Bitmap board_bitmap;
    Bitmap symbol_bitmap;

    DisplayMetrics displaymetrics;

    public StandartBoard (Context context) {
        this.context = context;

        board_bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_board);

        displaymetrics = context.getResources().getDisplayMetrics();
        board_bitmap = Bitmap.createScaledBitmap(board_bitmap, (int) (displaymetrics.widthPixels*0.5), (int) (displaymetrics.heightPixels*0.35), false);
        int j = 0;
        for (String i:
        context.getResources().getStringArray(R.array.standart_symbols)) {
            int resID = context.getResources().getIdentifier(i , "drawable", context.getPackageName());
            Bitmap symbol_bitmap = BitmapFactory.decodeResource(
                    context.getResources(),
                    resID);
            symbol_bitmap = Bitmap.createScaledBitmap(symbol_bitmap, (int) (displaymetrics.widthPixels*0.5*0.5), (int) (displaymetrics.heightPixels*0.35*0.35), false);
            symbols_bitmaps[j] = symbol_bitmap;
            j++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(board_bitmap, (int)(displaymetrics.widthPixels * 0.25), (int) (displaymetrics.heightPixels*0.28), mPaint);
        canvas.drawBitmap(symbol_bitmap, (int)(displaymetrics.widthPixels * 0.25 + displaymetrics.widthPixels*0.5*0.25), (int) (displaymetrics.heightPixels*0.33 + displaymetrics.heightPixels*0.35*0.175), mPaint);
    }

    @Override
    public void update(int ms) {

    }

    @Override
    public void gradeDecrease() {

    }

    @Override
    public int next() {
        Random random = new Random();
        int i = random.nextInt(5);
        symbol_bitmap = symbols_bitmaps[i];
        return i;
    }
}
