package ru.roumaan.nonamegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


public class GamePlayer extends View {

    Background background;
    Buttons buttons;
    Board board;
    GameTimer timer; // Полоска времени
    Context context; // Context нужен для получения размера экрана на 56


    int startTime; // Стартовое время
    int remainingTime; // Оставшееся время
    int score; // Колличество очков
    int grade = 3; // Оценка 3 - золота и т.д.


    // Конструктор
    public GamePlayer(Context context , Background background, Buttons buttons, Board board, int remainingTime ) {
        super(context);

        this.context = context;
        this.background = background;
        this.buttons = buttons;
        this.board = board;
        this.remainingTime = remainingTime;
        buttons.next(board.next()); // Следующий символ
        timer = new GameTimer(context, remainingTime);
        startTime = remainingTime;


        Timer t = new Timer(); // Таймер (143) в отдельном потоке для запуска метода update()
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

        // Снижение оценки по времени
        if ((remainingTime < startTime * 0.50 && grade == 3) || (remainingTime < startTime * 0.25 && grade == 2) || (remainingTime <=0)) {
            gradeDecrease();
        }

        // Проигрыш по времени
        if (grade == 0) {
            gameOver();
        }


        remainingTime -= ms; // Уменьшение оставшегося времени

        // Обновление всех эллементов
        background.update(ms);
        buttons.update(ms);
        board.update(ms);
        timer.update(remainingTime);

        // Перерисовка
        invalidate();
    }

    // Снижение оценки
    private void gradeDecrease() {

        grade--;

        // Снижение оценки у всех эллементов
        background.gradeDecrease();
        buttons.gradeDecrease();
        board.gradeDecrease();
        timer.gradeDecrease();

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

        public Timer() {

            super(Integer.MAX_VALUE, 50); // Каждые 50мс вызывать onTick

        }

        @Override
        public void onTick(long millisUntilFinished) {
            update(50); // Обновление
        }

        @Override
        public void onFinish() {

        }
    }
}

