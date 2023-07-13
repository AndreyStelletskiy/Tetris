package com.mygdx.game.utils;

import static com.mygdx.game.utils.GameSettings.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.sun.org.apache.bcel.internal.generic.INEG;

import java.util.ArrayList;

public class MemoryLoader {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");
    static ArrayList<String> musicNames;
    static ArrayList<String> musicPaths;
    static ArrayList<String> assetNames;
    static ArrayList<String> assetPaths;

    public static void saveScoreBoard(ArrayList<Integer> arrayList) {
        prefs.putString("scoreBoard", parseArrayToString(arrayList));
        prefs.flush();
    }

    public static ArrayList<Integer> loadScoreBoard() {
        if (prefs.contains("scoreBoard")) return parseStringToArray(prefs.getString("scoreBoard"));
        return parseStringToArray("0 0 0 0 0");
    }

    public static void saveScore(int score) {
        prefs.putString("score", String.valueOf(score));
        prefs.flush();
    }

    public static int loadScore() {
        if (prefs.contains("score")) return Integer.parseInt(prefs.getString("score"));
        return 0;
    }

    public static DifficultyMapWight loadDifficultyMapWight() {
        if (prefs.contains("difficultyLevelWight")) {
            int difficultyLevelIdx = Integer.parseInt(prefs.getString("difficultyLevelWight"));
            for (DifficultyMapWight difficultyLevel : DifficultyMapWight.values()) {
                if (difficultyLevel.getDifficultyMapWightIdx() == difficultyLevelIdx)
                    return difficultyLevel;
            }
        }
        saveDifficultyLevelWight(DEFAULT_DIFFICULTY_WIGHT);
        return DEFAULT_DIFFICULTY_WIGHT;
    }

    public static DifficultyMapHeight loadDifficultyMapHeight() {
        if (prefs.contains("difficultyLevelHeight")) {
            int difficultyLevelIdx = Integer.parseInt(prefs.getString("difficultyLevelHeight"));
            for (DifficultyMapHeight difficultyLevel : DifficultyMapHeight.values()) {
                if (difficultyLevel.getDifficultyMapHeightIdx() == difficultyLevelIdx)
                    return difficultyLevel;
            }
        }
        saveDifficultyLevelHeight(DEFAULT_DIFFICULTY_HEIGHT);
        return DEFAULT_DIFFICULTY_HEIGHT;
    }

    public static void saveDifficultyLevelWight(DifficultyMapWight difficultyMapWight) {
        prefs.putString("difficultyLevelWight", String.valueOf(difficultyMapWight.getDifficultyMapWightIdx()));
        prefs.flush();
    }

    public static void saveDifficultyLevelHeight(DifficultyMapHeight difficultyMapHeight) {
        prefs.putString("difficultyLevelHeight", String.valueOf(difficultyMapHeight.getDifficultyMapHeightIdx()));
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

    public static ArrayList<Integer> parseStringToArray(String s) {
        ArrayList<Integer> ans = new ArrayList<>();
        String[] ar = s.split(" ");
        for (String string : ar) {
            ans.add(Integer.parseInt(string));
        }
        return ans;
    }

    public static String parseArrayToString(ArrayList<Integer> arr) {
        String ans = "" + arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            ans += " " + arr.get(i);
        }
        return ans;
    }

    public static void clearAllSaves() {
        MemoryLoader.saveDifficultyLevelWight(DEFAULT_DIFFICULTY_WIGHT);
        MemoryLoader.saveDifficultyLevelHeight(DEFAULT_DIFFICULTY_HEIGHT);
        MemoryLoader.saveMusicState(GameSettings.DEFAULT_SOUND_STATE);
        MemoryLoader.saveScoreBoard(parseStringToArray("0 0 0 0 0"));
        MemoryLoader.saveScore(0);
    }

    public static ArrayList<String> loadMusicNames() {
        if (musicNames == null) {
            musicNames = new ArrayList<>();
            musicNames.add("Default song");
            musicNames.add("Tetris Phonk Remix");
            musicNames.add("Best Song");
            musicNames.add("Mortal Combat");
            musicNames.add("Doom");
        }
        return musicNames;
    }

    public static ArrayList<String> loadMusicPaths() {
        if (musicPaths == null) {
            musicPaths = new ArrayList<>();
            for(int i = 0; i < 5; i ++){
                musicPaths.add("tetris_theme_" + i +".mp3");
            }
        }
        return musicPaths;
    }

    public static void saveMusicStates(ArrayList<Integer> arrayList) {
        prefs.putString("musicStates",parseArrayToString(arrayList));
        prefs.flush();
    }

    public static ArrayList<Integer> loadMusicStates() {
        if (prefs.contains("musicStates")) {
            return parseStringToArray(prefs.getString("musicStates"));
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(0);
        }
        arrayList.set(0,2);
        saveMusicStates(arrayList);
        return arrayList;
    }
    public static void saveAssetStates(ArrayList<Integer> arrayList) {
        prefs.putString("assetStates",parseArrayToString(arrayList));
        prefs.flush();
    }

    public static ArrayList<Integer> loadAssetStates() {
        if (prefs.contains("assetStates"))
            return parseStringToArray(prefs.getString("assetStates"));
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            arrayList.add( 0);
        }
        arrayList.set(0,2);
        saveAssetStates(arrayList);
        return arrayList;
    }
    public static ArrayList<String> loadAssetNames() {
        if (assetNames == null) {
            assetNames = new ArrayList<>();
            assetNames.add("Default");
            assetNames.add("Noir");
        }
        return assetNames;
    }

    public static ArrayList<String> loadAssetPaths() {
        if (assetPaths == null) {
            assetPaths = new ArrayList<>();
            for(int i = 0; i < 2; i ++){
                assetPaths.add("asset_pack" + i + "/");
            }
        }
        return assetPaths;
    }

}