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

        // 랭킹 표시 업데이트
        updateRankings();
    }

    private void updateRankings() {
        List<RankingDBHelper.Ranking> rankings = dbHelper.getTop3Rankings();

        TextView firstRankText = findViewById(R.id.firstRankText);
        TextView secondRankText = findViewById(R.id.secondRankText);
        TextView thirdRankText = findViewById(R.id.thirdRankText);

        // 기본값으로 모든 TextView를 "Blank"로 설정
        firstRankText.setText("Blank");
        secondRankText.setText("Blank");
        thirdRankText.setText("Blank");

        // 존재하는 랭킹 데이터만 표시
        try {
            if (rankings != null && !rankings.isEmpty()) {
                // 첫 번째 랭킹이 있으면 표시
                if (rankings.size() >= 1) {
                    RankingDBHelper.Ranking first = rankings.get(0);
                    firstRankText.setText(String.format("%s (%d초)",
                            first.getNickname(), first.getClearTime()));
                }

                // 두 번째 랭킹이 있으면 표시
                if (rankings.size() >= 2) {
                    RankingDBHelper.Ranking second = rankings.get(1);
                    secondRankText.setText(String.format("%s (%d초)",
                            second.getNickname(), second.getClearTime()));
                }

                // 세 번째 랭킹이 있으면 표시
                if (rankings.size() >= 3) {
                    RankingDBHelper.Ranking third = rankings.get(2);
                    thirdRankText.setText(String.format("%s (%d초)",
                            third.getNickname(), third.getClearTime()));
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 모든 TextView를 "Blank"로 설정
            firstRankText.setText("Blank");
            secondRankText.setText("Blank");
            thirdRankText.setText("Blank");
        }
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        super.onDestroy();
    }
}