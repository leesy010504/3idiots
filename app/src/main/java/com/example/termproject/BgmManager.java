package com.example.termproject;

import android.content.Context;
import android.media.MediaPlayer;

public class BgmManager {
    private static BgmManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isPaused;

    private BgmManager() {
    }

    public static BgmManager getInstance() {
        if (instance == null) {
            instance = new BgmManager();
        }
        return instance;
    }

    public void start(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bgm);
            mediaPlayer.setLooping(true);
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPaused = false;
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        }
    }

    public void resume() {
        if (mediaPlayer != null && isPaused) {
            mediaPlayer.start();
            isPaused = false;
        }
    }

}
