package com.example.termproject;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class easy_stage extends AppCompatActivity {
    private customView customView;
    private int countAnswer = 0;
    private View clearView;
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
            // 오답인 경우 목숨 감소 구현



        }
        return super.onTouchEvent(event);
    }
}
