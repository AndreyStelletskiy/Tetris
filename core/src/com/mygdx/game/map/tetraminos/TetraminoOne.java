package com.mygdx.game.map.tetraminos;

public class TetraminoOne extends AbstractTetramino{ //квадратик
    TetraminoOne(int centralX, int centralY) {
        coordinatesX = new int[4];
        getCoordinatesY = new int[4];
        coordinatesX[0] = centralX;
        coordinatesX[1] = centralX + 1;
        coordinatesX[2] = centralX;
    }
}
