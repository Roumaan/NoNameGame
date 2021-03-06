package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class StandardCampaignPlayer extends View {

    StandardBackground background;
    StandardButtons buttons;
    StandardBoard board;
    StandardGameTimer timer; // Полоска времени
    Context context; // Context нужен для получения размера экрана на 58


    int startTime; // Стартовое время
    int remainingTime; // Оставшееся время
    int score; // Колличество очков



    // Конструктор
    public StandardCampaignPlayer(Context context, int remainingTime) {
        super(context);

        this.context = context;

        this.remainingTime = remainingTime;
        startTime = remainingTime;

        Timer t = new Timer(); // Таймер (116) в отдельном потоке для запуска метода update()
        t.start();

    }

    // Отрисовка
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Отрисовка кнопок, полоски времени, фона, доски
        timer.draw(canvas);
        background.draw(canvas);
        buttons.draw(canvas);
        board.draw(canvas);

        Paint mPaint = new Paint(); // Кисть с настройкой размера текста на 30 для отрисовки колличества очков на 59 строчке
        mPaint.setTextSize(30);

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics(); // Размер экрана
        canvas.drawText(Integer.toString(score), (float) (displaymetrics.widthPixels * 0.05), (float) (displaymetrics.heightPixels * 0.1), mPaint);


    }

    // Обновление
    private void update(int ms) {

        Log.i("abc", "2");
        if (remainingTime <= 0) {
            gameOver();
        }
        remainingTime -= ms; // Уменьшение оставшегося времени

        // Обновление всех эллементов
        background.update(ms);
        buttons.update(ms);
        board.update(ms);
        timer.update(ms, remainingTime);

        Log.i("abc", "3");
        // Перерисовка
        invalidate();
        Log.i("abc", "4");
    }

    // Этот метод вызывается при касании экрана
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Если это нажатие и по кнопке то...
        if (event.getAction() == MotionEvent.ACTION_DOWN&& buttons.touchCheck(event.getX(), event.getY())) {

            // Если кнопка правильная то...
            if (buttons.isTapRight(event.getX(), event.getY())) {

                remainingTime += 500;// Восполнить время на 500 мс

                // Перейти к другому символу
                int id = board.next();
                buttons.next(id);

                score++;// Увеличить колличество очков

            } /* А в противном случае */ else {
                gameOver();// Проигрыш
            }


        }
        return true;
    }

    // Проигрыш
    private void gameOver() {
        remainingTime = startTime;// Возвращение полоски времени к начальному состоянию
        score = 0;
    }

    // Таймер
    class Timer extends CountDownTimer {
        boolean firstUpdate;
        public Timer() {
            super(Integer.MAX_VALUE, 50); // Каждые 50мс вызывать onTick
            firstUpdate = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(getWidth() != 0 && getHeight() != 0) {

                if (firstUpdate) {
                    timer = new StandardGameTimer(context, remainingTime, getWidth(), getHeight());
                    background = new StandardBackground(context, getWidth(), getHeight());
                    buttons = new StandardButtons(context, getWidth(), getHeight());
                    board = new StandardBoard(context, getWidth(), getHeight());

                    buttons.next(board.next()); // Следующий символ

                    firstUpdate = false;
                }


                update(50); // Обновление

            }

        }

        @Override
        public void onFinish() {

        }
    }

}

