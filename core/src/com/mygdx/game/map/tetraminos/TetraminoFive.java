package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

public class TetraminoFive extends AbstractTetramino{
    public TetraminoFive(int centralX, int centralY) {
        INDEX = 5;
        coordinatesX = new int[4];
        coordinatesY = new int[4];
        coordinatesX[0] = centralX - 1;
        coordinatesX[1] = centralX;
        coordinatesX[2] = centralX + 1;
        coordinatesX[3] = centralX;
        coordinatesY[0] = centralY;
        coordinatesY[1] = centralY;
        coordinatesY[2] = centralY;
        coordinatesY[3] = centralY - 1;
    }

}
