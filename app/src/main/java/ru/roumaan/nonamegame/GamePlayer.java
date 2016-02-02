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
    GameTimer timer;
    Context context;


    int startTime;
    int remainingTime;
    int score;
    int grade = 3;


    public GamePlayer(Context context , Background background, Buttons buttons, Board board, int remainingTime ) {
        super(context);

        this.context = context;
        this.background = background;
        this.buttons = buttons;
        this.board = board;
        this.remainingTime = remainingTime;
        buttons.next(board.next());
        timer = new GameTimer(context, remainingTime);
        startTime = remainingTime;


        Timer t = new Timer();
        t.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        timer.draw(canvas);
        background.draw(canvas);
        buttons.draw(canvas);
        board.draw(canvas);
        Paint mPaint = new Paint();
        mPaint.setTextSize(30);
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        canvas.drawText(Integer.toString(score), (float) (displaymetrics.widthPixels*0.05), (float) (displaymetrics.heightPixels*0.1), mPaint);


    }


    private void update(int ms) {

        if ((remainingTime < startTime*0.75 && grade == 3) || (remainingTime < startTime*0.30 && grade == 2) || remainingTime <= 0) {
            gradeDecrease();
        }

        if (grade==0) {
            gameOver();
        }

        remainingTime -= ms;
        background.update(ms);
        buttons.update(ms);
        board.update(ms);
        timer.update(remainingTime);

        invalidate();
    }


    private void gradeDecrease() {

        grade--;
        background.gradeDecrease();
        buttons.gradeDecrease();
        board.gradeDecrease();
        timer.gradeDecrease();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN&& buttons.touchCheck(event.getX(), event.getY())) {
            if (buttons.isTapRight(event.getX(), event.getY())) {

                remainingTime += 500;

                int id = board.next();
                score++;
                buttons.next(id);

            } else {
                gameOver();
            }

        }
        return true;
    }

    private void gameOver() {
        remainingTime = startTime;
        score = 0;
    }

    class Timer extends CountDownTimer {

        public Timer() {

            super(Integer.MAX_VALUE, 50);

        }

        @Override

        public void onTick(long millisUntilFinished) {

            update(50);
        }

        @Override

        public void onFinish() {

        }
    }
}

