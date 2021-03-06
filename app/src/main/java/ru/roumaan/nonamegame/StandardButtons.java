package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class StandardButtons extends   Buttons {

    public StandardButtons(Context context, int width, int height) {
        this.context = context;

        buttonsBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_button);

        button_width = (int) (width*0.20);
        button_height = button_width;

        buttonsBitmap = Bitmap.createScaledBitmap(buttonsBitmap, button_width,  button_height, false);

        button1X = (int) (width * 0.04);
        button1Y = (int) (height * 0.8);

        button2X = (int) (width * 0.08 + button_width);
        button2Y = (int) (height * 0.8);

        button3X = (int) (width * 0.12 + button_width * 2);
        button3Y = (int) (height * 0.8);

        button4X = (int) (width * 0.16 + button_width * 3);
        button4Y = (int) (height * 0.8);


        String[] names = context.getResources().getStringArray(R.array.standart_symbols);
        symbolsBitmaps = new Bitmap[names.length];

        for (int i = 0; i < names.length; i++) {
            int resID = context.getResources().getIdentifier(names[i], "drawable", context.getPackageName());
            Bitmap symbolBitmap = BitmapFactory.decodeResource(
                    context.getResources(),
                    resID);
            Log.i("button", Integer.toString(resID));
            Log.i("button", context.getPackageName());
            Log.i("button", names[i]);
            symbolBitmap = Bitmap.createScaledBitmap(symbolBitmap, (int) (button_width * 0.5), (int) (button_height * 0.5), false);
            symbolsBitmaps[i] = symbolBitmap;
        }

        prepare();
    }


}
