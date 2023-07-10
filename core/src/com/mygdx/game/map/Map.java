package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.Block;

import java.util.ArrayList;

public class Map {
    float posX, posY;
    public int width, height;
    float blockSize;
    ArrayList<ArrayList<Block>> mapList;
    AbstractTetramino currentTetramino, nextTetramino;




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
            if(tetr.coordinatesX[i]!= 0 || tetr.coordinatesY[i] != 0){
                return false;
            }
        }
        return true;
    }

}

