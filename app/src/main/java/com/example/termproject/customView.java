package com.example.termproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

public class customView extends View {
    private final Paint paint = new Paint();
    private final float[][] positionX, positionY;
    private boolean[] draw = new boolean[5];

    public customView(Context context, float[][] _positionX, float[][] _positionY) {
        super(context);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        positionX = _positionX;
        positionY = _positionY;
    }

    public void setcorrectIdx(int idx) {
        this.draw[idx] = true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int[] location = new int[2];
        getLocationOnScreen(location);
        for(int i = 0; i < 5; ++i) {
            if(draw[i]) {
                float posX = (positionX[i][0] + positionX[i][1]) / 2;
                float posY = (positionY[i][0] + positionY[i][1]) / 2;
                int radius = (int) Math.sqrt(   Math.pow((positionX[i][1] - positionX[i][0]) / 2, 2) +
                        Math.pow((positionY[i][1] - positionY[i][0]) / 2, 2));
                canvas.drawCircle(posX - location[0], posY - location[1], radius, paint);
            }
        }
        invalidate();
    }
}
