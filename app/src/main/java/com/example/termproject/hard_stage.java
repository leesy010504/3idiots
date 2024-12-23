package com.example.termproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
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
                        // 게임 클리어 시 남은 시간을 초로 계산
                        int clearTimeInSeconds = (int)(timeleft / 1000);
                        timer.cancel(); // 타이머 중지

                        // ClearActivity로 클리어 시간 전달
                        Intent intent = new Intent(this, hardClearActivity.class);
                        intent.putExtra("clear_time", clearTimeInSeconds);
                        startActivity(intent);
                        finish(); // 현재 액티비티 종료
                    }
                    return super.onTouchEvent(event);
                }
            }

            lives--;
            livesTextView.setText("목숨: " + lives);
            checkGameOver();
        }
        return super.onTouchEvent(event);
    }

    private void checkGameOver() {
        if (lives <= 0) {
            Dialog failView = new Dialog(this);
            failView.setContentView(R.layout.fail);

            ImageView btnMain = failView.findViewById(R.id.btnMain);
            ImageView btnRestart = failView.findViewById(R.id.btnRestart);

            btnMain.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });

            btnRestart.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), hard_stage.class);
                startActivity(intent);
                finish();
            });

            failView.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            failView.show();

            Toast.makeText(hard_stage.this, "게임 오버! 다시 도전하세요!", Toast.LENGTH_LONG).show();

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


    @Override
    protected void onPause() {
        super.onPause();
        BgmManager.getInstance().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BgmManager.getInstance().resume();
    }
}
