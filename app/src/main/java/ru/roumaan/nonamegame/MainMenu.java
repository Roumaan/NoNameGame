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

public class MainMenu extends SurfaceView implements SurfaceHolder.Callback{

    private DrawThread drawThread;

    Context context;
    MainActivity mainActivity;

    Sprite background;
    Sprite logo;
    Sprite chooseCampaignButton;
    Sprite endlessModeButton;
    Sprite tORButton;
    Sprite aboutButton;

    Bitmap backgroundBitmap;

    Bitmap logoBitmap;

    Bitmap button;
    Bitmap pressedButton;

    int buttonsW;
    int buttonsH;

    int buttonsGap;

    double buttonsX;

    boolean firstUpdate;

    // Конструктор
    public MainMenu(Context context, MainActivity mainActivity) {
        super(context);

        firstUpdate = true;
        this.context = context;
        this.mainActivity = mainActivity;

        getHolder().addCallback(this);
    }


    // Обновление
    private void update(int ms) {

        background.update(ms);

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

                chooseCampaignButton.setBitmap(pressedButton);
            } else if ((event.getX() > endlessModeButton.getX() &&
                    event.getX() < endlessModeButton.getX() + endlessModeButton.getFrameWidth())
                    &&
                    (event.getY() > endlessModeButton.getY() &&
                            event.getY() < endlessModeButton.getY() + endlessModeButton.getFrameHeight())) {

                endlessModeButton.setBitmap(pressedButton);
            } else if ((event.getX() > tORButton.getX() &&
                    event.getX() < tORButton.getX() + tORButton.getFrameWidth())
                    &&
                    (event.getY() > tORButton.getY() &&
                            event.getY() < tORButton.getY() + tORButton.getFrameHeight())) {

                tORButton.setBitmap(pressedButton);
            } else if ((event.getX() > aboutButton.getX() &&
                    event.getX() < aboutButton.getX() + aboutButton.getFrameWidth())
                    &&
                    (event.getY() > aboutButton.getY() &&
                            event.getY() < aboutButton.getY() + aboutButton.getFrameHeight())) {

                aboutButton.setBitmap(pressedButton);
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if ((event.getX() > chooseCampaignButton.getX() &&
                    event.getX() < chooseCampaignButton.getX() + chooseCampaignButton.getFrameWidth())
                    &&
                    (event.getY() > chooseCampaignButton.getY() &&
                            event.getY() < chooseCampaignButton.getY() + chooseCampaignButton.getFrameHeight())) {

                ChooseCampaignActivity activity = new ChooseCampaignActivity();
                Intent intent = new Intent(context, activity.getClass());
                context.startActivity(intent);
                mainActivity.finish();

            } else if ((event.getX() > endlessModeButton.getX() &&
                    event.getX() < endlessModeButton.getX() + endlessModeButton.getFrameWidth())
                    &&
                    (event.getY() > endlessModeButton.getY() &&
                            event.getY() < endlessModeButton.getY() + endlessModeButton.getFrameHeight())) {

                ArcadeGameActivity activity = new ArcadeGameActivity();
                Intent intent = new Intent(context, activity.getClass());
                context.startActivity(intent);
                mainActivity.finish();
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

                AboutActivity activity = new AboutActivity();
                Intent intent = new Intent(context, activity.getClass());
                context.startActivity(intent);
                mainActivity.finish();
            }

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

                Canvas canvas = surfaceHolder.lockCanvas();

                if (firstUpdate) {

                     backgroundBitmap = BitmapFactory.decodeResource(
                             context.getResources(),
                             R.drawable.background_full);
                     backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, canvas.getWidth(), canvas.getHeight(), false);

                     Rect backgroundInitialFrame = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
                     background = new Sprite(0, 0, 0, 0, backgroundInitialFrame, backgroundBitmap);

                     logoBitmap = BitmapFactory.decodeResource(
                             context.getResources(),
                             R.drawable.logo);
                     logoBitmap = Bitmap.createScaledBitmap(logoBitmap, (int) (canvas.getWidth()*0.848), (int) (canvas.getHeight()*0.121), false);

                     Rect logoInitialFrame = new Rect(0, 0, logoBitmap.getWidth(), logoBitmap.getHeight());
                     logo = new Sprite((canvas.getWidth()-logoBitmap.getWidth())/2, canvas.getHeight()*0.111,
                            0, 0,
                            logoInitialFrame,
                            logoBitmap);

                     buttonsW = (int) (canvas.getWidth()*0.5354);
                     buttonsH = (int) (canvas.getHeight()*0.069);

                     buttonsX = (canvas.getWidth()-buttonsW)/2;
                     buttonsGap = (int) (canvas.getHeight()*0.018);

                     button = BitmapFactory.decodeResource(
                             context.getResources(),
                             R.drawable.main_menu_button);
                     button = Bitmap.createScaledBitmap(button, buttonsW, buttonsH, false);

                     pressedButton = BitmapFactory.decodeResource(
                             context.getResources(),
                             R.drawable.main_menu_button_pressed);
                     pressedButton = Bitmap.createScaledBitmap(pressedButton, buttonsW, buttonsH, false);

                     Rect buttonsInitialFrame = new Rect(0, 0, buttonsW, buttonsH);

                     chooseCampaignButton = new Sprite(buttonsX, (canvas.getHeight()-buttonsGap*3-buttonsH*4)/2,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                     endlessModeButton = new Sprite(buttonsX, chooseCampaignButton.getY()+buttonsH+buttonsGap,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                     tORButton = new Sprite(buttonsX, endlessModeButton.getY()+buttonsH+buttonsGap,
                            0, 0,
                            buttonsInitialFrame,
                             button);

                     aboutButton = new Sprite(buttonsX, tORButton.getY()+buttonsH+buttonsGap,
                            0, 0,
                            buttonsInitialFrame,
                            button);

                     firstUpdate = false;
                }

                if (canvas != null && !firstUpdate) {
                    try {

                        background.draw(canvas);

                        logo.draw(canvas);
                        chooseCampaignButton.draw(canvas);
                        endlessModeButton.draw(canvas);
                        tORButton.draw(canvas);
                        aboutButton.draw(canvas);

                        Paint paint = new Paint();
                        paint.setTextSize(buttonsH / 2);



                        String text = context.getText(R.string.ChooseCampaignText).toString();

                        float[] widthsOfChars = new float[text.length()];
                        paint.getTextWidths(text, widthsOfChars);
                        float widthOfText = 0;

                        for (float widthOfChar :
                                widthsOfChars) {
                            widthOfText+=widthOfChar;
                        }

                        canvas.drawText(text, (float) (chooseCampaignButton.getX() + (buttonsW-widthOfText)/2),
                                (float) (chooseCampaignButton.getY() + buttonsH/2 + buttonsH*0.15), paint);

                        text = context.getText(R.string.EndlessModeText).toString();

                        widthsOfChars = new float[text.length()];
                        paint.getTextWidths(text, widthsOfChars);
                        widthOfText = 0;

                        for (float widthOfChar :
                                widthsOfChars) {
                            widthOfText+=widthOfChar;
                        }

                        canvas.drawText(text, (float) (endlessModeButton.getX() + (buttonsW-widthOfText)/2),
                                (float) (endlessModeButton.getY() + buttonsH/2 + buttonsH*0.15), paint);

                        text = context.getText(R.string.TableOfRecordsText).toString();

                        widthsOfChars = new float[text.length()];
                        paint.getTextWidths(text, widthsOfChars);
                        widthOfText = 0;

                        for (float widthOfChar :
                                widthsOfChars) {
                            widthOfText+=widthOfChar;
                        }

                        canvas.drawText(text, (float) (tORButton.getX() + (buttonsW-widthOfText)/2),
                                (float) (tORButton.getY() + buttonsH/2 + buttonsH*0.15), paint);

                        text = context.getText(R.string.AboutText).toString();

                        widthsOfChars = new float[text.length()];
                        paint.getTextWidths(text, widthsOfChars);
                        widthOfText = 0;

                        for (float widthOfChar :
                                widthsOfChars) {
                            widthOfText+=widthOfChar;
                        }

                        canvas.drawText(text, (float) (aboutButton.getX() + (buttonsW-widthOfText)/2),
                                (float) (aboutButton.getY() + buttonsH/2 + buttonsH*0.15), paint);

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
