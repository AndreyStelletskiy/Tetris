package com.mygdx.game.map;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.Block;

import java.util.ArrayList;

public class Map {
    public float posX;
    float posY;
    public int width, height;
    float blockSize;
    public ArrayList<ArrayList<Block>> mapList;


    public Map(float posX, float posY, int width, int height, float blockSize) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        mapList = new ArrayList<>();
        System.out.println(height);
        System.out.println(width);
        for (int i = 0; i < height; i++) {
            mapList.add(new ArrayList<Block>());
            for (int j = 0; j < width; j++) {
                Block zeroBlock = new Block(posX + j * blockSize, posY + i * blockSize, blockSize, blockSize, "block_0.bmp", 0);
                mapList.get(i).add(zeroBlock);
            }

        }
    }

    public boolean summon(AbstractTetramino tetr) {
        if (height == 5) {
            tetr.setCentralCoordinate(2, 2, this);
            if(!dontTetraminoConflict(tetr))return false;
        }
        else {
            tetr.setCentralCoordinate((int) (Math.random()*(width-2)), height - 2, this);
            if(!dontTetraminoConflict(tetr))return false;
        }
        addTetramino(tetr);
        return true;

    }

    public void addTetramino(AbstractTetramino tetr) {
        tetr.updateCoords(this);
        if (dontTetraminoConflict(tetr)) {
            for (int i = 0; i < 4; i++) {
                mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).setType(tetr.INDEX);
            }
        }
    }

    public void deleteTetramino(AbstractTetramino tetr) {
        tetr.updateCoords(this);
        for (int i = 0; i < 4; i++) {
            if(mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).type == tetr.INDEX) {
                mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).setType(0);
            }
        }
    }

    public void draw(MyGdxGame myGdxGame) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mapList.get(i).get(j).draw(myGdxGame);
            }
        }
    }

    public boolean dontTetraminoConflict(AbstractTetramino tetr) {
        for (int i = 0; i < 4; i++) {
            if (!(0 <= tetr.coordinatesY[i] && tetr.coordinatesY[i] < height && 0 <= tetr.coordinatesX[i] && tetr.coordinatesX[i] < width))
                return false;
            if (mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).type != 0 && mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).type != -2) {
                return false;
            }
        }
        return true;
    }
    public boolean downCheck(){
        return true;
    }

    public boolean isStringFull(int indx) {
        for (int i = 0; i < width; i++) {
            if (mapList.get(indx).get(i).type == 0 || mapList.get(indx).get(i).type == -2) {
                return false;
            }
        }
        return true;
    }

    public void deleteString(int indx) {
        for (int i = 0; i < width; i++) {
            mapList.get(indx).get(i).setType(0);
        }
        for (int i = indx; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                mapList.get(i).get(j).setType(mapList.get(i + 1).get(j).type);
            }
        }
    }

    public void colorString(int i) {
        for(int j = 0; j < width; j++){
            mapList.get(i).get(j).setType(-1);
        }
    }

    public void addColumns(int count) {
        ArrayList<ArrayList<Block>> newMapList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            newMapList.add(new ArrayList<Block>());
            for (int j = 0; j < width + count * 2; j++) {
                Block zeroBlock = new Block(posX + j * blockSize, posY + i * blockSize, blockSize, blockSize, "block_0.bmp", 0);
                newMapList.get(i).add(zeroBlock);
            }
        }
        for(int i = 0; i < height; i ++){
            for (int j = 0; j < width; j++) {
                newMapList.get(i).get(j+count).setType(mapList.get(i).get(j).type);
            }
        }
        mapList = newMapList;
        width += 2 * count;
    }
}

