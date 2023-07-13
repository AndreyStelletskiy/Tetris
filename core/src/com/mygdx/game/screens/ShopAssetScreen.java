package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.UiComponent;

import java.util.ArrayList;

public class ShopAssetScreen implements Screen {
    MyGdxGame myGdxGame;
    ArrayList<UiComponent> uiComponentsList;
    TextButton buttonExit;

    public ShopAssetScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        uiComponentsList = new ArrayList<>();
        buttonExit = new TextButton(myGdxGame.largeFontb.bitmapFont, "Return Shop", 25, 85);
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        uiComponentsList.add(buttonExit);

    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if(component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }

        ScreenUtils.clear(1, 1, 1, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        for (UiComponent component : uiComponentsList) {
            component.draw(myGdxGame);
        }

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {

            myGdxGame.setScreen(myGdxGame.shopScreen);
        }

        @Override
        public void onClicked2() {
            onClicked();
        }
    };
}
