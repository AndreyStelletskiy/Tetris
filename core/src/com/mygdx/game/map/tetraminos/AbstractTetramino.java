package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.badlogic.gdx.Gdx;
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
        map.deleteTetramino(this);
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = percent(++coordinatesX[i], map.width);
        }
        if (!map.isTetraminoConflict(this)) {
            for (int i = 0; i < 4; i++) {
                coordinatesX[i] = percent(--coordinatesX[i], map.width);
            }
        }
        map.addTetramino(this);
    }

    public void moveLeft(Map map) {
        map.deleteTetramino(this);
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = percent(--coordinatesX[i], map.width);
        }
        if (!map.isTetraminoConflict(this)) {
            for (int i = 0; i < 4; i++) {
                coordinatesX[i] = percent(++coordinatesX[i], map.width);
            }
        }
        map.addTetramino(this);
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

        if (!map.isTetraminoConflict(this)) {
            for (int i = 0; i < 4; i++) {
                coordinatesY[i]++;
            }
            this.isMovable = false;
        }
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
