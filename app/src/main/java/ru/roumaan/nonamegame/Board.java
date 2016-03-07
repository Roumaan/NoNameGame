package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.Random;

abstract public class Board {
    protected Context context;
    protected Bitmap[] symbolsBitmaps;
    protected Bitmap boardBitmap;
    private Sprite board;
    private Sprite symbol;

    protected double symbolX;
    protected double symbolY;

    protected double boardX;
    protected double boardY;

    protected double vX;
    protected double vY;

    protected int boardW;
    protected int boardH;

    protected int symbolW;
    protected int symbolH;

    void prepare() {
        Rect boardInitialFrame = new Rect(0, 0, boardW, boardH);
        Rect symbolInitialFrame = new Rect(0, 0, symbolW, symbolH);
        board = new Sprite(boardX, boardY, vX, vY, boardInitialFrame, boardBitmap );
        symbol = new Sprite(symbolX, symbolY, vX, vY, symbolInitialFrame, symbolsBitmaps[0] );
    }

    void draw(Canvas canvas) {
        board.draw(canvas);
        symbol.draw(canvas);
    }

    void update(int ms) {

        board.update(ms);
        symbol.update(ms);

        boardX = board.getX();
        boardY = board.getY();

        symbolX = symbol.getX();
        symbolY = symbol.getY();
    }

    int next() {
        Random random = new Random();
        int i = random.nextInt(symbolsBitmaps.length);
        symbol.setBitmap(symbolsBitmaps[i]);

        return i;
    }

}
