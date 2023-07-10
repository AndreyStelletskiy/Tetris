package com.mygdx.game.map.tetraminos;

public class AbstractTetramino {
    int[] coordinatesX;
    int[] getCoordinatesY;

    public void rotateRight() {

    }
    public void rotateLeft() {

    }
    public void moveRight() {

    }
    public void moveLeft() {

    }
    protected void moveUp() {

    }
    public void moveDown() {
        for (int x : coordinatesX) x--;
    }
}
