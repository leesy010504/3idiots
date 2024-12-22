package com.example.termproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

public class normal_stage extends AppCompatActivity {
    private customView customView;
    private int countAnswer = 0;
    private View clearView;
    private int lives = 3; //노말 스테이지 목숨
    private long timeleft = 60 * 1000; //노말 스테이지 타이머 60초
    private TextView timerTextView, livesTextView;
    private CountDownTimer timer;
    private boolean[] checkAnswer = new boolean[5];
    final float[][] positionX = {   {235.f, 475.f},
                                    {415.f, 570.f},
                                    {590.f, 715.f},
                                    {120.f, 290.f},
                                    {760.f, 965.f}};
    final float[][] positionY = {   {530.f, 675.f},
                                    {765.f, 870.f},
                                    {560.f, 745.f},
                                    {895.f, 960.f},
                                    {870.f, 960.f}};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_stage);
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
        if (lives ==0) {
            return false; // 목숨 0이면 터치 무시
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float X = event.getX();
            float Y = event.getY();

            for (int i = 0; i < 5; ++i) {
                if (X >= positionX[i][0] && X <= positionX[i][1] &&
                        Y >= positionY[i][0] && Y <= positionY[i][1]) {
                    customView.setcorrectIdx(i);
                    if (!checkAnswer[i]) {
                        countAnswer++;
                        checkAnswer[i] = true;
                    }
                    if (countAnswer >= 5) {
                        clearView = (View) View.inflate(normal_stage.this, R.layout.clear, null);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(normal_stage.this);
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
            Toast.makeText(normal_stage.this, "게임 오버! 다시 도전하세요!", Toast.LENGTH_LONG).show();

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
