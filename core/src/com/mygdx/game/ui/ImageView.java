package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class ImageView extends UiComponent{

    String imgSource;
    Texture imgTexture;

    public ImageView(float x, float y, float width, float height, String imgSource) {
        super(x, y, width, height);
        this.imgSource = imgSource;
        imgTexture = new Texture(imgSource);
    }

    public ImageView(float x, float y, float width, float height, Texture imgTexture) {
        super(x, y, width, height);
        this.imgTexture = imgTexture;
    }

    @Override
    public void draw(MyGdxGame myGdxGame) {
        myGdxGame.batch.begin();
        myGdxGame.batch.draw(imgTexture, x, y, width, height);
        myGdxGame.batch.end();
    }
    @Override
    public boolean isHit(float tx, float ty) {
        boolean isTouchHitComponent = x < tx && tx < x + width && y < ty && ty < y + height;
        if (isTouchHitComponent && onClickListener != null) onClickListener.onClicked();
        return isTouchHitComponent;
    }
    public void setImgTexture(Texture texture) {
        imgTexture = texture;
    }
}
