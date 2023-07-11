package com.mygdx.game.utils;


public enum DifficultyMapWight {

    EASY(GameSettings.COUNT_OF_SIZE_WIGHT_EASY,
            GameSettings.COUNT_OF_SIZE_WIGHT_EASY),

    MEDIUM(GameSettings.COUNT_OF_SIZE_WIGHT_MEDIUM,
            GameSettings.COUNT_OF_SIZE_WIGHT_MEDIUM),

    HARD(GameSettings.COUNT_OF_SIZE_WIGHT_HARD,
            GameSettings.COUNT_OF_SIZE_WIGHT_HARD);

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