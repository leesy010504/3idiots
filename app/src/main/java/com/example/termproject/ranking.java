package com.example.termproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ranking extends AppCompatActivity {

    ImageView backArrow;
    private RankingDBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        dbHelper = new RankingDBHelper(this);
        backArrow = findViewById(R.id.back_arrow_ranking);

        backArrow.setOnClickListener(view -> finish());
        BgmManager.getInstance().start(this);

        // 랭킹 표시 업데이트
        updateRankings();
    }

    private void updateRankings() {
        // 각 난이도별 랭킹 가져오기
        List<RankingDBHelper.Ranking> easyRankings = dbHelper.getTop3Rankings("EASY");
        List<RankingDBHelper.Ranking> normalRankings = dbHelper.getTop3Rankings("NORMAL");
        List<RankingDBHelper.Ranking> hardRankings = dbHelper.getTop3Rankings("HARD");

        TextView firstRankText = findViewById(R.id.firstRankText);   // Easy 1등
        TextView secondRankText = findViewById(R.id.secondRankText); // Normal 1등
        TextView thirdRankText = findViewById(R.id.thirdRankText);   // Hard 1등

        // 기본값으로 모든 TextView를 "Blank"로 설정
        firstRankText.setText("Easy Mode: Blank");
        secondRankText.setText("Normal Mode: Blank");
        thirdRankText.setText("Hard Mode: Blank");

        try {
            // Easy 모드 1등
            if (easyRankings != null && !easyRankings.isEmpty()) {
                RankingDBHelper.Ranking easyFirst = easyRankings.get(0);
                firstRankText.setText(String.format("Easy Mode: %s (%d초)",
                        easyFirst.getNickname(), easyFirst.getClearTime()));
            }

            // Normal 모드 1등
            if (normalRankings != null && !normalRankings.isEmpty()) {
                RankingDBHelper.Ranking normalFirst = normalRankings.get(0);
                secondRankText.setText(String.format("Normal Mode: %s (%d초)",
                        normalFirst.getNickname(), normalFirst.getClearTime()));
            }

            // Hard 모드 1등
            if (hardRankings != null && !hardRankings.isEmpty()) {
                RankingDBHelper.Ranking hardFirst = hardRankings.get(0);
                thirdRankText.setText(String.format("Hard Mode: %s (%d초)",
                        hardFirst.getNickname(), hardFirst.getClearTime()));
            }
        } catch (Exception e) {
            // 예외 발생 시 기본값으로 설정
            firstRankText.setText("Easy Mode: Blank");
            secondRankText.setText("Normal Mode: Blank");
            thirdRankText.setText("Hard Mode: Blank");
        }
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        super.onDestroy();
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