package ru.roumaan.nonamegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface Background {
    void draw(Canvas canvas);
    void update(int ms);
    void gradeDecrease();
}
