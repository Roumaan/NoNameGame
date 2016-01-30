package ru.roumaan.nonamegame;


import android.graphics.Canvas;

public interface Board {
    void draw(Canvas canvas);
    void update(int ms);
    void gradeDecrease();
    int next();
}
