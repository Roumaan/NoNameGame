package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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

        symbolW = (int) (boardW/1.5);
        symbolH = symbolW;

        boardW = (int) (width*0.4);
        boardH = (int) (boardW*1.5);

        boardX = (width - boardW)/2;
        boardY = (height - boardH)*0.4;

        symbolX = boardX+(boardW-symbolW)/2;
        symbolY = boardY+(boardH-symbolH)/2;

        vX = 0;
        vY = speed;

        int resID = context.getResources().getIdentifier("gold_egypt_board_" + symbols, "drawable", context.getPackageName());
        GoldGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                resID);
        GoldGradeBoardBitmap = Bitmap.createScaledBitmap(GoldGradeBoardBitmap, boardW, boardH, false);

        resID = context.getResources().getIdentifier("silver_egypt_board_" + symbols, "drawable", context.getPackageName());
        SilverGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                resID);
        SilverGradeBoardBitmap = Bitmap.createScaledBitmap(SilverGradeBoardBitmap, boardW, boardH, false);

        resID = context.getResources().getIdentifier("bronze_egypt_board_" + symbols, "drawable", context.getPackageName());
        BronzeGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                resID);
        BronzeGradeBoardBitmap = Bitmap.createScaledBitmap(BronzeGradeBoardBitmap, boardW, boardH, false);

        boardBitmap = GoldGradeBoardBitmap;


        prepare();


        minusFourthSymbol = symbol;
        minusThirdSymbol = symbol;
        minusSecondSymbol = symbol;
        minusFirstSymbol = symbol;

        firstSymbol = symbol;
        secondSymbol = symbol;
        thirdSymbol = symbol;
        fourthSymbol = symbol;

        Random random = new Random();

        minusFourthSymbolId = random.nextInt(symbolsBitmaps.length);
        minusFourthSymbol.setBitmap(symbolsBitmaps[minusFourthSymbolId]);

        minusThirdSymbolId = random.nextInt(symbolsBitmaps.length);
        minusThirdSymbol.setBitmap(symbolsBitmaps[minusThirdSymbolId]);

        minusSecondSymbolId = random.nextInt(symbolsBitmaps.length);
        minusSecondSymbol.setBitmap(symbolsBitmaps[minusSecondSymbolId]);

        minusFirstSymbolId = random.nextInt(symbolsBitmaps.length);
        minusFirstSymbol.setBitmap(symbolsBitmaps[minusFirstSymbolId]);



        firstSymbolId = random.nextInt(symbolsBitmaps.length);
        firstSymbol.setBitmap(symbolsBitmaps[firstSymbolId]);

        secondSymbolId = random.nextInt(symbolsBitmaps.length);
        secondSymbol.setBitmap(symbolsBitmaps[secondSymbolId]);

        thirdSymbolId = random.nextInt(symbolsBitmaps.length);
        thirdSymbol.setBitmap(symbolsBitmaps[thirdSymbolId]);

        fourthSymbolId = random.nextInt(symbolsBitmaps.length);
        fourthSymbol.setBitmap(symbolsBitmaps[fourthSymbolId]);
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
    void update(int ms) {
        super.update(ms);

        minusFourthSymbol.update(ms);
        minusThirdSymbol.update(ms);
        minusSecondSymbol.update(ms);
        minusFirstSymbol.update(ms);

        firstSymbol.update(ms);
        secondSymbol.update(ms);
        thirdSymbol.update(ms);
        fourthSymbol.update(ms);
    }

    @Override
    void draw(Canvas canvas) {
        super.draw(canvas);

        minusFourthSymbol.draw(canvas);
        minusThirdSymbol.draw(canvas);
        minusSecondSymbol.draw(canvas);
        minusFirstSymbol.draw(canvas);

        firstSymbol.draw(canvas);
        secondSymbol.draw(canvas);
        thirdSymbol.draw(canvas);
        fourthSymbol.draw(canvas);
    }

    @Override
    int next() {

        minusFourthSymbolId = minusThirdSymbolId;
        minusFourthSymbol = minusThirdSymbol;

        minusThirdSymbolId = minusSecondSymbolId;
        minusThirdSymbol = minusSecondSymbol;

        minusSecondSymbolId = minusFirstSymbolId;
        minusSecondSymbol = minusFirstSymbol;

        symbolId = firstSymbolId;
        symbol = firstSymbol;

        firstSymbolId = secondSymbolId;
        firstSymbol = secondSymbol;

        secondSymbolId = thirdSymbolId;
        secondSymbol = thirdSymbol;

        thirdSymbolId = fourthSymbolId;
        thirdSymbol = fourthSymbol;

        Random random = new Random();
        fourthSymbolId = random.nextInt(symbolsBitmaps.length);
        fourthSymbol = symbol;

        fourthSymbol.setBitmap(symbolsBitmaps[fourthSymbolId]);
        fourthSymbol.setY(thirdSymbol.getY() + symbolH + symbolGap );

        return symbolId;
    }

    public boolean isBeyond() {
        return false;
    }
}
