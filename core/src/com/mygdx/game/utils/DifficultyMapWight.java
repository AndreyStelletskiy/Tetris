package com.mygdx.game.utils;


public enum DifficultyMapWight {

    EASY(GameSettings.COUNT_OF_SIZE_WIGHT_EASY,
            20),

    MEDIUM(GameSettings.COUNT_OF_SIZE_WIGHT_MEDIUM,
            26),

    HARD(GameSettings.COUNT_OF_SIZE_WIGHT_HARD,
            30);

    private final int difficultyLevelWightIdx;
    private final int countOfSize;

    DifficultyMapWight(int countOfSize, int difficultyLevelWightIdx) {
        this.difficultyLevelWightIdx = difficultyLevelWightIdx;
        this.countOfSize = countOfSize;

    }


    public int getDifficultyMapWightIdx() {
        return difficultyLevelWightIdx;
    }

}