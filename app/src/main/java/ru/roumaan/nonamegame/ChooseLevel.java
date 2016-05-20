package ru.roumaan.nonamegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ChooseLevel extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    Context context;

    ChooseLevelActivity activity;

    Sprite[][] buttons;
    Sprite background;
    Sprite backButton;

    Bitmap backgroundBitmap;
    Bitmap backButtonBitmap;
    Bitmap backButtonPressedBitmap;

    boolean firstUpdate;

    // Конструктор
    public ChooseLevel(Context context, ChooseLevelActivity activity) {
        super(context);

        firstUpdate = true;
        this.context = context;
        this.activity = activity;

        getHolder().addCallback(this);
    }


    // Обновление
    private void update(int ms) {

        background.update(ms);
        backButton.update(ms);
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
            }

            backButton.setBitmap(backButtonBitmap);
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

                    /*
                    backgroundBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.background_full);
                    */
                    backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, canvas.getWidth(), canvas.getHeight(), false);

                    Rect backgroundInitialFrame = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
                    background = new Sprite(0, 0, 0, 0, backgroundInitialFrame, backgroundBitmap);

                    /*
                    backButtonBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.back_button);
                    backButtonBitmap = Bitmap.createScaledBitmap(backButtonBitmap, (int) (canvas.getHeight() * 0.093), (int) (canvas.getHeight() * 0.093), false);

                    backButtonPressedBitmap = BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.back_button_pressed);
                    backButtonPressedBitmap = Bitmap.createScaledBitmap(backButtonPressedBitmap, (int) (canvas.getHeight()*0.093), (int) (canvas.getHeight()*0.093), false);
                    */
                    Rect backButtonInitialFrame = new Rect(0, 0, backButtonBitmap.getWidth(), backButtonBitmap.getHeight());
                    backButton = new Sprite(canvas.getHeight()*0.075, canvas.getHeight() - canvas.getHeight()*0.075 - backButtonBitmap.getHeight() , 0, 0, backButtonInitialFrame, backButtonBitmap);

                    firstUpdate = false;
                }

                if (canvas != null && !firstUpdate) {
                    try {

                        background.draw(canvas);
                        backButton.draw(canvas);
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