package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.Block;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.utils.Textures;

import java.util.ArrayList;

public class Map {
    public float posX;
    float posY;
    public int width, height;
    public float blockSize;
    float step;
    ImageView fone;
    public ArrayList<ArrayList<Block>> mapList;


    public Map(float posX, float posY, int width, int height, float blockSize, float step) {
        this.step = step;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        if(width == 5){
            fone = new ImageView(posX - 3 - step, posY - 4 , width*(blockSize+step) + step  + 6, height * (blockSize+step)   + 8, Textures.textures.get(-4));
        }
        else{
            fone = new ImageView(posX - 12 - step,  posY - 20-  step, width*(blockSize+step) +step  + 24, height * (blockSize+step) + step  + 40, Textures.textures.get(-4));
        }
        mapList = new ArrayList<>();
        mapList = createClearMap(width,height);
    }
    public ArrayList<ArrayList<Block>> createClearMap(int width, int height){
        ArrayList<ArrayList<Block>> mapList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            mapList.add(new ArrayList<Block>());
            for (int j = 0; j < width; j++) {
                Block zeroBlock = new Block(posX + j * (blockSize+step), posY + i * (blockSize+step), blockSize, blockSize, Textures.textures.get(0), 0);
                mapList.get(i).add(zeroBlock);
            }
        }
        return mapList;
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
        fone.draw(myGdxGame);
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
        //posX -= count*(blockSize + step);
        ArrayList<ArrayList<Block>> newMapList = createClearMap(width + count * 2, height);
        for(int i = 0; i < height; i ++){
            for (int j = 0; j < width; j++) {
                newMapList.get(i).get(j+count).setType(mapList.get(i).get(j).type);
            }
        }
        mapList = newMapList;
        width += 2 * count;
        fone = new ImageView(posX - 12 - step,  posY - 20-  step, width*(blockSize+step) +step  + 24, height * (blockSize+step) + step  + 40, Textures.textures.get(-4));
    }
}

