package com.mygdx.game.map.tetraminos;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ui.ImageView;

public class Block extends ImageView {
    public int type;

    public Block(float x, float y, float width, float height, String imgSource, int type) {
        super(x, y, width, height, imgSource);
        this.type = type;
    }

    public Block(float x, float y, float width, float height, Texture imgTexture, int type) {
        super(x, y, width, height, imgTexture);
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
        setImgTexture(new Texture("block_" + type + ".jpg" ));
    }
}
