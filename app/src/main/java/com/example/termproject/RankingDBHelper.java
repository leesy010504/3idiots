package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RankingDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RankingDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "rankings";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_TIME = "clear_time";

    public RankingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NICKNAME + " TEXT, "
                + COLUMN_TIME + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRanking(String nickname, int clearTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NICKNAME, nickname);
        values.put(COLUMN_TIME, clearTime);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Ranking> getTop3Rankings() {
        List<Ranking> rankings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_TIME + " ASC",
                "3"
        );

        while (cursor.moveToNext()) {
            String nickname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NICKNAME));
            int time = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            rankings.add(new Ranking(nickname, time));
        }

        cursor.close();
        db.close();
        return rankings;
    }

    public static class Ranking {
        private final String nickname;
        private final int clearTime;

        public Ranking(String nickname, int clearTime) {
            this.nickname = nickname;
            this.clearTime = clearTime;
        }

        public String getNickname() {
            return nickname;
        }

        public int getClearTime() {
            return clearTime;
        }
    }
}
