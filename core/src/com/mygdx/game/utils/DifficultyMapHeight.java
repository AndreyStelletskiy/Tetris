package com.mygdx.game.utils;


public enum DifficultyMapHeight {

    EASY(GameSettings.COUNT_OF_SIZE_HEIGHT_EASY,
            36),

    MEDIUM(GameSettings.COUNT_OF_SIZE_HEIGHT_MEDIUM,
            40),

    HARD(GameSettings.COUNT_OF_SIZE_HEIGHT_HARD,
            44);

    private final int DifficultyMapHeightIdx;
    private final int countOfSize;

    DifficultyMapHeight(int countOfSize, int difficultyMapWightIdx) {
        this.DifficultyMapHeightIdx = difficultyMapWightIdx;
        this.countOfSize = countOfSize;

    }


    public int getDifficultyMapHeightIdx() {
        return DifficultyMapHeightIdx;
    }

}