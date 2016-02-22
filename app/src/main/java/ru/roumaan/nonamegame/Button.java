package ru.roumaan.nonamegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Button {
    private Sprite button;
    private Sprite symbol;
    private Bitmap[] symbolsBitmaps;

    private int buttonX;
    private int buttonY;

    private int buttonW;
    private int buttonH;

    private int symbolX;
    private int symbolY;

    private int vX;
    private int vY;


    public Button(Bitmap buttonBitmap, Bitmap[] symbolsBitmaps, int x, int y, int vX, int vY) {
        buttonW = buttonBitmap.getWidth();
        buttonH = buttonBitmap.getHeight();

        this.buttonX = x;
        this.buttonY = y;

        this.vX = vX;
        this.vY = vY;

        this.symbolsBitmaps = symbolsBitmaps;

        int symbolW = symbolsBitmaps[0].getWidth();
        int symbolH = symbolsBitmaps[0].getHeight();

        symbolX = buttonX + (buttonW- symbolW)/2;
        symbolY = buttonY + (buttonH- symbolH)/2;

        Rect buttonInitialFrame = new Rect(0, 0, buttonW, buttonH);
        button = new Sprite(buttonX, buttonY, 0, 0, buttonInitialFrame , buttonBitmap);

        Rect symbolInitialFrame = new Rect(0,0, symbolW, symbolH);
        symbol = new Sprite(symbolX, symbolY, 0, 0, symbolInitialFrame, symbolsBitmaps[0]);
    }

    public void setSymbol (int id) {
        symbol.setBitmap(symbolsBitmaps[id]);
    }

    public void draw(Canvas canvas) {
        button.draw(canvas);
        symbol.draw(canvas);
    }

    public boolean isTouched (float x, float y) {
        return (x > buttonX && x < buttonX + buttonW) && (y > buttonY && x < buttonY + buttonH);
    }

    public void update(int ms) {
        buttonX += vX*ms;
        buttonY += vY*ms;

        symbolX += vX*ms;
        symbolY += vY*ms;
    }

    public void setVX(int vX) {
        this.vX = vX;

        button.setVx(vX);
        symbol.setVx(vX);
    }

    public void setVY(int vY) {
        this.vY = vY;

        button.setVy(vY);
        symbol.setVy(vY);
    }

    public int getButtonX() {
        return buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }

    public void setButtonX(int x) {
        button.setX(x);
        buttonX = x;
    }

    public void setButtonY(int y) {
        button.setY(y);
        buttonY = y;
    }

    public void setButtonBitmap(Bitmap buttonBitmap) {
        button.setBitmap(buttonBitmap);
    }

    public int getSymbolX() {

        return symbolX;
    }

    public int getSymbolY() {
        return symbolY;
    }

    public void setSymbolX(int x) {
        symbol.setX(x);
        buttonX = x;
    }

    public void setSymbolY(int y) {
        symbol.setY(y);
        symbolY = y;
    }



}
