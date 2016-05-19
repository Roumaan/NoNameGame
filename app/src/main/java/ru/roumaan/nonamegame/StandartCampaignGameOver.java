package ru.roumaan.nonamegame;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StandartCampaignGameOver extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    Context context;

    Sprite background;
    Sprite board;
    Sprite backButton;
    Sprite restartButton;

    Sprite grade;


    Bitmap backgroundBitmap;
    Bitmap boardBitmap;

    Bitmap backButtonBitmap;
    Bitmap backButtonPressedBitmap;

    Bitmap restartButtonBitmap;
    Bitmap restartButtonPressedBitmap;

    Bitmap gradeBitmap;

    boolean firstUpdate;

    int gradeW;
    int gradeH;

    int score;

    // Конструктор
    public StandartCampaignGameOver(Context context, int score) {
        super(context);

        firstUpdate = true;
        this.context = context;
        this.score = score;

        getHolder().addCallback(this);
    }


    // Обновление
    private void update(int ms) {

        background.update(ms);
        backButton.update(ms);
        restartButton.update(ms);
        board.update(ms);

        grade.update(ms);
    }

    // Этот метод вызывается при касании экрана
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Если это нажатие то...
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if ((event.getX() > backButton.getX() &&
                    event.getX() < backButton.getX() + backButton.getFrameWidth())
                    &&
                    (event.getY() > backButton.getY() &&
                            event.getY() < backButton.getY() + backButton.getFrameHeight())) {
                backButton.setBitmap(backButtonPressedBitmap);
            } else if ((event.getX() > restartButton.getX() &&
                    event.getX() < restartButton.getX() + restartButton.getFrameWidth())
                    &&
                    (event.getY() > restartButton.getY() &&
                            event.getY() < restartButton.getY() + restartButton.getFrameHeight())) {
                restartButton.setBitmap(restartButtonPressedBitmap);
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if ((event.getX() > backButton.getX() &&
                    event.getX() < backButton.getX() + backButton.getFrameWidth())
                    &&
                    (event.getY() > backButton.getY() &&
                            event.getY() < backButton.getY() + backButton.getFrameHeight())) {

                MainActivity activity = new MainActivity();
                Intent intent = new Intent(context, activity.getClass());
                context.startActivity(intent);
            } else if ((event.getX() > restartButton.getX() &&
                    event.getX() < restartButton.getX() + restartButton.getFrameWidth())
                    &&
                    (event.getY() > restartButton.getY() &&
                            event.getY() < restartButton.getY() + restartButton.getFrameHeight())) {

                ArcadeGameActivity activity = new ArcadeGameActivity();
                Intent intent = new Intent(context, activity.getClass());
                context.startActivity(intent);
            }

            backButton.setBitmap(backButtonBitmap);
            restartButton.setBitmap(restartButtonBitmap);
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.start();

        Timer t = new Timer();
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }

    }

    public class DrawThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private volatile boolean running = true;//флаг для остановки потока

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void requestStop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {

                Canvas canvas = surfaceHolder.lockCanvas();

                if (firstUpdate) {

                    backgroundBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.background_full);
                    backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, canvas.getWidth(), canvas.getHeight(), false);

                    Rect backgroundInitialFrame = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
                    background = new Sprite(0, 0, 0, 0, backgroundInitialFrame, backgroundBitmap);



                    backButtonBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.back_button);
                    backButtonBitmap = Bitmap.createScaledBitmap(backButtonBitmap, (int) (canvas.getHeight() * 0.093), (int) (canvas.getHeight() * 0.093), false);

                    backButtonPressedBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.back_button_pressed);
                    backButtonPressedBitmap = Bitmap.createScaledBitmap(backButtonPressedBitmap, backButtonBitmap.getWidth(), backButtonBitmap.getHeight(), false);

                    Rect backButtonInitialFrame = new Rect(0, 0, backButtonBitmap.getWidth(), backButtonBitmap.getHeight());
                    backButton = new Sprite(canvas.getHeight()*0.075, canvas.getHeight() - canvas.getHeight()*0.075 - backButtonBitmap.getHeight() , 0, 0, backButtonInitialFrame, backButtonBitmap);



                    restartButtonBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.restart_button);
                    restartButtonBitmap = Bitmap.createScaledBitmap(restartButtonBitmap, backButtonBitmap.getWidth(), backButtonBitmap.getHeight(), false);

                    restartButtonPressedBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.restart_button_pressed);
                    restartButtonPressedBitmap = Bitmap.createScaledBitmap(restartButtonPressedBitmap, (int) (canvas.getHeight() * 0.093), (int) (canvas.getHeight()*0.093), false);

                    Rect restartButtonInitialFrame = new Rect(0, 0, backButtonBitmap.getWidth(), backButtonBitmap.getHeight());
                    restartButton = new Sprite(canvas.getWidth()-canvas.getHeight()*0.075-restartButtonBitmap.getWidth(), canvas.getHeight() - canvas.getHeight()*0.075 - restartButtonBitmap.getHeight() , 0, 0, restartButtonInitialFrame, restartButtonBitmap);



                    boardBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.standart_board);

                    boardBitmap = Bitmap.createScaledBitmap(boardBitmap, (int) (canvas.getWidth()*0.4), (int) ((canvas.getWidth()*0.4)*1.5), false);

                    Rect boardInitialFrame = new Rect(0, 0, (int) (canvas.getWidth()*0.4), (int) ((canvas.getWidth()*0.4)*1.5));
                    board = new Sprite((canvas.getWidth()-boardBitmap.getWidth())/2, (canvas.getHeight()-boardBitmap.getHeight())/2, 0, 0, boardInitialFrame, boardBitmap );

                    gradeW = board.getFrameWidth()/2;
                    gradeH = (int) (board.getFrameHeight()*0.4);

                    if (score > 100) {
                        gradeBitmap = BitmapFactory.decodeResource(
                                context.getResources(),
                                R.drawable.grade_a);
                    } else if (score > 75) {
                        gradeBitmap = BitmapFactory.decodeResource(
                                context.getResources(),
                                R.drawable.grade_b);
                    } else if (score > 50) {
                        gradeBitmap = BitmapFactory.decodeResource(
                                context.getResources(),
                                R.drawable.grade_c);
                    } else if (score > 35) {
                        gradeBitmap = BitmapFactory.decodeResource(
                                context.getResources(),
                                R.drawable.grade_d);
                    } else {
                        gradeBitmap = BitmapFactory.decodeResource(
                                context.getResources(),
                                R.drawable.grade_f);
                    }

                    gradeBitmap = Bitmap.createScaledBitmap(gradeBitmap, gradeW, gradeH, false);

                    Rect gradeInitialFrame = new Rect(0, 0, gradeW, gradeH);
                    grade = new Sprite(board.getX() + (board.getFrameWidth()-gradeW)/2,board.getY() + (board.getFrameHeight() - 32) / 2 - 32 + board.getFrameHeight() * 0.10 - gradeH, 0, 0, gradeInitialFrame, gradeBitmap);

                    firstUpdate = false;
                }

                if (canvas != null && !firstUpdate) {
                    try {

                        background.draw(canvas);
                        backButton.draw(canvas);
                        restartButton.draw(canvas);
                        board.draw(canvas);

                        grade.draw(canvas);

                        Paint paint = new Paint();
                        paint.setTextSize(32);

                        String text = context.getText(R.string.ResultText).toString()+ " " + score;

                        float[] widthsOfChars = new float[text.length()];
                        paint.getTextWidths(text, widthsOfChars);
                        float widthOfText = 0;

                        for (float widthOfChar :
                                widthsOfChars) {
                            widthOfText+=widthOfChar;
                        }

                        canvas.drawText(text, (float) (board.getX() + (board.getFrameWidth() - widthOfText) / 2),
                                (float) (board.getY() + (board.getFrameHeight() - 32) / 2 + board.getFrameHeight() * 0.15), paint);

                        text = context.getText(R.string.RecordText).toString()+ " " + score;

                        widthsOfChars = new float[text.length()];
                        paint.getTextWidths(text, widthsOfChars);
                        widthOfText = 0;

                        for (float widthOfChar :
                                widthsOfChars) {
                            widthOfText+=widthOfChar;
                        }

                        canvas.drawText(text, (float) (board.getX() + (board.getFrameWidth() - widthOfText) / 2),
                                (float) (board.getY() + (board.getFrameHeight() - 32)/2 + 36 +  board.getFrameHeight()*0.15), paint);

                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }


        }
    }

    // Таймер
    class Timer extends CountDownTimer {


        public Timer() {
            super(Integer.MAX_VALUE, 50); // Каждые 100мс вызывать onTick

        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (!firstUpdate) {

                update(50); // Обновление

            }

        }


        @Override
        public void onFinish() {

        }
    }
}