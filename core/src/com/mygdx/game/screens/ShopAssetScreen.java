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
import com.mygdx.game.utils.MathHelper;
import com.mygdx.game.utils.MemoryLoader;
import com.mygdx.game.utils.SoundExecutor;
import com.mygdx.game.utils.Textures;

import java.util.ArrayList;

public class ShopAssetScreen implements Screen {
    MyGdxGame myGdxGame;
    ArrayList<UiComponent> uiComponentsList;
    int itemIdx = 0;
    int assetsCount = 0;
    TextButton buttonExit;
    ArrayList<ArrayList<UiComponent>> widgets;

    public ShopAssetScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    private void uiInitialize() {
        ImageView nextWidgetButton = new ImageView(GameSettings.SCR_WIDTH - 100, GameSettings.SCR_HEIGHT/2 - 50, 60, 60, "Buttons/toright.png");
        ImageView previousWidgetButton = new ImageView(40, GameSettings.SCR_HEIGHT/2 - 50, 60, 60, "Buttons/toleft.png");
        nextWidgetButton.setOnClickListener(nextWidgetButtonOnClickListener);
        previousWidgetButton.setOnClickListener(previousWidgetButtonOnClickListener);
        uiComponentsList = new ArrayList<>();
        uiComponentsList.add(nextWidgetButton);
        uiComponentsList.add(previousWidgetButton);
        buttonExit = new TextButton(myGdxGame.largeFontb.bitmapFont, "Return Shop", 25, 85);
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        uiComponentsList.add(buttonExit);
        widgetsInitialize();
    }

    private void widgetsInitialize() {
        widgets = new ArrayList<>();
        for (int i = 0; i < assetsCount; i++) {
            widgets.add(new ArrayList<UiComponent>());
            TextView songName = new TextView(myGdxGame.largeFont.bitmapFont, MemoryLoader.loadAssetNames().get(i), GameSettings.SCR_WIDTH/2-200, GameSettings.SCR_HEIGHT/2);
            TextButton textButton;
            switch (MemoryLoader.loadMusicStates().get(i)){
                case 0:
                    textButton = new TextButton(myGdxGame.commonFont.bitmapFont, "BUY" + GameSettings.ASSET_PRICE,GameSettings.SCR_WIDTH/2-200, GameSettings.SCR_HEIGHT/2-300 );
                    textButton.setOnClickListener(onBuyOnClickListener);
                    break;
                case 1:
                    textButton = new TextButton(myGdxGame.commonFont.bitmapFont, "CHOOSE",GameSettings.SCR_WIDTH/2-200, GameSettings.SCR_HEIGHT/2-300 );
                    textButton.setOnClickListener(onChooseOnClickListener);
                    break;
                default:
                    textButton = new TextButton(myGdxGame.commonFont.bitmapFont, "CHOOSEN",GameSettings.SCR_WIDTH/2-200, GameSettings.SCR_HEIGHT/2-300 );
            }

            widgets.get(i).add(songName);
            widgets.get(i).add(textButton);
        }
    }

    @Override
    public void show() {
        assetsCount = MemoryLoader.loadAssetNames().size();
        uiInitialize();
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if(component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
            for(UiComponent component : widgets.get(itemIdx)){
                if(component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }

        ScreenUtils.clear(0.25F, 0.25F, 0.25F, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        for (UiComponent component : uiComponentsList) {
            component.draw(myGdxGame);
        }
        for(UiComponent component : widgets.get(itemIdx)){
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

    UiComponent.OnClickListener nextWidgetButtonOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            itemIdx += 1;
            itemIdx = MathHelper.percent(itemIdx, assetsCount);
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener previousWidgetButtonOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            itemIdx -= 1;
            itemIdx = MathHelper.percent(itemIdx, assetsCount);
        }

        @Override
        public void onClicked2() {

        }
    };

    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            SoundExecutor.stopPlaying();
            myGdxGame.setScreen(myGdxGame.shopScreen);
        }

        @Override
        public void onClicked2() {

        }
    };

    UiComponent.OnClickListener onBuyOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            if(MemoryLoader.loadScore() >= GameSettings.MUSIC_PRICE) {
                ArrayList<Integer> arrayList = MemoryLoader.loadAssetStates();
                arrayList.set(itemIdx, 1);
                MemoryLoader.saveAssetStates(arrayList);
                widgetsInitialize();
                MemoryLoader.saveScore(MemoryLoader.loadScore() - GameSettings.MUSIC_PRICE);
            }
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener onChooseOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            ArrayList<Integer> arrayList = MemoryLoader.loadAssetStates();
            for(int i = 0; i < assetsCount; i++){
                if(arrayList.get(i) == 2){
                    arrayList.set(i, 1);
                }
            }
            arrayList.set(itemIdx, 2);
            MemoryLoader.saveAssetStates(arrayList);
            widgetsInitialize();
        }

        @Override
        public void onClicked2() {

        }
    };



}
