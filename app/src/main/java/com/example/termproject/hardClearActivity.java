package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class hardClearActivity extends AppCompatActivity {
    private RankingDBHelper dbHelper;
    private static final String DIFFICULTY = "HARD";
    private int clearTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear);

        dbHelper = new RankingDBHelper(this);
        clearTime = getIntent().getIntExtra("clear_time", 0);

        TextView firstRankText = findViewById(R.id.firstRankText);
        TextView secondRankText = findViewById(R.id.secondRankText);
        TextView thirdRankText = findViewById(R.id.thirdRankText);
        EditText nicknameInput = findViewById(R.id.nicknameInput);
        ImageView enterButton = findViewById(R.id.enterButton);

        updateRankings();

        enterButton.setOnClickListener(v -> {
            String nickname = nicknameInput.getText().toString();
            if (!nickname.isEmpty()) {
                dbHelper.insertRanking(nickname, clearTime, DIFFICULTY);
                updateRankings();
                nicknameInput.getText().clear();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRankings() {
        List<RankingDBHelper.Ranking> rankings = dbHelper.getTop3Rankings(DIFFICULTY);

        TextView firstRankText = findViewById(R.id.firstRankText);
        TextView secondRankText = findViewById(R.id.secondRankText);
        TextView thirdRankText = findViewById(R.id.thirdRankText);

        firstRankText.setText(rankings.size() > 0 ?
                rankings.get(0).getNickname() + " (" + rankings.get(0).getClearTime() + "초)" : "Blank");

        secondRankText.setText(rankings.size() > 1 ?
                rankings.get(1).getNickname() + " (" + rankings.get(1).getClearTime() + "초)" : "Blank");

        thirdRankText.setText(rankings.size() > 2 ?
                rankings.get(2).getNickname() + " (" + rankings.get(2).getClearTime() + "초)" : "Blank");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}