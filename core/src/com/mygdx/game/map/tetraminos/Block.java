package com.mygdx.game.map.tetraminos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.utils.Textures;

public class Block extends ImageView {
    public int type;
    //private Texture[] textures;

    public Block(float x, float y, float width, float height, String imgSource, int type) {
        super(x, y, width, height, imgSource);
        this.type = type;
        //createTextures();
    }

    public Block(float x, float y, float width, float height, Texture imgTexture, int type) {
        super(x, y, width, height, imgTexture);
        this.type = type;
        //createTextures();
    }
    /*void createTextures(){
        textures = new Texture[6];
        for(int i = 0; i < 6; i++){
            textures[i] = new Texture("block_" + i + ".jpg");
        }
    }*/

    @Override
    public void draw(MyGdxGame myGdxGame) {
        if(type == -1){
            setImgTexture(new Texture("block_-1_" + myGdxGame.timer%2+".jpg"));
        }
        super.draw(myGdxGame);

    }

    public void setType(int type) {
        this.type = type;
        if(type == -1) {
            setImgTexture(new Texture("block_" + type + "_0.jpg"));
        }
        else{
            setImgTexture(Textures.textures[type]);
        }
    }
}
