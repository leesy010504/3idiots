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

import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class easy_stage extends AppCompatActivity {
    private customView customView;
    private int countAnswer = 0;
    private View clearView;

    private int lives = 5; //이지 스테이지 목숨
    private long timeleft = 90 * 1000; //이지 스테이지 타이머 90초

    private TextView timerTextView, livesTextView;
    private CountDownTimer timer;

    private boolean[] checkAnswer = new boolean[5];
    final float[][] positionX = {   {45.f, 190.f},
                                    {275.f, 425.f},
                                    {590.f, 700.f},
                                    {625.f, 770.f},
                                    {960.f, 1050.f}};
    final float[][] positionY = {   {980.f, 1100.f},
                                    {605.f, 705.f},
                                    {540.f, 755.f},
                                    {850.f, 930.f},
                                    {860.f, 1020.f}};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_stage);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        customView = new customView(this, positionX, positionY);
        frameLayout.addView(customView);

        timerTextView = findViewById(R.id.timerTextView);
        livesTextView = findViewById(R.id.livesTextView);

        livesTextView.setText("목숨: " + lives);

        BgmManager.getInstance().start(this);

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
                        clearView = (View) View.inflate(easy_stage.this, R.layout.clear, null);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(easy_stage.this);
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
            Dialog failView = new Dialog(this);
            failView.setContentView(R.layout.fail);

            ImageView btnMain = (ImageView) failView.findViewById(R.id.btnMain);
            ImageView btnRestart = (ImageView) failView.findViewById(R.id.btnRestart);

            btnMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            btnRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), easy_stage.class);
                    startActivity(intent);
                    finish();
                }
            });

            failView.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            failView.show();

            // 씬 전환시 이용
            Toast.makeText(easy_stage.this, "게임 오버! 다시 도전하세요!", Toast.LENGTH_LONG).show();

            // 타이머 중지
            if (timer != null) {
                timer.cancel();
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel(); // 액티비티 종료 시 타이머 중지
        }
    }
}
