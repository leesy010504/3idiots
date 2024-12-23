package com.example.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class select_level extends Activity {

    ImageView back_arrow, easy, normal, hard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);

        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        easy = (ImageView) findViewById(R.id.easy);
        normal = (ImageView) findViewById(R.id.normal);
        hard = (ImageView) findViewById(R.id.hard);

        BgmManager.getInstance().start(this);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), easy_stage.class);
                startActivity(intent);
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), normal_stage.class);
                startActivity(intent);
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), hard_stage.class);
                startActivity(intent);
            }
        });



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
