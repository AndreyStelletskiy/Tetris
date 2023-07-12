package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

public class TetraminoThree extends AbstractTetramino{

    public TetraminoThree(int centralX, int centralY) {
        INDEX = 3;
        coordinatesX = new int[4];
        coordinatesY = new int[4];
        coordinatesX[0] = centralX - 1;
        coordinatesX[1] = centralX;
        coordinatesX[2] = centralX + 1;
        coordinatesX[3] = centralX - 1;
        coordinatesY[0] = centralY;
        coordinatesY[1] = centralY;
        coordinatesY[2] = centralY;
        coordinatesY[3] = centralY - 1;
    }
    @Override
    public void rotateRight(Map map) {
        int[] bufferX = new int[4];
        int[] bufferY = new int[4];
        System.arraycopy(coordinatesX, 0, bufferX, 0, 4);
        System.arraycopy(coordinatesY, 0, bufferY, 0, 4);
        if (coordinatesX[0] == coordinatesX[1] - 1 && coordinatesX[2] == coordinatesX[1] + 1) {
            coordinatesX[0] = coordinatesX[1];
            coordinatesX[2] = coordinatesX[1];
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1] - 1;
            coordinatesY[2] = coordinatesY[1] + 1;
            coordinatesY[3] = coordinatesY[1] - 1;
        } else if (coordinatesY[0] == coordinatesY[1] - 1 && coordinatesY[2] == coordinatesY[1] + 1) {
            coordinatesX[0] = coordinatesX[1] + 1;
            coordinatesX[2] = coordinatesX[1] - 1;
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1];
            coordinatesY[2] = coordinatesY[1];
            coordinatesY[3] = coordinatesY[1] + 1;
        } else if (coordinatesX[0] == coordinatesX[1] + 1 && coordinatesX[2] == coordinatesX[1] - 1) {
            coordinatesX[0] = coordinatesX[1];
            coordinatesX[2] = coordinatesX[1];
            coordinatesX[3] = coordinatesX[1] - 1;
            coordinatesY[0] = coordinatesY[1] + 1;
            coordinatesY[3] = coordinatesY[1] + 1;
            coordinatesY[2] = coordinatesY[1] - 1;
        } else {
            coordinatesX[0] = coordinatesX[1] - 1;
            coordinatesX[2] = coordinatesX[1] + 1;
            coordinatesX[3] = coordinatesX[1] - 1;
            coordinatesY[0] = coordinatesY[1];
            coordinatesY[2] = coordinatesY[1];
            coordinatesY[3] = coordinatesY[1] - 1;
        }

        for(int i = 0; i < 4; i ++){
            coordinatesX[i] = percent(coordinatesX[i], map.width);
            coordinatesY[i] = percent(coordinatesY[i], map.height);
        }

        if (!map.dontTetraminoConflict(this)) {
            this.moveLeft(map);
            if (!map.dontTetraminoConflict(this)) {
                this.moveRight(map);
                this.moveRight(map);
            }
            if (!map.dontTetraminoConflict(this)) {
                this.moveLeft(map);
                this.moveUp(map);
                if (!map.dontTetraminoConflict(this)) {
                    System.arraycopy(bufferX, 0, coordinatesX, 0, 4);
                    System.arraycopy(bufferY, 0, coordinatesY, 0, 4);
                    this.isMovable = false;
                }
            }
        }
    }
    @Override
    public void rotateLeft(Map map) {
        int[] bufferX = new int[4];
        int[] bufferY = new int[4];
        System.arraycopy(coordinatesX, 0, bufferX, 0, 4);
        System.arraycopy(coordinatesY, 0, bufferY, 0, 4);

        if (coordinatesX[0] == coordinatesX[1] - 1 && coordinatesX[2] == coordinatesX[1] + 1) {
            coordinatesX[0] = coordinatesX[1] - 1;
            coordinatesX[2] = coordinatesX[1] + 1;
            coordinatesX[3] = coordinatesX[1] - 1;
            coordinatesY[0] = coordinatesY[1];
            coordinatesY[2] = coordinatesY[1];
            coordinatesY[3] = coordinatesY[1] - 1;
        } else if (coordinatesY[0] == coordinatesY[1] - 1 && coordinatesY[2] == coordinatesY[1] + 1) {
            coordinatesX[0] = coordinatesX[1];
            coordinatesX[2] = coordinatesX[1];
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1] - 1;
            coordinatesY[2] = coordinatesY[1] + 1;
            coordinatesY[3] = coordinatesY[1] - 1;
        } else if (coordinatesX[0] == coordinatesX[1] + 1 && coordinatesX[2] == coordinatesX[1] - 1) {
            coordinatesX[2] = coordinatesX[1] - 1;
            coordinatesX[0] = coordinatesX[1] + 1;
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1];
            coordinatesY[2] = coordinatesY[1];
            coordinatesY[3] = coordinatesY[1] + 1;
        } else {
            coordinatesX[0] = coordinatesX[1];
            coordinatesX[2] = coordinatesX[1];
            coordinatesX[3] = coordinatesX[1] - 1;
            coordinatesY[0] = coordinatesY[1] + 1;
            coordinatesY[3] = coordinatesY[1] + 1;
            coordinatesY[2] = coordinatesY[1] - 1;
        }

        for(int i = 0; i < 4; i ++){
            coordinatesX[i] = percent(coordinatesX[i], map.width);
            coordinatesY[i] = percent(coordinatesY[i], map.height);
        }

        if (!map.dontTetraminoConflict(this)) {
            this.moveLeft(map);
            if (!map.dontTetraminoConflict(this)) {
                this.moveRight(map);
                this.moveRight(map);
            }
            if (!map.dontTetraminoConflict(this)) {
                this.moveLeft(map);
                this.moveUp(map);
                if (!map.dontTetraminoConflict(this)) {
                    System.arraycopy(bufferX, 0, coordinatesX, 0, 4);
                    System.arraycopy(bufferY, 0, coordinatesY, 0, 4);
                    this.isMovable = false;
                }
            }
        }
    }
}
