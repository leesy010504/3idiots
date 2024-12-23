package com.example.termproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
        if (lives == 0) {
            return false;
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
                        int clearTimeInSeconds = (int)(timeleft / 1000);
                        timer.cancel();

                        Intent intent = new Intent(this, ClearActivity.class);
                        intent.putExtra("clear_time", clearTimeInSeconds);
                        startActivity(intent);
                        finish();
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
                Intent intent = new Intent(getApplicationContext(), easy_stage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });

            failView.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            failView.show();

            Toast.makeText(normal_stage.this, "게임 오버! 다시 도전하세요!", Toast.LENGTH_LONG).show();

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
