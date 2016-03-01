package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class StandardBackground extends Background {

    public StandardBackground(Context context, int width, int height) {

        this.context = context;

        w = width;
        h = height;

        bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_background);
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);

        createSprite();

    }

}
