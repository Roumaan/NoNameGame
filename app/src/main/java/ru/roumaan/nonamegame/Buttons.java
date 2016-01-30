package ru.roumaan.nonamegame;

import android.graphics.Canvas;

public interface Buttons {
    void next(int id);
    void gradeDecrease();
    void update(int ms);
    void draw(Canvas canvas);
    boolean touchCheck(float x, float y);
    boolean isTapRight(float x, float y);
}
