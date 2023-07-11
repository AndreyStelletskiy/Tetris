package com.mygdx.game.utils;

import static com.mygdx.game.utils.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryLoader {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");


    public static void saveScore(int score){
        prefs.putString("score", String.valueOf(score));
        prefs.flush();
    }
    public static int loadScore(){
        if(prefs.contains("score")) return Integer.parseInt(prefs.getString("score"));
        return 0;
    }


}