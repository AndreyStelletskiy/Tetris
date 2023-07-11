package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class SoundExecutor {

    static Sound backSound = Gdx.audio.newSound(Gdx.files.internal("tetris_theme.mp3"));
    static Sound[] tetrisSound = {
            Gdx.audio.newSound(Gdx.files.internal("tetris_theme.mp3"))
    };

    public static void playBackSound() {
        long soundId = backSound.play();
        backSound.setLooping(soundId, true);
    }

    public static void stopPlaying() {
        backSound.stop();
    }

    public static void playTetrisSound() {
        tetrisSound[MathUtils.random(0, tetrisSound.length - 1)].play();
    }


}