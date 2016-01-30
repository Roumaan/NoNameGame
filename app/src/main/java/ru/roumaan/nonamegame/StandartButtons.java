package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Random;


public class StandartButtons implements  Buttons {
    Paint mPaint = new  Paint();
    Context context;
    Bitmap[] symbols_bitmaps = new Bitmap[5];
    Bitmap buttons_bitmap;
    int button1_symbol_id;
    int button2_symbol_id;
    int button3_symbol_id;
    int button4_symbol_id;
    int true_button;
    int button_width;
    int button_height;

    DisplayMetrics displaymetrics;

    public StandartButtons (Context context) {
        this.context = context;

        buttons_bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_button);

        displaymetrics = context.getResources().getDisplayMetrics();

        button_width = (int) (displaymetrics.widthPixels*0.20);
        button_height = (int) (displaymetrics.heightPixels*0.11);
        buttons_bitmap = Bitmap.createScaledBitmap(buttons_bitmap, button_width,  button_height, false);
        int j = 0;
        for (String i:
                context.getResources().getStringArray(R.array.standart_symbols)) {
            int resID = context.getResources().getIdentifier(i, "drawable", context.getPackageName());
            Bitmap symbol_bitmap = BitmapFactory.decodeResource(
                    context.getResources(),
                    resID);
            symbol_bitmap = Bitmap.createScaledBitmap(symbol_bitmap, (int) (button_width * 0.5), (int) (button_height * 0.5), false);
            symbols_bitmaps[j] = symbol_bitmap;
            j++;
        }
    }

    @Override
    public void next(int id) {
        Random random = new Random();
        int i = random.nextInt(4);
        true_button = i;

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(0);
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);
        ArrayList<Integer> ids2 = new ArrayList<>();


        for (int id1 : ids) {
            if (id1 != id) {
                ids2.add(id1);
            }
        }

        int anId;
        switch (i) {
            case 0 :
                button1_symbol_id = id;

                anId = random.nextInt(3);
                button2_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(2);
                button3_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(1);
                button4_symbol_id = ids2.get(anId);
                ids2.remove(anId);
                return;
            case 1 :
                button2_symbol_id = id;

                anId = random.nextInt(3);
                button1_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(2);
                button3_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(1);
                button4_symbol_id = ids2.get(anId);
                ids2.remove(anId);
                return;
            case 2 :
                button3_symbol_id = id;

                anId = random.nextInt(3);
                button1_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(2);
                button2_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(1);
                button4_symbol_id = ids2.get(anId);
                ids2.remove(anId);
                return;
            case 3 :
                button4_symbol_id = id;

                anId = random.nextInt(3);
                button1_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(2);
                button2_symbol_id = ids2.get(anId);
                ids2.remove(anId);

                anId = random.nextInt(1);
                button3_symbol_id = ids2.get(anId);
                ids2.remove(anId);

        }


    }

    @Override
    public void gradeDecrease() {

    }

    @Override
    public void update(int ms) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(buttons_bitmap, (int) (displaymetrics.widthPixels * 0.04), (int) (displaymetrics.heightPixels * 0.7), mPaint);
        canvas.drawBitmap(symbols_bitmaps[button1_symbol_id], (int) (displaymetrics.widthPixels * 0.04 + button_width*0.25), (int) (displaymetrics.heightPixels * 0.7 + button_height*0.25), mPaint);

        canvas.drawBitmap(buttons_bitmap, (int) (displaymetrics.widthPixels * 0.08 + button_width), (int) (displaymetrics.heightPixels * 0.7), mPaint);
        canvas.drawBitmap(symbols_bitmaps[button2_symbol_id], (int) (displaymetrics.widthPixels * 0.08 + button_width*1.25), (int) (displaymetrics.heightPixels * 0.7 + button_height*0.25), mPaint);

        canvas.drawBitmap(buttons_bitmap,  (int) (displaymetrics.widthPixels * 0.12 + button_width * 2), (int) (displaymetrics.heightPixels * 0.7), mPaint);
        canvas.drawBitmap(symbols_bitmaps[button3_symbol_id], (int) (displaymetrics.widthPixels * 0.12 + button_width*2.25), (int) (displaymetrics.heightPixels * 0.7 + button_height*0.25), mPaint);

        canvas.drawBitmap(buttons_bitmap, (int) (displaymetrics.widthPixels * 0.16 + button_width * 3), (int) (displaymetrics.heightPixels * 0.7), mPaint);
        canvas.drawBitmap(symbols_bitmaps[button4_symbol_id], (int) (displaymetrics.widthPixels * 0.16 + button_width * 3.25), (int) (displaymetrics.heightPixels * 0.7 + button_height*0.25), mPaint);
    }

    @Override
    public boolean touchCheck(float x, float y) {
        return (x > displaymetrics.widthPixels * 0.04 && x < displaymetrics.widthPixels * 0.16 + button_width * 4) && (y > displaymetrics.heightPixels * 0.7 && y < displaymetrics.heightPixels * 0.7 + button_height);
    }

    @Override
    public boolean isTapRight(float x, float y) {
        switch (true_button) {
            case 0:
                return (x > displaymetrics.widthPixels * 0.04 && x < displaymetrics.widthPixels * 0.04 + button_width) && (y > displaymetrics.heightPixels * 0.7 && y < displaymetrics.heightPixels * 0.7 + button_height);
            case 1:
                return (x > displaymetrics.widthPixels * 0.08 + button_width && x < displaymetrics.widthPixels * 0.08 + button_width * 2) && (y > displaymetrics.heightPixels * 0.7 && y < displaymetrics.heightPixels * 0.7 + button_height);
            case 2:
                return (x > displaymetrics.widthPixels * 0.12 + button_width * 2 && x < displaymetrics.widthPixels * 0.12 + button_width * 3) && (y > displaymetrics.heightPixels * 0.7 && y < displaymetrics.heightPixels * 0.7 + button_height);
            case 3:
                return (x > displaymetrics.widthPixels * 0.16 + button_width * 3 && x < displaymetrics.widthPixels * 0.16 + button_width * 4) && (y > displaymetrics.heightPixels * 0.7 && y < displaymetrics.heightPixels * 0.7 + button_height);
        }
        return false;
    }
}
