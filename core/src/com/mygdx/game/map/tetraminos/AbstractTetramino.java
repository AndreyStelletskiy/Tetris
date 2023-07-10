package com.mygdx.game.map.tetraminos;

public class AbstractTetramino {
    public int[] coordinatesX;
    public int[] coordinatesY;

    public void rotateRight() {

    }
    public void rotateLeft() {

    }
    public void moveRight() {

    }
    public void moveLeft() {

        for (int x : coordinatesX) x--;

    }
    protected void moveUp() {
        for (int y : coordinatesX) y++;

    }
    public void moveDown() {
        for (int y : coordinatesX) y--;
    }
}
