package com.example.termproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class select_level extends Activity {

    ImageView back_arrow, easy, medium, hard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);

        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        easy = (ImageView) findViewById(R.id.easy);
        medium = (ImageView) findViewById(R.id.medium);
        hard = (ImageView) findViewById(R.id.hard);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
