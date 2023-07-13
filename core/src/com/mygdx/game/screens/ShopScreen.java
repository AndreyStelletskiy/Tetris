package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.SoundExecutor;

import java.util.ArrayList;

public class ShopScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    MyGdxGame myGdxGame;


    public ShopScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        uiInitialize();
    }

    private void uiInitialize() {

        uiComponentsList = new ArrayList<>();
        ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/homeBG.png");
        TextView title = new TextView(myGdxGame.largeFont.bitmapFont, "Shop", -1, 1825);
        TextButton buttonExit = new TextButton(myGdxGame.largeFont.bitmapFont, "Return home", 25, 175);
        TextButton musicShop = new TextButton(myGdxGame.largeFont.bitmapFont, "Music shop", GameSettings.SCR_WIDTH / 2- 100, GameSettings.SCR_HEIGHT / 2 + 100);
        TextButton assetShop = new TextButton(myGdxGame.largeFont.bitmapFont, "Asset ", GameSettings.SCR_WIDTH / 2- 100, GameSettings.SCR_HEIGHT / 2 - 100);
        musicShop.setOnClickListener(onMusicShopClickListener);
        assetShop.setOnClickListener(onAssetShopClickListener);
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        uiComponentsList.add(background);
        uiComponentsList.add(title);
        uiComponentsList.add(buttonExit);
        uiComponentsList.add(musicShop);
        uiComponentsList.add(assetShop);
    }

    @Override
    public void show() {
        SoundExecutor.stopStartSound();
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

        ScreenUtils.clear(0, 0, 0, 1);
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
        SoundExecutor.stopStartSound();
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
        public void onClicked(UiComponent uiComponent) {
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener onMusicShopClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            myGdxGame.setScreen(myGdxGame.shopMusicScreen);
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener onAssetShopClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            Gdx.app.debug("onClicked1", "onReturnButtonClicked1");
            myGdxGame.setScreen(myGdxGame.shopAssetScreen);
        }

        @Override
        public void onClicked2() {

        }
    };
}
