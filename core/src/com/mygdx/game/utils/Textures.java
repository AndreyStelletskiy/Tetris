package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Textures {
    public static HashMap<Integer, Texture> textures;

    static {
        textures = new HashMap<>();
        for(int i = 0; i < 8; i++){

            textures.put(i,new Texture("block_" + i + ".bmp") );
        }
        textures.put(-2,new Texture("block_" + -2 + ".bmp"));
    }
}
