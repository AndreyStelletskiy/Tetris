package com.mygdx.game.map.tetraminos;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.map.Map;

public class AbstractTetramino implements Cloneable{
    public int INDEX = 0;
    public int[] coordinatesX;
    public int[] coordinatesY;
    public boolean isMovable = true;

    public void rotateRight(Map map) {}
    public void rotateLeft(Map map) {}
    public void moveRight(Map map) {
        for (int x : coordinatesX) x = ++x % map.height;
        if (map.isTetraminoConflict(this)) for (int x : coordinatesX) x = --x % map.height;
    }
    public void moveLeft(Map map) {
        for (int x : coordinatesX) x = --x % map.height;
        if (map.isTetraminoConflict(this)) for (int x : coordinatesX) x = ++x % map.height;
    }

    protected void moveUp(Map map) {
        for (int i = 0; i < 4; i++) {
            coordinatesY[i]++;
        }
    }

    public void moveDown(Map map) {
        map.deleteTetramino(this);
        for (int i = 0; i < 4; i++) {
            coordinatesY[i]--;
        }

        if (!map.isTetraminoConflict(this)) {
            this.moveUp(map);
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

    public AbstractTetramino clone() throws CloneNotSupportedException{
        return (AbstractTetramino) super.clone();
    }

}
