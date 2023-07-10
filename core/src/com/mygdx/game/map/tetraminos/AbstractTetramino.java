package com.mygdx.game.map.tetraminos;

import com.mygdx.game.map.Map;
import com.mygdx.game.screens.GameScreen;

public class AbstractTetramino {
    public static int INDEX = 0;
    public int[] coordinatesX;
    public int[] coordinatesY;
    public boolean isMovable;

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
        for (int y : coordinatesX) y++;
    }

    public void moveDown(Map map) {
        for (int y : coordinatesX) y--;
        if (map.isTetraminoConflict(this)) {
            this.moveUp(map);
            this.isMovable = false;
        }
    }

    public void setCentralCoordinare(int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] += (newX - coordinatesX[1]);
            coordinatesY[i] += (newY - coordinatesY[1]);
        }
    }


}
