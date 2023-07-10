package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.Block;

import java.util.ArrayList;

public class Map {
    float posX, posY;
    public int width, height;
    float blockSize;
    public ArrayList<ArrayList<Block>> mapList;
    public AbstractTetramino currentTetramino, nextTetramino;




    public Map(float posX, float posY, int width, int height, float blockSize ) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        mapList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            mapList.add(new ArrayList<Block>());
            for (int j = 0; j < width; j++) {
                Block zeroBlock = new Block(posX + j * blockSize, posY + i * blockSize, blockSize, blockSize, "block_0.jpg", 0);
                mapList.get(i).add(zeroBlock);
            }

        }
    }

    public void summon(AbstractTetramino tetr){
        if(height == 5)tetr.setCentralCoordinate(2,2 );
        else{
            tetr.setCentralCoordinate(10, 18);
        }
        addTetramino(tetr);
    }

    public void addTetramino(AbstractTetramino tetr){
        if(isTetraminoConflict(tetr)){
            for (int i = 0; i < 4; i++) {
                mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).setType(tetr.INDEX);
            }
        }
    }
    public void deleteTetramino(AbstractTetramino tetr){
        for (int i = 0; i < 4; i++) {
            mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).setType(0);
        }
    }

    public void draw(MyGdxGame myGdxGame) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mapList.get(i).get(j).draw(myGdxGame);
            }
        }
    }

    public boolean isTetraminoConflict(AbstractTetramino tetr) {
        for (int i = 0; i < 4; i++) {
            if(!(0 <= tetr.coordinatesY[i] && tetr.coordinatesY[i] < height && 0 <= tetr.coordinatesX[i] && tetr.coordinatesX[i] < width ))       return false;
            if(mapList.get(tetr.coordinatesY[i]).get(tetr.coordinatesX[i]).type != 0){
                return false;
            }
        }
        return true;
    }

}

