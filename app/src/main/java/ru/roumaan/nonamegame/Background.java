package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

abstract public class Background {
    protected Context context;
    protected Bitmap bitmap;
    protected Sprite sprite;

    protected double x;
    protected double y;

    protected int w;
    protected int h;

    protected void prepare() {
        Rect initialFrame = new Rect(0, 0, w, h);
        sprite = new Sprite(x, y, 0, 0, initialFrame, bitmap);
    }

    void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    void update(int ms) {
        sprite.update(ms);
    }
}
