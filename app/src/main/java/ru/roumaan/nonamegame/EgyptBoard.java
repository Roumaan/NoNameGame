package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class EgyptBoard extends Board {

    private Bitmap GoldGradeBoardBitmap;
    private Bitmap SilverGradeBoardBitmap;
    private Bitmap BronzeGradeBoardBitmap;
    private Bitmap EndOfBordBitmap;

    boolean justCreated;

    int heightOfCanvas;
    int widthOfCanvas;

    Sprite endOfBoard;
    
    int endOfBoardW;
    int endOfBoardH;
    
    double endOfBoardX;
    double endOfBoardY;

    Sprite[] symbolsHigher;
    Sprite[] platesHigher;
    int[] symbolsHigherIds;

    Sprite plate;
    Bitmap plateBitmap;
    Bitmap pressedPlateBitmap;
    int plateW;
    int plateH;
    double plateX;
    double plateY;

    Sprite[] symbolsBelow;
    Sprite[] platesBelow;
    int[] symbolsBelowIds;

    boolean isStopped;

    int symbolGap;

    public EgyptBoard(Context context, int width, int height, int symbols, int speed, Bitmap endOfBoard) {

        heightOfCanvas = height;
        widthOfCanvas = width;

        boardW = (int) (width*0.396);
        boardH = (int) (height*3.122);

        boardX = (width - boardW)/2+10;
        boardY = height+10;

        symbolGap = (int)(boardH*0.014);

        symbolW = (int) (boardW*0.231);
        symbolH = (int) (boardH*0.047);

        symbolX = boardX+(boardW-symbolW)/2;
        symbolY = boardY + boardH * 0.044 + symbolGap/2;

        plateW = (int) (boardW*0.3098);
        plateH = (int) (boardH*0.05);

        plateX = symbolX + (symbolW-plateW)/2;
        plateY = symbolY + (symbolH-plateH)/2;

        
        endOfBoardW = boardW+boardW/4;
        endOfBoardH = (int) (height + height * 0.2325);
        
        		
        endOfBoardX = boardX - boardW/4/2;
        endOfBoardY = boardY + boardH * 0.044 + symbolH*symbols + symbolGap*(symbols-1) + symbolGap/2;
        
        vX = 0;
        vY = -speed;

        GoldGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_board);
        GoldGradeBoardBitmap = Bitmap.createScaledBitmap(GoldGradeBoardBitmap, boardW, boardH, false);

        SilverGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_board);
        SilverGradeBoardBitmap = Bitmap.createScaledBitmap(SilverGradeBoardBitmap, boardW, boardH, false);

        BronzeGradeBoardBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.egypt_board);
        BronzeGradeBoardBitmap = Bitmap.createScaledBitmap(BronzeGradeBoardBitmap, boardW, boardH, false);

        boardBitmap = GoldGradeBoardBitmap;
        
        EndOfBordBitmap = endOfBoard;
        EndOfBordBitmap = Bitmap.createScaledBitmap(EndOfBordBitmap, endOfBoardW, endOfBoardH, false);

        plateBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.plate);
        plateBitmap = Bitmap.createScaledBitmap(plateBitmap, plateW, plateH, false);

        pressedPlateBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.pressed_plate);
        pressedPlateBitmap = Bitmap.createScaledBitmap(pressedPlateBitmap, plateW, plateH, false);

        String[] names = context.getResources().getStringArray(R.array.egypt_symbols);
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

        justCreated = true;
    }

    @Override
    public void prepare() {
        super.prepare();


        Rect endOfBoardInitialFrame = new Rect(0, 0,  endOfBoardW, endOfBoardH);
        
        endOfBoard = new Sprite(endOfBoardX, endOfBoardY, vX, vY, endOfBoardInitialFrame, EndOfBordBitmap);

        Random random = new Random();

        Rect symbolsInitialFrame = new Rect(0, 0, symbolW, symbolH);
        Rect plateInitialFrame = new Rect(0, 0, plateW, plateH);

        platesHigher = new Sprite[10];
        symbolsHigherIds = new int[10];
        symbolsHigher = new Sprite[10];

        plate = new Sprite(plateX,
                plateY,
                vX, vY,
                plateInitialFrame,
                plateBitmap);

        platesBelow = new Sprite [10];
        symbolsBelowIds = new int[10];
        symbolsBelow = new Sprite[10];

        for (int i = 0; i < symbolsBelow.length; i++) {
        	symbolsBelowIds[i] = random.nextInt(symbolsBitmaps.length);
        	symbolsBelow[i] = new Sprite(symbolX,
        		symbolY + symbolH*(i+1) + symbolGap*(i+1),
        		vX, vY,
        		symbolsInitialFrame,
        		symbolsBitmaps[symbolsBelowIds[i]]);
        }

        for (int i = 0; i < platesBelow.length; i++) {
            platesBelow[i] = new Sprite(plateX,
                    symbolsBelow[i].getY()+ (symbolH-plateH)/2,
                    vX, vY,
                    plateInitialFrame,
                    plateBitmap);
        }
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


        for (int i = 0; i < symbolsHigher.length; i++) {
            if (symbolsHigher[i] != null) {
                if (symbolsHigher[i].getY() < 0 - symbolH) {
                    symbolsHigher[i] = null;
                }
            }
        }

        for (int i = 0; i < platesHigher.length; i++) {
            if (platesHigher[i] != null) {
                if (platesHigher[i].getY() < 0 - plateH) {
                    symbolsHigher[i] = null;
                }
            }
        }



        if (endOfBoard.getY() + endOfBoardH <= heightOfCanvas) {
            vY = 0;

            board.setVy(vY);
            endOfBoard.setVy(vY);

            for (Sprite aSymbolHigher : symbolsHigher) {
                if (aSymbolHigher != null) {
                    aSymbolHigher.setVy(vY);
                }
            }

            for (Sprite aPlateHigher : platesHigher) {
                if (aPlateHigher != null) {
                    aPlateHigher.setVy(vY);
                }
            }

            plate.setVy(vY);

            for (Sprite aSymbolBelow : symbolsBelow) {
                aSymbolBelow.setVy(vY);
            }

            for (Sprite aPlateBelow : platesBelow) {
                aPlateBelow.setVy(vY);
            }

            symbol.setVy(vY);

            isStopped = true;


        }

        endOfBoard.update(ms);

        Random random = new Random();

        int i = random.nextInt(10);
        int j = random.nextInt(10);

        board.shakeIt(i, j);

        endOfBoard.shakeIt(i, j);

        for (Sprite aSymbolHigher : symbolsHigher) {
            if (aSymbolHigher != null) {
                aSymbolHigher.update(ms);
                aSymbolHigher.shakeIt(i, j);
            }
        }

        plate.update(ms);
        plate.shakeIt(i, j);

        for (Sprite aPlateHigher : platesHigher) {
            if (aPlateHigher != null) {
                aPlateHigher.update(ms);
                aPlateHigher.shakeIt(i, j);
            }
        }

        symbol.shakeIt(i, j);


        for (Sprite aSymbolBelow : symbolsBelow) {
            aSymbolBelow.update(ms);
            aSymbolBelow.shakeIt(i, j);
        }


        for (Sprite aPlateBelow : platesBelow) {
            aPlateBelow.update(ms);
            aPlateBelow.shakeIt(i, j);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (Sprite aPlateHigher : platesHigher) {
            if (aPlateHigher != null) {
                aPlateHigher.draw(canvas);
            }
        }

        for (Sprite aSymbolHigher : symbolsHigher) {
            if (aSymbolHigher != null) {
                aSymbolHigher.draw(canvas);
            }
        }

        plate.draw(canvas);
        symbol.draw(canvas);

        for (Sprite aPlateBelow : platesBelow) {
            aPlateBelow.draw(canvas);
        }

        for (Sprite aSymbolBelow : symbolsBelow) {
            aSymbolBelow.draw(canvas);
        }



        endOfBoard.draw(canvas);
    }

    @Override
    int next() {

        if (!justCreated) {
            for (int i = symbolsHigher.length - 1; i >= 0; i--) {
                if (i == 0) {
                    symbolsHigher[i] = symbol;
                    symbolsHigherIds[i] = symbolId;
                    break;
                }

                symbolsHigher[i] = symbolsHigher[i - 1];
                symbolsHigherIds[i] = symbolsHigherIds[i - 1];
            }

            for (int i = platesHigher.length - 1; i >= 0; i--) {
                if (i == 0) {
                    platesHigher[i] = plate;
                    break;
                }

                platesHigher[i] = platesHigher[i - 1];
            }


            symbolId = symbolsBelowIds[0];
            symbol = symbolsBelow[0];

            plate.setBitmap(pressedPlateBitmap);
            plate = platesBelow[0];

            for (int i = 0; i < symbolsBelow.length - 1; i++) {
                symbolsBelow[i] = symbolsBelow[i + 1];
                symbolsBelowIds[i] = symbolsBelowIds[i + 1];
            }

            for (int i = 0; i < platesBelow.length - 1; i++) {
                platesBelow[i] = platesBelow[i + 1];
            }

            Random random = new Random();

            Rect symbolsInitialFrame = new Rect(0, 0, symbolW, symbolH);
            Rect plateInitialFrame = new Rect(0, 0, plateW, plateH);

            symbolsBelowIds[symbolsBelowIds.length - 1] = random.nextInt(symbolsBitmaps.length);
            symbolsBelow[symbolsBelow.length - 1] = new Sprite(symbolX,
                    symbol.getY() + symbolH * symbolsBelow.length + symbolGap * symbolsBelow.length,
                    vX, vY,
                    symbolsInitialFrame,
                    symbolsBitmaps[symbolsBelowIds[symbolsBelowIds.length - 1]]);

            platesBelow[platesBelow.length - 1] = new Sprite(plateX,
                    plate.getY() + plateH * platesBelow.length + symbolGap * platesBelow.length,
                    vX, vY,
                    plateInitialFrame,
                    plateBitmap);

        } else {
            justCreated = false;
        }

        return symbolId;

    }

    public boolean isBeyond() {
        return symbol.getY() <= -symbolH / 2;
    }

    public void setVy (double vY) {
        if (!isStopped) {
            this.vY = -vY;


            board.setVy(this.vY);

            for (Sprite symbol :
                    symbolsHigher) {
                if (symbol != null) {
                    symbol.setVy(this.vY);
                }
            }

            for (Sprite plate :
                    platesHigher) {
                if (plate != null) {
                    plate.setVy(this.vY);
                }
            }

            this.symbol.setVy(this.vY);
            plate.setVy(this.vY);

            for (Sprite symbol :
                    symbolsBelow) {
                symbol.setVy(this.vY);
            }

            for (Sprite plate :
                    platesBelow) {
                plate.setVy(this.vY);
            }

            endOfBoard.setVy(this.vY);
        }
    }
}
