package com.example.termproject;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class hard_stage extends AppCompatActivity {
    private customView customView;
    private int countAnswer = 0;
    private View clearView;
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
            // 오답인 경우 목숨 감소 구현



        }
        return super.onTouchEvent(event);
    }

}
