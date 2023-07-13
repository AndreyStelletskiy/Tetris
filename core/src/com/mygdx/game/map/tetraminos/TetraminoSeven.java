package com.mygdx.game.map.tetraminos;

public class TetraminoSeven extends AbstractTetramino{
    public TetraminoSeven(int centralX, int centralY) {
        super();
        INDEX = 7;
        vectorX = new int[4];
        vectorY = new int[4];
        vectorX[0] = centralX + 1;
        vectorX[1] = centralX;
        vectorX[2] = centralX;
        vectorX[3] = centralX - 1;
        vectorY[0] = centralY;
        vectorY[1] = centralY;
        vectorY[2] = centralY - 1;
        vectorY[3] = centralY - 1;
    }
}
