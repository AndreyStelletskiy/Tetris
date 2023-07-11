package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;

public class Textures {
    public static Texture[] textures;
    static {
        textures = new Texture[6];
        for(int i = 0; i < 6; i++){
            textures[i] = new Texture("block_" + i + ".jpg");
        }
    }
}
