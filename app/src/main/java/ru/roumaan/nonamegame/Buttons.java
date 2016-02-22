package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

abstract public class Buttons {
    protected Context context;
    protected Bitmap[] symbolsBitmaps;
    protected Bitmap buttonsBitmap;
    protected Button button1;
    protected Button button2;
    protected Button button3;
    protected Button button4;

    protected int true_button;
    protected int button_width;
    protected int button_height;

    protected int button1X;
    protected int button1Y;
    protected int button1VX;
    protected int button1VY;

    protected int button2X;
    protected int button2Y;
    protected int button2VX;
    protected int button2VY;

    protected int button3X;
    protected int button3Y;
    protected int button3VX;
    protected int button3VY;

    protected int button4X;
    protected int button4Y;
    protected int button4VX;
    protected int button4VY;


    void create() {
        button1 = new Button(buttonsBitmap, symbolsBitmaps, button1X, button1Y, button1VX, button1VY);
        button2 = new Button(buttonsBitmap, symbolsBitmaps, button2X, button2Y, button2VX, button2VY);
        button3 = new Button(buttonsBitmap, symbolsBitmaps, button3X, button3Y, button3VX, button3VY);
        button4 = new Button(buttonsBitmap, symbolsBitmaps, button4X, button4Y, button4VX, button4VY);
    }

    void next(int id) {
        Log.i("abc", "4");
        Random random = new Random();
        true_button = random.nextInt(4);

        ArrayList<Integer> ids = new ArrayList<>();
        Log.i("abc", "5");
        for (int i = 0; i < symbolsBitmaps.length-1; i++) {
            Log.i("abc", "6");

            if (i != id) {
                ids.add(i);
            }
        }

        Log.i("abc", "7");
        int anId;
        switch (true_button) {
            case 0 :
                button1.setSymbol(id);

                anId = random.nextInt(ids.size());
                button2.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button3.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button4.setSymbol(ids.get(anId));
                ids.remove(anId);
                return;
            case 1 :
                button2.setSymbol(id);

                anId = random.nextInt(ids.size());
                button1.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button3.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button4.setSymbol(ids.get(anId));
                ids.remove(anId);
                return;
            case 2 :
                button3.setSymbol(id);

                anId = random.nextInt(ids.size());
                button1.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button2.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button4.setSymbol(ids.get(anId));
                ids.remove(anId);
                return;
            case 3 :
                button4.setSymbol(id);

                anId = random.nextInt(ids.size());
                button1.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button2.setSymbol(ids.get(anId));
                ids.remove(anId);

                anId = random.nextInt(ids.size());
                button3.setSymbol(ids.get(anId));
                ids.remove(anId);

        }
        Log.i("abc", "7");
    }

    void draw(Canvas canvas) {

        button1.draw(canvas);
        button2.draw(canvas);
        button3.draw(canvas);
        button4.draw(canvas);

    }

    boolean touchCheck(float x, float y) {
        return button1.isTouched(x, y) || button2.isTouched(x, y) || button3.isTouched(x, y) || button4.isTouched(x, y);
    }

    boolean isTapRight(float x, float y) {
        boolean res = false;

        switch (true_button) {
            case 0:
                res = button1.isTouched(x, y);
                break;
            case 1:
                res = button2.isTouched(x, y);
                break;
            case 2:
                res = button3.isTouched(x, y);
                break;
            case 3:
                res = button4.isTouched(x, y);
                break;
        }

        return res;
    }

    public void update(int ms) {
        button1.update(ms);
        button2.update(ms);
        button3.update(ms);
        button4.update(ms);
    }

}
