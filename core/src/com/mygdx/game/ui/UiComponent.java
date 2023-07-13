package com.mygdx.game.ui;

import com.mygdx.game.MyGdxGame;

public class UiComponent {

    public float x;
    public float y;
    float width;
    float height;
    public boolean isVisible;
    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    UiComponent(float x, float y, float width, float height) {
        this(x, y);
        this.width = width;
        this.height = height;
        isVisible = true;
    }

    UiComponent(float x, float y) {
        this.x = x;
        this.y = y;
        this.onClickListener = null;
        isVisible = true;
    }

    public void draw(MyGdxGame myGdxGame) {};

    public boolean isHit(float tx, float ty){
        boolean isTouchHitComponent = x < tx && tx < x + width && y > ty && ty > y - height;

        return isHit(isTouchHitComponent);
    }
    public boolean isHit(Boolean bool){
        if(bool && onClickListener != null){
            onClickListener.onClicked();
        }
        return bool;
    }

    public interface OnClickListener {
        void onClicked();
        void onClicked2();

    }
}
