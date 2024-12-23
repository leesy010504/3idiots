package com.example.termproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ranking extends AppCompatActivity {

    ImageView backArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        BgmManager.getInstance().start(this);

        backArrow = (ImageView) findViewById(R.id.back_arrow_ranking);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
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
