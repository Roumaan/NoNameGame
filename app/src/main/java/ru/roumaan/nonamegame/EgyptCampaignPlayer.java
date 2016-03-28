package ru.roumaan.nonamegame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class EgyptCampaignPlayer extends View {

    private EgyptBackground background;
    private EgyptButtons buttons;
    private EgyptBoard board;
    private GameTimer timer; // Полоска времени
    private Context context; // Context нужен для получения размера экрана на 58

    Sprite foreground;

    private int startTime; // Стартовое время
    private int remainingTime; // Оставшееся время
    private int silverGrade;
    private int bronzeGrade;

    private int speed;
    private double speedMultiplier;

    private int symbols;
    private int score; // Колличество очков

    private int grade;


    // Конструктор
    public EgyptCampaignPlayer(Context context, int remainingTime, int silverGrade, int bronzeGrade, int speed, double speedMultiplier, int symbols) {
        super(context);

        this.context = context;

        this.remainingTime = remainingTime;
        this.silverGrade = silverGrade;
        this.bronzeGrade = bronzeGrade;

        this.speed = speed;
        this.speedMultiplier = speedMultiplier;

        this.symbols = symbols;
        score = 0;

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

        //foreground.draw(canvas);


        Paint mPaint = new Paint(); // Кисть с настройкой размера текста на 30 для отрисовки колличества очков на 59 строчке
        mPaint.setTextSize(30);

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics(); // Размер экрана
        canvas.drawText(score + "/" + symbols, (float) (displaymetrics.widthPixels * 0.05), (float) (displaymetrics.heightPixels * 0.1), mPaint);


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

        // Перерисовка
        invalidate();
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

                if (score>=symbols) {
                    victory();
                }

            } /* А в противном случае */ else {
                gameOver();// Проигрыш
            }


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
                    timer = new GameTimer(context, remainingTime, getWidth(), getHeight());
                    background = new EgyptBackground(context, getWidth(), getHeight() /*, symbols, speed*/);
                    buttons = new EgyptButtons(context, getWidth(), getHeight());
                    board = new EgyptBoard(context, getWidth(), getHeight(), symbols, speed);

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

