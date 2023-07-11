package com.mygdx.game.utils;

import static com.mygdx.game.utils.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryLoader {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");


    public static void saveScore(int score) {
        prefs.putString("score", String.valueOf(score));
        prefs.flush();
    }

    public static int loadScore() {
        if (prefs.contains("score")) return Integer.parseInt(prefs.getString("score"));
        return 0;
    }

    public static DifficultyMapWight loadDifficultyMapWight() {
        if (prefs.contains("difficultyLevel")) {
            int difficultyLevelIdx = Integer.parseInt(prefs.getString("difficultyLevel"));
            for (DifficultyMapWight difficultyLevel : DifficultyMapWight.values()) {
                if (difficultyLevel.getDifficultyIdx() == difficultyLevelIdx)
                    return difficultyLevel;
            }
        }
        saveDifficultyLevelWight(DEFAULT_DIFFICULTY_WIGHT);
        return DEFAULT_DIFFICULTY_WIGHT;
    }

    public static DifficultyMapHeight loadDifficultyMapHeight() {
        if (prefs.contains("difficultyLevel")) {
            int difficultyLevelIdx = Integer.parseInt(prefs.getString("difficultyLevel"));
            for (DifficultyMapHeight difficultyLevel : DifficultyMapHeight.values()) {
                if (difficultyLevel.getDifficultyIdx() == difficultyLevelIdx)
                    return difficultyLevel;
            }
        }
        saveDifficultyLevelHeight(DEFAULT_DIFFICULTY_HEIGHT);
        return DEFAULT_DIFFICULTY_HEIGHT;
    }

    public static void saveDifficultyLevelWight(DifficultyMapWight difficultyMapWight) {
        prefs.putString("difficultyLevel", String.valueOf(difficultyMapWight.getDifficultyIdx()));
        prefs.flush();
    }

    public static void saveDifficultyLevelHeight(DifficultyMapHeight difficultyMapHeight) {
        prefs.putString("difficultyLevel", String.valueOf(difficultyMapHeight.getDifficultyIdx()));
        prefs.flush();
    }

    public static void saveMusicState(boolean isPlaying) {
        prefs.putString("musicState", String.valueOf(isPlaying));
        prefs.flush();
    }

    public static boolean loadMusicState() {
        if (prefs.contains("musicState"))
            return Boolean.parseBoolean(prefs.getString("musicState"));
        saveMusicState(DEFAULT_SOUND_STATE);
        return true;
    }
}