package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

public class TetraminoTwo extends AbstractTetramino {

    private boolean isPositionVertical;

    public TetraminoTwo(int centralX, int centralY) { //опорная координата - x[1]y[1]
        INDEX = 2;
        coordinatesX = new int[4];
        coordinatesY = new int[4];
        coordinatesX[0] = centralX - 1;
        coordinatesX[1] = centralX;
        coordinatesX[2] = centralX;
        coordinatesX[3] = centralX + 1;
        coordinatesY[0] = centralY;
        coordinatesY[1] = centralY;
        coordinatesY[2] = centralY - 1;
        coordinatesY[3] = centralY - 1;
        isPositionVertical = false;
    }

}

