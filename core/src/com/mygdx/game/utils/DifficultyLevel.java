package com.mygdx.game.utils;

public enum DifficultyLevel {

    EASY(15, 10, 1, 1.3f),
    MEDIUM(30, 15, 2, 1.f),
    HARD(45, 20, 3, 0.8f);

    private final int difficultyLevelIdx;
    private final int countOfEnemies;
    private final float enemySpeed;
    private final float size;

    DifficultyLevel(int countOfEnemies, float enemySpeed, int difficultyLevelIdx, float size) {
        this.difficultyLevelIdx = difficultyLevelIdx;
        this.countOfEnemies = countOfEnemies;
        this.enemySpeed = enemySpeed;
        this.size = size;
    }

    public int getCountOfEnemies() {
        return countOfEnemies;
    }

    public float getEnemySpeed() {
        return enemySpeed;
    }

    public int getDifficultyLevelIdx() {
        return difficultyLevelIdx;
    }
    public String getDifficultyName(){

        switch (getDifficultyLevelIdx()){
            case 1:
                return "EASY";
            case 2:
                return"MEDIUM";
            case 3:
                return "HARD";
            default:
                return "";
        }

    }

    public float getSize() {
        return size;
    }
}
