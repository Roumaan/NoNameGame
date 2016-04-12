package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class EgyptBackground extends Background {
    Bitmap GoldGradeBitmap;
    Bitmap SilverGradeBitmap;
    Bitmap BronzeGradeBitmap;

    public EgyptBackground(Context context, int width, int height) {
        x = 0;
        y = 0;

        w = width + 20;
        h = height + 20;

        GoldGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_background);
        GoldGradeBitmap = Bitmap.createScaledBitmap(GoldGradeBitmap, w, h, false);

        SilverGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        SilverGradeBitmap = Bitmap.createScaledBitmap(SilverGradeBitmap, w, h, false);

        BronzeGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        BronzeGradeBitmap = Bitmap.createScaledBitmap(BronzeGradeBitmap, w, h, false);

        bitmap = GoldGradeBitmap;

        prepare();
    }

    public void setGrade(int grade) {
        switch (grade) {
            case 3:
                bitmap = GoldGradeBitmap;
                break;
            case 2:
                bitmap = SilverGradeBitmap;
                break;
            case 1:
                bitmap = BronzeGradeBitmap;
                break;
        }
        sprite.setBitmap(bitmap);

    }

    @Override
    public void update(int ms) {
        super.update(ms);

        Random random = new Random();

        int i = random.nextInt(20);
        int j = random.nextInt(20);

        sprite.shakeIt(i, j);
    }
}
