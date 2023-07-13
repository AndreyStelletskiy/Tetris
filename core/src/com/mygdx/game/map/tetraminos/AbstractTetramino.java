package com.mygdx.game.map.tetraminos;

import static com.mygdx.game.utils.MathHelper.percent;

import com.mygdx.game.map.Map;
import com.mygdx.game.utils.MathHelper;

public class AbstractTetramino implements Cloneable {
    public int INDEX = 0;
    public int X, Y;
    public int[] coordinatesX;
    public int[] coordinatesY;
    public int[] vectorX;
    public int[] vectorY;
    public boolean isMovable = true;

    AbstractTetramino() {
        coordinatesX = new int[4];
        coordinatesY = new int[4];
    }

    public void rotateLeft(Map map) {
        //if(!isMovable)return;
        if (INDEX == 1) return;

        int[] bufferX = new int[4];
        int[] bufferY = new int[4];
        System.arraycopy(vectorX, 0, bufferX, 0, 4);
        System.arraycopy(vectorY, 0, bufferY, 0, 4);
        for (int i = 0; i < 4; i++) {
            vectorX[i] = bufferX[1] - bufferY[i] + bufferY[1];
            vectorY[i] = bufferY[1] + bufferX[i] - bufferX[1];
        }
        updateCoords(map);
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = percent(coordinatesX[i], map.width);
        }
        /*if (!map.dontTetraminoConflict(this)) {
            System.arraycopy(bufferX, 0, coordinatesX, 0, 4);
            System.arraycopy(bufferY, 0, coordinatesY, 0, 4);
        }*/
    }

    public void rotateRight(Map map) {
        //if(!isMovable)return;
        if (INDEX == 1) return;

        int[] bufferX = new int[4];
        int[] bufferY = new int[4];
        System.arraycopy(vectorX, 0, bufferX, 0, 4);
        System.arraycopy(vectorY, 0, bufferY, 0, 4);
        for (int i = 0; i < 4; i++) {
            vectorX[i] = bufferX[1] + bufferY[i] - bufferY[1];
            vectorY[i] = bufferY[1] - bufferX[i] + bufferX[1];
        }
        updateCoords(map);
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = MathHelper.percent(coordinatesX[i], map.width);
        }

//        if (coordinatesX[1] <= 2 || coordinatesX[1] >= map.width - 3) {
//            for (int i = 0; i < 4; i++) {
//                if (bufferX[1] - bufferX[i] > 2) coordinatesX[i] = bufferX[i] + map.width;
//                if (bufferX[1] - bufferX[i] < -2) coordinatesX[i] = bufferX[i] - map.width;
//            }
//            for (int i = 0; i < 4; i++) {
//                coordinatesX[i] = percent(bufferX[1] + bufferY[i] - bufferY[1], map.width);
//                coordinatesY[i] = percent(bufferY[1] - bufferX[i] + bufferX[1], map.height);
//            }
//            for (int i = 0; i < 4; i++) {
//                coordinatesX[i] = percent(coordinatesX[i], map.width);
//            }
//        } else {
//            for (int i = 0; i < 4; i++) {
//                coordinatesX[i] = percent(bufferX[1] + bufferY[i] - bufferY[1], map.width);
//                coordinatesY[i] = percent(bufferY[1] - bufferX[i] + bufferX[1], map.height);
//            }
//        }
//        for (int i = 0; i < 4; i++) {
//            coordinatesX[i] = percent(coordinatesX[i], map.width);
//        }
        /*if (!map.dontTetraminoConflict(this)) {
            System.arraycopy(bufferX, 0, coordinatesX, 0, 4);
            System.arraycopy(bufferY, 0, coordinatesY, 0, 4);
        }*/

    }

    public void moveRight(Map map) {
        if (!isMovable) return;
        X = percent(++X, map.width);
        updateCoords(map);
        if (!map.dontTetraminoConflict(this)) {
            X = percent(--X, map.width);
            updateCoords(map);
        }
    }

    public void moveLeft(Map map) {
        if (!isMovable) return;
        X = percent(--X, map.width);
        updateCoords(map);

        if (!map.dontTetraminoConflict(this)) {
            X = percent(++X, map.width);
            updateCoords(map);
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
        Y--;
        updateCoords(map);

        if (!map.dontTetraminoConflict(this)) {
            Y++;
            updateCoords(map);
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

    public void setCentralCoordinate(int newX, int newY, Map map) {
        X = newX;
        Y = newY;
        updateCoords(map);
    }

    public AbstractTetramino clone() throws CloneNotSupportedException {
        return (AbstractTetramino) super.clone();
    }

    public void updateCoords(Map map) {
        for (int i = 0; i < 4; i++) {
            coordinatesX[i] = percent(X + vectorX[i] - vectorX[1], map.width);
            coordinatesY[i] = Y + vectorY[i] - vectorY[1];
        }
    }

}
