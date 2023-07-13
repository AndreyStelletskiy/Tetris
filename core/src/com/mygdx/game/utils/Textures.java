package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class Textures {
    public static HashMap<Integer, Texture> textures;
    public static String ASSET_PATH ;
    static {
        updateTextures();
    }
    public static void updateTextures(){
        ArrayList<Integer> arrayList = MemoryLoader.loadAssetStates();
        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i) == 2){
                ASSET_PATH = MemoryLoader.loadAssetPaths().get(i);
            }
        }
        textures = new HashMap<>();
        for(int i = 0; i < 8; i++){
            textures.put(i,new Texture(ASSET_PATH + "block_" + i + ".bmp") );
        }
        textures.put(-2,new Texture(ASSET_PATH + "block_" + -2 + ".bmp"));
        textures.put(-3,new Texture(ASSET_PATH + "gameBG.bmp"));
        textures.put(-4,new Texture(ASSET_PATH + "mapBG.bmp"));
    }
}