package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class EgyptBoard extends Board {

    private Bitmap GoldGradeBoardBitmap;
    private Bitmap SilverGradeBoardBitmap;
    private Bitmap BronzeGradeBoardBitmap;


    Sprite minusFourthSymbol;
    int minusFourthSymbolId;

    Sprite minusThirdSymbol;
    int minusThirdSymbolId;

    Sprite minusSecondSymbol;
    int minusSecondSymbolId;

    Sprite minusFirstSymbol;
    int minusFirstSymbolId;



    Sprite firstSymbol;
    int firstSymbolId;

    Sprite secondSymbol;
    int secondSymbolId;

    Sprite thirdSymbol;
    int thirdSymbolId;

    Sprite fourthSymbol;
    int fourthSymbolId;


    int symbolGap;

    public EgyptBoard(Context context, int width, int height, int symbols, int speed) {

        boardW = (int) (width*0.396);
        boardH = (int) (height*3.122);

        boardX = (width - boardW)/2;
        boardY = height;

        symbolW = (int) (boardW*0.231);
        symbolH = (int) (boardH*0.0387);

        symbolX = boardX+(boardW-symbolW)/2;
        symbolY = boardY + boardH * 0.06;

        symbolGap = (int)(height*0.0714);

        vX = 0;
        vY = -speed;

        GoldGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_board);
        GoldGradeBoardBitmap = Bitmap.createScaledBitmap(GoldGradeBoardBitmap, boardW, boardH, false);

        SilverGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_board);
        SilverGradeBoardBitmap = Bitmap.createScaledBitmap(SilverGradeBoardBitmap, boardW, boardH, false);

        BronzeGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.standart_board);
        BronzeGradeBoardBitmap = Bitmap.createScaledBitmap(BronzeGradeBoardBitmap, boardW, boardH, false);

        boardBitmap = GoldGradeBoardBitmap;

        String[] names = context.getResources().getStringArray(R.array.standart_symbols);
        symbolsBitmaps = new Bitmap[names.length];

        for (int i = 0; i < names.length; i++) {
            int resID = context.getResources().getIdentifier(names[i], "drawable", context.getPackageName());
            Bitmap symbolBitmap = BitmapFactory.decodeResource(
                    context.getResources(),
                    resID);

            symbolBitmap = Bitmap.createScaledBitmap(symbolBitmap, symbolW, symbolH, false);

            symbolsBitmaps[i] = symbolBitmap;
        }

        prepare();

    }

    @Override
    public void prepare() {
        super.prepare();

        Random random = new Random();

        Rect symbolsInitialFrame = new Rect(0, 0, symbolW, symbolH);

        firstSymbolId = random.nextInt(symbolsBitmaps.length);
        firstSymbol = new Sprite(symbolX,
                symbolY + symbolH + symbolGap,
                vX, vY,
                symbolsInitialFrame,
                symbolsBitmaps[firstSymbolId]);


        secondSymbolId = random.nextInt(symbolsBitmaps.length);
        secondSymbol = new Sprite(symbolX,
                symbolY + symbolH * 2 + symbolGap * 2,
                vX, vY,
                symbolsInitialFrame,
                symbolsBitmaps[secondSymbolId]);

        thirdSymbolId = random.nextInt(symbolsBitmaps.length);
        thirdSymbol = new Sprite(symbolX,
                symbolY + symbolH * 3 + symbolGap * 3,
                vX, vY,
                symbolsInitialFrame,
                symbolsBitmaps[thirdSymbolId]);


        fourthSymbolId = random.nextInt(symbolsBitmaps.length);
        fourthSymbol = new Sprite(symbolX,
                symbolY + symbolH * 4 + symbolGap * 4,
                vX, vY,
                symbolsInitialFrame,
                symbolsBitmaps[fourthSymbolId]);

    }

    public void setGrade(int grade) {
        switch (grade) {
            case 3:
                boardBitmap = GoldGradeBoardBitmap;
                break;
            case 2:
                boardBitmap = SilverGradeBoardBitmap;
                break;
            case 1:
                boardBitmap = BronzeGradeBoardBitmap;
                break;
        }
        board.setBitmap(boardBitmap);
    }

    @Override
    public void update(int ms) {
        super.update(ms);

        Random random = new Random();

        int i = random.nextInt(10);
        board.setX(boardX - i);

        if (minusFourthSymbol != null) {
            minusFourthSymbol.update(ms);
            minusFourthSymbol.setX(symbolX - i);
        }
        if (minusThirdSymbol != null) {
            minusThirdSymbol.update(ms);
            minusThirdSymbol.setX(symbolX - i);
        }
        if (minusSecondSymbol != null) {
            minusSecondSymbol.update(ms);
            minusSecondSymbol.setX(symbolX - i);
        }
        if (minusFirstSymbol != null) {
            minusFirstSymbol.update(ms);
            minusFirstSymbol.setX(symbolX - i);
        }

        symbol.setX(symbolX - i);

        firstSymbol.update(ms);
        firstSymbol.setX(symbolX - i);
        secondSymbol.update(ms);
        secondSymbol.setX(symbolX - i);
        thirdSymbol.update(ms);
        thirdSymbol.setX(symbolX - i);
        fourthSymbol.update(ms);
        fourthSymbol.setX(symbolX-i);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (minusFourthSymbol != null) {
            minusFourthSymbol.draw(canvas);
        }
        if (minusThirdSymbol != null) {
            minusThirdSymbol.draw(canvas);
        }
        if (minusSecondSymbol != null) {
            minusSecondSymbol.draw(canvas);
        }
        if (minusFirstSymbol != null) {
            minusFirstSymbol.draw(canvas);
        }

        fourthSymbol.draw(canvas);
        thirdSymbol.draw(canvas);
        secondSymbol.draw(canvas);
        firstSymbol.draw(canvas);


    }

    @Override
    int next() {

        minusFourthSymbolId = minusThirdSymbolId;
        minusFourthSymbol = minusThirdSymbol;

        minusThirdSymbolId = minusSecondSymbolId;
        minusThirdSymbol = minusSecondSymbol;

        minusSecondSymbolId = minusFirstSymbolId;
        minusSecondSymbol = minusFirstSymbol;

        minusFirstSymbolId = symbolId;
        minusFirstSymbol = symbol;

        symbolId = firstSymbolId;
        symbol = firstSymbol;


        firstSymbolId = secondSymbolId;
        firstSymbol = secondSymbol;

        secondSymbolId = thirdSymbolId;
        secondSymbol = thirdSymbol;

        thirdSymbolId = fourthSymbolId;
        thirdSymbol = fourthSymbol;

        Random random = new Random();

        Rect symbolsInitialFrame = new Rect(0, 0, symbolW, symbolH);

        fourthSymbolId = random.nextInt(symbolsBitmaps.length);
        fourthSymbol = new Sprite(symbolX,
                symbol.getY() + symbolH * 4 + symbolGap * 4,
                vX, vY,
                symbolsInitialFrame,
                symbolsBitmaps[fourthSymbolId]);


        return symbolId;

    }

    public boolean isBeyond() {
        return symbol.getY() <= -symbolH / 2;
    }
}
