package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

public class TetraminoThree extends AbstractTetramino{

    public TetraminoThree(int centralX, int centralY) {
        super();
        INDEX = 3;
        vectorX = new int[4];
        vectorY = new int[4];
        vectorX[0] = centralX - 1;
        vectorX[1] = centralX;
        vectorX[2] = centralX + 1;
        vectorX[3] = centralX - 1;
        vectorY[0] = centralY;
        vectorY[1] = centralY;
        vectorY[2] = centralY;
        vectorY[3] = centralY - 1;
    }

}
