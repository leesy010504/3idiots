package com.example.termproject;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

public class hard_stage extends AppCompatActivity {
    private customView customView;
    private int countAnswer = 0;
    private View clearView;
    private int lives = 1; //하드 스테이지 목숨
    private long timeleft = 30 * 1000; //하드 스테이지 타이머 60초
    private TextView timerTextView, livesTextView;
    private CountDownTimer timer;
    private boolean[] checkAnswer = new boolean[5];
    final float[][] positionX = {   {340.f, 480.f},
                                    {820.f, 970.f},
                                    {475.f, 695.f},
                                    {715.f, 830.f},
                                    {45.f, 190.f}};
    final float[][] positionY = {   {585.f, 685.f},
                                    {625.f, 745.f},
                                    {940.f, 1025.f},
                                    {895.f, 1025.f},
                                    {910.f, 1050.f}};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hard_stage);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        customView = new customView(this, positionX, positionY);
        frameLayout.addView(customView);

        timerTextView = findViewById(R.id.timerTextView);
        livesTextView = findViewById(R.id.livesTextView);

        livesTextView.setText("목숨: " + lives);
        timer = new CountDownTimer(timeleft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
                timerTextView.setText("타이머: " + timeleft / 1000 + "초");
            }

            @Override
            public void onFinish() {
                // 타이머가 0이 되면 게임 종료
                lives = 0;
                checkGameOver();
            }
        };
        timer.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (lives == 0) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float X = event.getX();
            float Y = event.getY();

            android.util.Log.i("좌표X", Float.toString(X));
            android.util.Log.i("좌표Y", Float.toString(Y));

            for (int i = 0; i < 5; ++i) {
                if (X >= positionX[i][0] && X <= positionX[i][1] &&
                        Y >= positionY[i][0] && Y <= positionY[i][1]) {
                    customView.setcorrectIdx(i);
                    if (!checkAnswer[i]) {
                        countAnswer++;
                        checkAnswer[i] = true;
                    }
                    if (countAnswer >= 5) {
                        clearView = (View) View.inflate(hard_stage.this, R.layout.clear, null);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(hard_stage.this);
                        dlg.setView(clearView);
                        dlg.show();
                    }
                    return super.onTouchEvent(event);
                }
            }
            lives--;
            livesTextView.setText("목숨: " + lives);
            checkGameOver(); // 목숨이 0인지 확인

        }
        return super.onTouchEvent(event);
    }

    private void checkGameOver() {
        if (lives <= 0) {
            // 씬 전환시 이용
            Toast.makeText(hard_stage.this, "게임 오버! 다시 도전하세요!", Toast.LENGTH_LONG).show();

            // 타이머 중지
            if (timer != null) {
                timer.cancel();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel(); // 액티비티 종료 시 타이머 중지
        }
    }

}
