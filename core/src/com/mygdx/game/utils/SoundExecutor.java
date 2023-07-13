package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class SoundExecutor {

    static Music backSound = Gdx.audio.newMusic(Gdx.files.internal("tetris_theme.mp3"));
    static Music startSound = Gdx.audio.newMusic(Gdx.files.internal("start.mp3"));
    static Sound revSound = Gdx.audio.newSound(Gdx.files.internal("rev.mp3"));
    static Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop_sound.mp3"));
    static Sound lrSound = Gdx.audio.newSound(Gdx.files.internal("lr.mp3"));

    static Sound[] GameOverSound = {
            Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"))
    };

    public static void playBackSound() {
        if(MemoryLoader.loadMusicState()) {
            backSound.setLooping(true);
            backSound.play();
        }
    }

    public static void stopPlaying() {
        backSound.stop();
    }
    public static void resumePlaying() {
        backSound.play();
    }
    public static void pausePlaying() {
        backSound.pause();
    }

    public static void playGameOutSound() {
        if(MemoryLoader.loadMusicState()) {
            GameOverSound[MathUtils.random(0, GameOverSound.length - 1)].play();
        }
    }

    public static void playDropSound(){
        if(MemoryLoader.loadMusicState()) {
        dropSound.play();}
    }
    public static void playrevSound(){
        if(MemoryLoader.loadMusicState()) {
        revSound.play();}
    }
    public static void playStartSound(){
        if(MemoryLoader.loadMusicState()) {
            startSound.play();}
    }

    public static void pauseStartPlaying() {
        startSound.pause();
    }

    public static void stopStartSound(){
        startSound.stop();
    }

    public static void playlrSound(){
        if(MemoryLoader.loadMusicState()) {
        lrSound.play();}
    }


}