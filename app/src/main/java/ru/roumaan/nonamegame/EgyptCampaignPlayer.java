package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class EgyptCampaignPlayer extends SurfaceView implements SurfaceHolder.Callback {

    private EgyptBackground background;
    private EgyptButtons buttons;
    private EgyptBoard board;
    private GameTimer timer; // Полоска времени
    private Context context; // Context нужен для получения размера экрана на 58

    //Sprite foreground;

    private int startTime; // Стартовое время
    private int remainingTime; // Оставшееся время
    private int silverGrade;
    private int bronzeGrade;

    private int speed;
    private double speedMultiplier;

    private int symbols;
    private int score; // Колличество очков

    private int grade;

    private DrawThread drawThread;

    boolean firstUpdate;

    // Конструктор
    public EgyptCampaignPlayer(Context context, int remainingTime, int silverGrade, int bronzeGrade, int speed, double speedMultiplier, int symbols) {
        super(context);

        firstUpdate = true;

        this.context = context;

        this.remainingTime = remainingTime;
        this.silverGrade = silverGrade;
        this.bronzeGrade = bronzeGrade;

        this.speed = speed;
        this.speedMultiplier = speedMultiplier;

        this.symbols = symbols;
        score = 0;

        startTime = remainingTime;

        getHolder().addCallback(this);
    }


    // Обновление
    private void update(int ms) {
        if(remainingTime < silverGrade && grade == 3) {
            gradeDecrease();
        } if(remainingTime < bronzeGrade && grade == 2) {
            gradeDecrease();
        } else if (remainingTime <= 0) {
            gameOver();
        }

        speed*= speedMultiplier;

        if (board.isBeyond()) {
            gameOver();
        }

        remainingTime -= ms; // Уменьшение оставшегося времени

        // Обновление всех эллементов
        background.update(ms);
        buttons.update(ms);
        board.update(ms);
        timer.update(remainingTime);

        speed *= speedMultiplier;

        board.setVy(speed);
    }

    // Этот метод вызывается при касании экрана
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Если это нажатие и по кнопке то...
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // Если кнопка правильная то...
            if (buttons.tap(event.getX(), event.getY())) {

                remainingTime += 500;// Восполнить время на 500 мс

                // Перейти к другому символу
                int id = board.next();
                buttons.next(id);

                score++;// Увеличить колличество очков

                if (score>=symbols) {
                    victory();
                }

            } /* А в противном случае */ else {
                gameOver();// Проигрыш
            }


        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            buttons.release();
        }
        
        return true;
    }

    private void gradeDecrease () {
        grade--;

        /*
        timer.setGrade(grade);
        */

        background.setGrade(grade);
        board.setGrade(grade);
        buttons.setGrade(grade);

    }

    // Проигрыш
    private void gameOver() {
        remainingTime = startTime;// Возвращение полоски времени к начальному состоянию
        score = 0;
    }

    private void victory() {

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
                if (firstUpdate) {
                    timer = new GameTimer(context, remainingTime, getWidth(), getHeight());
                    background = new EgyptBackground(context, getWidth(), getHeight() /*, symbols, speed*/);
                    buttons = new EgyptButtons(context, getWidth(), getHeight());

                    Bitmap endOfBoard = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.end_of_egypt_board1);
                    board = new EgyptBoard(context, getWidth(), getHeight(), symbols, speed, endOfBoard);

                    buttons.next(board.next()); // Следующий символ

                    firstUpdate = false;
                }

                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null && !firstUpdate) {
                    try {

                        // Отрисовка кнопок, полоски времени, фона, доски
                        timer.draw(canvas);
                        background.draw(canvas);
                        board.draw(canvas);
                        buttons.draw(canvas);


                        //foreground.draw(canvas);


                        Paint mPaint = new Paint(); // Кисть с настройкой размера текста на 30 для отрисовки колличества очков на 59 строчке
                        mPaint.setTextSize(30);

                        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics(); // Размер экрана
                        canvas.drawText(score + "/" + symbols, (float) (displaymetrics.widthPixels * 0.05), (float) (displaymetrics.heightPixels * 0.1), mPaint);
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
            if(getWidth() != 0 && getHeight() != 0 && !firstUpdate) {

                update(50); // Обновление

            }

        }

        @Override
        public void onFinish() {

        }
    }

}

