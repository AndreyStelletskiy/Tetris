package com.mygdx.game.utils;


public enum DifficultyMapHeight {

    EASY(GameSettings.COUNT_OF_SIZE_HEIGHT_EASY,
            GameSettings.COUNT_OF_SIZE_HEIGHT_EASY),

    MEDIUM(GameSettings.COUNT_OF_SIZE_HEIGHT_MEDIUM,
            GameSettings.COUNT_OF_SIZE_HEIGHT_MEDIUM),

    HARD(GameSettings.COUNT_OF_SIZE_HEIGHT_HARD,
            GameSettings.COUNT_OF_SIZE_HEIGHT_HARD);

    private final int difficultyMapHeightIdx;
    private final int countOfSize;

    DifficultyMapHeight(int countOfSize, int difficultyMapHeightIdx) {
        this.difficultyMapHeightIdx = difficultyMapHeightIdx;
        this.countOfSize = countOfSize;

    }


    public int getDifficultyMapHeightIdx() {
        return difficultyMapHeightIdx;
    }

}