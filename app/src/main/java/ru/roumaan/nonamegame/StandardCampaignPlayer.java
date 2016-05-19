package ru.roumaan.nonamegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class StandardCampaignPlayer extends SurfaceView implements SurfaceHolder.Callback  {

    private StandardBackground background;
    private StandardButtons buttons;
    private StandardBoard board;
    private GameTimer timer; // Полоска времени
    private Context context; // Context нужен для получения размера экрана на 58

    Timer t;
    ArcadeGameActivity activity;


    private int remainingTime; // Оставшееся время
    private int bonusTime;
    private int score; // Колличество очков

    private DrawThread drawThread;

    boolean firstUpdate;
    boolean gameOver;

    // Конструктор
    public StandardCampaignPlayer(Context context, ArcadeGameActivity activity, int remainingTime) {
        super(context);

        firstUpdate = true;

        this.context = context;
        this.activity = activity;

        this.remainingTime = remainingTime;
        bonusTime = 750;

        getHolder().addCallback(this);
    }


    // Обновление
    private void update(int ms) {

        if (remainingTime-ms <= 0 && !gameOver) {
            gameOver = true;
            gameOver();

        }

        remainingTime -= ms; // Уменьшение оставшегося времени

        // Обновление всех эллементов
        background.update(ms);
        buttons.update(ms);
        board.update(ms);
        timer.update(remainingTime);

    }

    // Этот метод вызывается при касании экрана
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Если это нажатие то...
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // Если кнопка правильная то...
            if (buttons.tap(event.getX(), event.getY())) {

                remainingTime += bonusTime;// Восполнить время на 500 мс

                bonusTime -= 0.75*score;

                // Перейти к другому символу
                int id = board.next();
                buttons.next(id);

                score++;// Увеличить колличество очков

            } /* А в противном случае */ else {
                gameOver();// Проигрыш
            }


        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            buttons.release();
        }
        return true;
    }

    // Проигрыш
    private void gameOver() {

        StandartCampaignGameOverActivity gameOverActivity = new StandartCampaignGameOverActivity();
        Intent intent = new Intent(context, gameOverActivity.getClass());
        intent.putExtra("score", score);
        context.startActivity(intent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.start();

        t = new Timer();
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
                if (firstUpdate) {
                    timer = new GameTimer(context, remainingTime, getWidth(), getHeight());
                    background = new StandardBackground(context, getWidth(), getHeight());
                    buttons = new StandardButtons(context, getWidth(), getHeight());
                    board = new StandardBoard(context, getWidth(), getHeight());

                    buttons.next(board.next()); // Следующий символ
                    firstUpdate = false;
                }

                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null && !firstUpdate) {
                    try {

                        timer.draw(canvas);
                        background.draw(canvas);
                        buttons.draw(canvas);
                        board.draw(canvas);

                        Paint mPaint = new Paint(); // Кисть с настройкой размера текста на 30 для отрисовки колличества очков на 59 строчке
                        mPaint.setTextSize(30);

                        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics(); // Размер экрана
                        canvas.drawText(Integer.toString(score), (float) (displaymetrics.widthPixels * 0.05), (float) (displaymetrics.heightPixels * 0.1), mPaint);
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
            if (getWidth() != 0 && getHeight() != 0 && !firstUpdate) {

                update(50); // Обновление

            }

        }


        @Override
        public void onFinish() {

        }
    }
}

