package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

import java.util.Arrays;

public class TetraminoTwo extends AbstractTetramino{

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
    @Override
    public void rotateRight(Map map) {
        int[] bufferX = new int[4];
        int[] bufferY = new int[4];
        System.arraycopy(coordinatesX, 0, bufferX, 0, 4);
        System.arraycopy(coordinatesY, 0, bufferY, 0, 4);
        if (this.isPositionVertical) {
            coordinatesX[0] = coordinatesX[1] - 1;
            coordinatesX[2] = coordinatesX[1];
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1];
            coordinatesY[2] = coordinatesY[1] - 1;
            coordinatesY[3] = coordinatesY[1] - 1;
            isPositionVertical = false;
        } else {
            coordinatesX[0] = coordinatesX[1];
            coordinatesX[2] = coordinatesX[1] + 1;
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1] - 1;
            coordinatesY[2] = coordinatesY[1];
            coordinatesY[3] = coordinatesY[1] + 1;
            isPositionVertical = true;
        }
        this.moveRight(map);
        this.moveDown(map);
        for(int i = 0; i < 4; i ++){
            coordinatesX[i] = percent(coordinatesX[i], map.width);
            coordinatesY[i] = percent(coordinatesY[i], map.height);
        }
        if (map.isTetraminoConflict(this)) {
            this.moveLeft(map);
            if (map.isTetraminoConflict(this)) {
                this.moveRight(map);
                this.moveRight(map);
            }
            if (map.isTetraminoConflict(this)) {
                this.moveLeft(map);
                this.moveUp(map);
                if (map.isTetraminoConflict(this)) {
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
        if (this.isPositionVertical) {
            coordinatesX[0] = coordinatesX[1] - 1;
            coordinatesX[2] = coordinatesX[1];
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1];
            coordinatesY[2] = coordinatesY[1] - 1;
            coordinatesY[3] = coordinatesY[1] - 1;
            isPositionVertical = false;
        } else {
            coordinatesX[0] = coordinatesX[1];
            coordinatesX[2] = coordinatesX[1] + 1;
            coordinatesX[3] = coordinatesX[1] + 1;
            coordinatesY[0] = coordinatesY[1] - 1;
            coordinatesY[2] = coordinatesY[1];
            coordinatesY[3] = coordinatesY[1] + 1;
            isPositionVertical = true;
        }
        for(int i = 0; i < 4; i ++){
            coordinatesX[i] = percent(coordinatesX[i], map.width);
            coordinatesY[i] = percent(coordinatesY[i], map.height);
        }
        if (map.isTetraminoConflict(this)) {
            this.moveLeft(map);
            if (map.isTetraminoConflict(this)) {
                this.moveRight(map);
                this.moveRight(map);
            }
            if (map.isTetraminoConflict(this)) {
                this.moveLeft(map);
                this.moveUp(map);
                if (map.isTetraminoConflict(this)) {
                    System.arraycopy(bufferX, 0, coordinatesX, 0, 4);
                    System.arraycopy(bufferY, 0, coordinatesY, 0, 4);
                    this.isMovable = false;
                }
            }
        }
    }
}
