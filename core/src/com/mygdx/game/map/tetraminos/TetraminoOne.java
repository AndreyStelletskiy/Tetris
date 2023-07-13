package com.mygdx.game.map.tetraminos;

public class TetraminoOne extends AbstractTetramino{ //квадратик
    public TetraminoOne(int centralX, int centralY) {

        super();
        INDEX = 1;
        vectorX = new int[4];
        vectorY = new int[4];
        vectorX[0] = centralX;
        vectorX[1] = centralX + 1;
        vectorX[2] = centralX;
        vectorX[3] = centralX + 1;
        vectorY[0] = centralY;
        vectorY[1] = centralY;
        vectorY[2] = centralY - 1;
        vectorY[3] = centralY - 1;
    }
}
