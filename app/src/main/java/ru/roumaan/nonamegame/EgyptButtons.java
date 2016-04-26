package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EgyptButtons extends  Buttons {

    Bitmap secondButtonBitmap;
    Bitmap thirdButtonBitmap;
    Bitmap fourthButtonBitmap;

    public EgyptButtons(Context context, int width, int height) {
        this.context = context;

        button_width = (int) (width*0.20);
        button_height = button_width;

        buttonsBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_button1);
        buttonsBitmap = Bitmap.createScaledBitmap(buttonsBitmap, button_width, button_height, false);

        secondButtonBitmap= BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_button2);
        secondButtonBitmap = Bitmap.createScaledBitmap(secondButtonBitmap, button_width, button_height, false);

        thirdButtonBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_button3);
        thirdButtonBitmap = Bitmap.createScaledBitmap(thirdButtonBitmap, button_width, button_height, false);

        fourthButtonBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_button4);
        fourthButtonBitmap = Bitmap.createScaledBitmap(fourthButtonBitmap, button_width, button_height, false);



        button1X = (int) (width * 0.04);
        button1Y = (int) (height * 0.8);

        button2X = (int) (width * 0.08 + button_width);
        button2Y = (int) (height * 0.8);

        button3X = (int) (width * 0.12 + button_width * 2);
        button3Y = (int) (height * 0.8);

        button4X = (int) (width * 0.16 + button_width * 3);
        button4Y = (int) (height * 0.8);


        String[] names = context.getResources().getStringArray(R.array.egypt_symbols);
        symbolsBitmaps = new Bitmap[names.length];

        for (int i = 0; i < names.length; i++) {
            int resID = context.getResources().getIdentifier(names[i], "drawable", context.getPackageName());
            Bitmap symbolBitmap = BitmapFactory.decodeResource(
                    context.getResources(),
                    resID);

            symbolBitmap = Bitmap.createScaledBitmap(symbolBitmap, (int) (button_width * 0.5), (int) (button_height * 0.5), false);
            symbolsBitmaps[i] = symbolBitmap;
        }

        prepare();
    }

    @Override
    void prepare() {
        button1 = new Button(buttonsBitmap, symbolsBitmaps, button1X, button1Y, button1VX, button1VY);
        button2 = new Button(secondButtonBitmap, symbolsBitmaps, button2X, button2Y, button2VX, button2VY);
        button3 = new Button(thirdButtonBitmap, symbolsBitmaps, button3X, button3Y, button3VX, button3VY);
        button4 = new Button(fourthButtonBitmap, symbolsBitmaps, button4X, button4Y, button4VX, button4VY);

    }

    public void setGrade(int grade) {
        // some code ...
        // in future
    }
}
