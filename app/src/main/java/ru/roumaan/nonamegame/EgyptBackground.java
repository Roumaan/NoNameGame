package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EgyptBackground extends Background {
    Bitmap goldGradeBitmap;
    Bitmap silverGradeBitmap;
    Bitmap bronzeGradeBitmap;

    public EgyptBackground(Context context, int width, int height) {

        this.context = context;

        w = width;
        h = height;

        goldGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        goldGradeBitmap = Bitmap.createScaledBitmap(goldGradeBitmap, w, h, false);

        silverGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        silverGradeBitmap = Bitmap.createScaledBitmap(silverGradeBitmap, w, h, false);

        bronzeGradeBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        bronzeGradeBitmap = Bitmap.createScaledBitmap(silverGradeBitmap, w, h, false);

        bitmap = goldGradeBitmap;

        createSprite();
    }

    public void setGrade(int grade) {
        switch (grade) {
            case 3:
                bitmap = goldGradeBitmap;
                sprite.setBitmap(bitmap);
                break;
            case 2:
                bitmap = bronzeGradeBitmap;
                sprite.setBitmap(bitmap);
                break;
            case 1:
                bitmap = silverGradeBitmap;
                sprite.setBitmap(bitmap);
                break;
        }
    }
}
