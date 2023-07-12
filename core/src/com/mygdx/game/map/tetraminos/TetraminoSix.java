package com.mygdx.game.map.tetraminos;

public class TetraminoSix extends AbstractTetramino{
    public TetraminoSix(int centralX, int centralY) {
        INDEX = 6;
        coordinatesX = new int[4];
        coordinatesY = new int[4];
        coordinatesX[0] = centralX - 1;
        coordinatesX[1] = centralX;
        coordinatesX[2] = centralX + 1;
        coordinatesX[3] = centralX + 1;
        coordinatesY[0] = centralY;
        coordinatesY[1] = centralY;
        coordinatesY[2] = centralY;
        coordinatesY[3] = centralY - 1;
    }
}
