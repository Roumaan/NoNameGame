package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EgyptButtons extends  Buttons {

    Bitmap GoldGradeButtonsBitmap;
    Bitmap SilverGradeButtonsBitmap;
    Bitmap BronzeGradeButtonsBitmap;

    public EgyptButtons(Context context, int width, int height) {
        this.context = context;

        button_width = (int) (width*0.20);
        button_height = button_width;

        GoldGradeButtonsBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_button);
        GoldGradeButtonsBitmap = Bitmap.createScaledBitmap(GoldGradeButtonsBitmap, button_width, button_height, false);

        SilverGradeButtonsBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_button);
        SilverGradeButtonsBitmap = Bitmap.createScaledBitmap(SilverGradeButtonsBitmap, button_width, button_height, false);

        BronzeGradeButtonsBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_button);
        BronzeGradeButtonsBitmap = Bitmap.createScaledBitmap(BronzeGradeButtonsBitmap, button_width, button_height, false);

        buttonsBitmap = GoldGradeButtonsBitmap;

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

    public void setGrade(int grade) {
        switch (grade) {
            case 3:
                buttonsBitmap = GoldGradeButtonsBitmap;
                break;
            case 2:
                buttonsBitmap = SilverGradeButtonsBitmap;
                break;
            case 1:
                buttonsBitmap = BronzeGradeButtonsBitmap;
                break;
        }
        button1.setButtonBitmap(buttonsBitmap);
    }
}
