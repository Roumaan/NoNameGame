package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

public class StandartBoard extends Board {

    DisplayMetrics displaymetrics;

    public StandartBoard (Context context, int width, int height) {

        this.context = context;

        displaymetrics = context.getResources().getDisplayMetrics();

        boardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_board);



        boardW = (int) (width*0.4);
        boardH = (int) (boardW*1.5);

        boardX = (width - boardW)/2;
        boardY = (height - boardH)*0.4;

        boardBitmap = Bitmap.createScaledBitmap(boardBitmap, boardW, boardH, false);

        symbolW = (int) (boardW/1.5);
        symbolH = symbolW;

        symbolX = boardX+(boardW-symbolW)/2;
        symbolY = boardY+(boardH-symbolH)/2;



        symbolsBitmaps = new Bitmap[10];
        int j = 0;
        for (String i:
                context.getResources().getStringArray(R.array.standart_symbols)) {
            int resID = context.getResources().getIdentifier(i, "drawable", context.getPackageName());
            Bitmap symbolBitmap = BitmapFactory.decodeResource(
                    context.getResources(),
                    resID);
            Log.i("button", Integer.toString(resID));
            Log.i("button", context.getPackageName());
            Log.i("button", i);
            symbolBitmap = Bitmap.createScaledBitmap(symbolBitmap, symbolW, symbolH, false);
            symbolsBitmaps[j] = symbolBitmap;
            j++;
        }

        prepare();
    }

}
