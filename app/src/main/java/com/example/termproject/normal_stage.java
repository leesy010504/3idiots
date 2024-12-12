package com.example.termproject;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class normal_stage extends AppCompatActivity {
    private customView customView;
    private int countAnswer = 0;
    private View clearView;
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
                        clearView = (View) View.inflate(normal_stage.this, R.layout.clear, null);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(normal_stage.this);
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
