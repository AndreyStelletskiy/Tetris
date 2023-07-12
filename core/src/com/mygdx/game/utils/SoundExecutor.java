package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class SoundExecutor {

    static Sound backSound = Gdx.audio.newSound(Gdx.files.internal("tetris_theme.mp3"));
    static Sound revSound = Gdx.audio.newSound(Gdx.files.internal("rev.mp3"));
    static Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop_sound.mp3"));
    static Sound lrSound = Gdx.audio.newSound(Gdx.files.internal("lr.mp3"));
    static Sound[] tetrisSound = {
            Gdx.audio.newSound(Gdx.files.internal("tetris_theme.mp3"))
    };

    static Sound[] GameOverSound = {
            Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"))
    };

    public static void playBackSound() {
        long soundId = backSound.play();
        backSound.setLooping(soundId, true);
    }

    public static void stopPlaying() {
        backSound.stop();
    }
    public static void resumePlaying() {
        backSound.resume();
    }
    public static void pausePlaying() {
        backSound.pause();
    }

    public static void playGameOutSound() {
        GameOverSound[MathUtils.random(0, GameOverSound.length - 1)].play();
    }

    public static void playTetrisSound() {
        tetrisSound[MathUtils.random(0, tetrisSound.length - 1)].play();
    }
    public static void playDropSound(){
        dropSound.play();
    }
    public static void playrevSound(){
        revSound.play();
    }

    public static void playlrSound(){
        lrSound.play();
    }


}