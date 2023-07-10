package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.Block;

import java.util.ArrayList;

public class Map {
    float posX, posY;
    int width, height;
    float blockSize;
    ArrayList<ArrayList<Block>> mapList;
    AbstractTetramino currentTetramino, nextTetramino;
    MyGdxGame myGdxGame;

    public Map(float posX, float posY, int width, int height, float blockSize, MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        mapList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            mapList.add(new ArrayList<Block>());
            for (int j = 0; j < width; j++) {
                Block zeroBlock = new Block(posX + j * blockSize, posY + i * blockSize, blockSize, blockSize, "badlogic.jpg", 0);
                mapList.get(i).add(zeroBlock);
            }

        }
    }


    public void draw() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mapList.get(i).get(j).draw(myGdxGame);
            }
        }
    }

    boolean isTetraminoConflict(AbstractTetramino tetr) {
        for (int i = 0; i < 4; i++) {


        }
        return true;
    }

}

