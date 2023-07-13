package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;

public class DoubleClickImageView extends ImageView{
    DoubleClickImageView.OnDoubleClickListener onDoubleClickListener;
    int hitsCount = 0;
    public DoubleClickImageView(float x, float y, float width, float height, String imgSource) {
        super(x, y, width, height, imgSource);
    }

    @Override
    public boolean isHit(float tx, float ty) {
        Gdx.app.debug("doubleClickListener", "");
        boolean isTouchHitComponent = x < tx && tx < x + width && y < ty && ty < y + height;
        return isHit(isTouchHitComponent);
    }
    @Override
    public boolean isHit(Boolean bool){
        if(bool && onDoubleClickListener != null){
            hitsCount++;
            if(hitsCount == 1) {
                Thread waitThread = new Thread() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            try {
                                this.wait(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (hitsCount == 1){
                            Gdx.app.debug("click1", "click");
                            onDoubleClickListener.onClicked1();
                        }
                        if(hitsCount > 1){
                            Gdx.app.debug("click2", "click");
                            onDoubleClickListener.onClicked2();
                        }
                        hitsCount = 0;
                    }
                };
                waitThread.start();
            }
        }
        return bool;
    }
    public interface OnDoubleClickListener {
        void onClicked1();
        void onClicked2();
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }
}
