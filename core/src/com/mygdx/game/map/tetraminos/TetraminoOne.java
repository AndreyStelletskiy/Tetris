package com.mygdx.game.map.tetraminos;

public class TetraminoOne extends AbstractTetramino{ //квадратик
    TetraminoOne(int centralX, int centralY) {
        coordinatesX = new int[4];
        coordinatesY = new int[4];
        coordinatesX[0] = centralX;
        coordinatesX[1] = centralX + 1;
        coordinatesX[2] = centralX;
        coordinatesX[3] = centralX + 1;
        coordinatesY[0] = centralY;
        coordinatesY[1] = centralY;
        coordinatesY[2] = centralY + 1;
        coordinatesY[3] = centralY + 1;
    }


}
