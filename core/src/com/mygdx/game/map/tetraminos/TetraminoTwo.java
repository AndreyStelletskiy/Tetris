package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

public class TetraminoTwo extends AbstractTetramino {

    private boolean isPositionVertical;

    public TetraminoTwo(int centralX, int centralY) { //опорная координата - x[1]y[1]
        super();
        INDEX = 2;
        vectorX = new int[4];
        vectorY = new int[4];
        vectorX[0] = centralX - 1;
        vectorX[1] = centralX;
        vectorX[2] = centralX;
        vectorX[3] = centralX + 1;
        vectorY[0] = centralY;
        vectorY[1] = centralY;
        vectorY[2] = centralY - 1;
        vectorY[3] = centralY - 1;
        isPositionVertical = false;
    }

}

