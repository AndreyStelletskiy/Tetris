package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;

public class AbstractTetramino implements Cloneable {
    public int INDEX = 0;
    public int[] coordinatesX;
    public int[] coordinatesY;
    public boolean isMovable = true;

    public void rotateRight(Map map) {
    }

    public void rotateLeft(Map map) {
    }

    public void moveRight(Map map) {
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = percent(++coordinatesX[i], map.width);
        }
        if (!map.dontTetraminoConflict(this)) {
            for (int i = 0; i < 4; i++) {
                coordinatesX[i] = percent(--coordinatesX[i], map.width);
            }
        }
    }

    public void moveLeft(Map map) {
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = percent(--coordinatesX[i], map.width);
        }
        if (!map.dontTetraminoConflict(this)) {
            for (int i = 0; i < 4; i++) {
                coordinatesX[i] = percent(++coordinatesX[i], map.width);
            }
        }
    }

    protected void moveUp(Map map) {
        map.deleteTetramino(this);
        for (int i = 0; i < 4; i++) {
            coordinatesY[i]++;
        }
        map.addTetramino(this);
    }

    public void moveDown(Map map) {
        map.deleteTetramino(this);
        for (int i = 0; i < 4; i++) {
            coordinatesY[i]--;
        }

        if (!map.dontTetraminoConflict(this)) {
            for (int i = 0; i < 4; i++) {
                coordinatesY[i]++;
            }
            this.isMovable = false;
        }
//        for (int i = 0; i < 4; i++) {
//            coordinatesY[i]--;
//        }
//        if (!map.dontTetraminoConflict(this)) this.isMovable = false;
//
//        for (int i = 0; i < 4; i++) {
//            coordinatesY[i]++;
//        }

        map.addTetramino(this);

    }

    public void setCentralCoordinate(int newX, int newY) {
        int x = coordinatesX[1], y = coordinatesY[1];
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] += (newX - x);
            coordinatesY[i] += (newY - y);
        }
    }

    public AbstractTetramino clone() throws CloneNotSupportedException {
        return (AbstractTetramino) super.clone();
    }

}
