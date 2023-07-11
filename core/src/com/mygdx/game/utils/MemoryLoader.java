package com.mygdx.game.utils;

import static com.mygdx.game.utils.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryLoader {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");

    public static void saveMusicState(boolean isPlaying) {
        prefs.putString("musicState", String.valueOf(isPlaying));
        prefs.flush();
    }

    public static void saveDifficultyLevelWight(DifficultyMapWight difficultyLevel) {
        prefs.putString("difficultyLevel", String.valueOf(difficultyLevel.getDifficultyMapWightIdx()));
        prefs.flush();
    }

    public static DifficultyMapWight loadDifficultyMapWight() {
        if (prefs.contains("difficultyLevel")) {
            int difficultyLevelIdx = Integer.parseInt(prefs.getString("difficultyLevel"));
            for (DifficultyMapWight difficultyMapWight : DifficultyMapWight.values()) {
                if (difficultyMapWight.getDifficultyMapWightIdx() == difficultyLevelIdx)
                    return difficultyMapWight;
            }
        }
        saveDifficultyLevelWight(DEFAULT_DIFFICULTY_WIGHT);
        return DEFAULT_DIFFICULTY_WIGHT;
    }

    public static void saveDifficultyLevelHeight(DifficultyMapHeight difficultyLevel) {
        prefs.putString("difficultyLevel", String.valueOf(difficultyLevel.getDifficultyMapHeightIdx()));
        prefs.flush();
    }

    public static DifficultyMapHeight loadDifficultyMapHeight() {
        if (prefs.contains("difficultyLevel")) {
            int difficultyMapIdx = Integer.parseInt(prefs.getString("difficultyLevel"));
            for (DifficultyMapHeight difficultyMapHeight : DifficultyMapHeight.values()) {
                if (difficultyMapHeight.getDifficultyMapHeightIdx() == difficultyMapIdx)
                    return difficultyMapHeight;
            }
        }
        saveDifficultyLevelHeight(DEFAULT_DIFFICULTY_HEIGHT);
        return DEFAULT_DIFFICULTY_HEIGHT;
    }

    public static void saveScore(int score){
        prefs.putString("score", String.valueOf(score));
        prefs.flush();
    }
    public static int loadScore(){
        if(prefs.contains("score")) return Integer.parseInt(prefs.getString("score"));
        return 0;
    }



    public static boolean loadMusicState() {
        if (prefs.contains("musicState"))
            return Boolean.parseBoolean(prefs.getString("musicState"));
        saveMusicState(DEFAULT_SOUND_STATE);
        return true;
    }


}