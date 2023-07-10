package com.mygdx.game.map.tetraminos;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ui.ImageView;

public class Block extends ImageView {
    int type;

    public Block(float x, float y, float width, float height, String imgSource, int type) {
        super(x, y, width, height, imgSource);
        this.type = type;
    }

    public Block(float x, float y, float width, float height, Texture imgTexture, int type) {
        super(x, y, width, height, imgTexture);
        this.type = type;
    }
}
