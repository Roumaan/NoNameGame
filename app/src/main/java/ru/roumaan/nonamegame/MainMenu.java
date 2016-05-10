package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainMenu extends SurfaceView implements SurfaceHolder.Callback{

    private Context context; // Context нужен для получения размера экрана на 58
    private DrawThread drawThread;

    Sprite logo;
    Sprite chooseCampaignButton;
    Sprite endlessModeButton;
    Sprite tORButton;
    Sprite aboutButton;

    Bitmap logoBitmap;

    Bitmap button;
    Bitmap pressedButton;

    int buttonsW;
    int buttonsH;

    boolean firstUpdate;

    // Конструктор
    public MainMenu(Context context) {
        super(context);

        firstUpdate = true;

        this.context = context;

        getHolder().addCallback(this);
    }


    // Обновление
    private void update(int ms) {

        logo.update(ms);
        chooseCampaignButton.update(ms);
        endlessModeButton.update(ms);
        tORButton.update(ms);
        aboutButton.update(ms);
    }

    // Этот метод вызывается при касании экрана
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Если это нажатие то...
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if ((event.getX() > chooseCampaignButton.getX() &&
                    event.getX() < chooseCampaignButton.getX() + chooseCampaignButton.getFrameWidth())
                    &&
                    (event.getY() > chooseCampaignButton.getY() &&
                            event.getY() < chooseCampaignButton.getY() + chooseCampaignButton.getFrameHeight())) {

            } else if ((event.getX() > endlessModeButton.getX() &&
                    event.getX() < endlessModeButton.getX() + endlessModeButton.getFrameWidth())
                    &&
                    (event.getY() > endlessModeButton.getY() &&
                            event.getY() < endlessModeButton.getY() + endlessModeButton.getFrameHeight())) {

            } else if ((event.getX() > tORButton.getX() &&
                    event.getX() < tORButton.getX() + tORButton.getFrameWidth())
                    &&
                    (event.getY() > tORButton.getY() &&
                            event.getY() < tORButton.getY() + tORButton.getFrameHeight())) {

            } else if ((event.getX() > aboutButton.getX() &&
                    event.getX() < aboutButton.getX() + aboutButton.getFrameWidth())
                    &&
                    (event.getY() > aboutButton.getY() &&
                            event.getY() < aboutButton.getY() + aboutButton.getFrameHeight())) {

            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            chooseCampaignButton.setBitmap(button);
            endlessModeButton.setBitmap(button);
            tORButton.setBitmap(button);
            aboutButton.setBitmap(button);
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
                if (firstUpdate) {

                    Rect logoInitialFrame = new Rect(0, 0, 0, 0);
                    logo = new Sprite(0, 0,
                            0, 0,
                            logoInitialFrame,
                            logoBitmap);

                    buttonsW = 0;
                    buttonsH = 0;

                    Rect buttonsInitialFrame = new Rect(0, 0, buttonsW, buttonsH);

                    chooseCampaignButton = new Sprite(0, 0,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                    endlessModeButton = new Sprite(0, 0,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                    tORButton = new Sprite(0, 0,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                    aboutButton = new Sprite(0, 0,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                }

                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null && !firstUpdate) {
                    try {
                        logo.draw(canvas);
                        chooseCampaignButton.draw(canvas);
                        endlessModeButton.draw(canvas);
                        tORButton.draw(canvas);
                        aboutButton.draw(canvas);
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
