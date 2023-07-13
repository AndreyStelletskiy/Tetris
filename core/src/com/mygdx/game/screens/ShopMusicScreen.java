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

import java.util.ArrayList;

public class ShopMusicScreen implements Screen {
    MyGdxGame myGdxGame;
    ArrayList<UiComponent> uiComponentsList;
    int itemIdx = 0;
    int songsCount = 0;
    TextButton buttonExit;
    ArrayList<ArrayList<UiComponent>> widgets;

    public ShopMusicScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    private void uiInitialize() {
        ImageView nextSongButton = new ImageView(GameSettings.SCR_WIDTH - 160, GameSettings.SCR_HEIGHT/2 - 60, 120, 120, "Buttons/toright.png");
        ImageView previousSongButton = new ImageView(40, GameSettings.SCR_HEIGHT/2 - 60, 120, 120, "Buttons/toleft.png");
        nextSongButton.setOnClickListener(nextSongButtonOnClickListener);
        previousSongButton.setOnClickListener(previousSongButtonOnClickListener);
        widgets = new ArrayList<>();
        uiComponentsList = new ArrayList<>();
        uiComponentsList.add(nextSongButton);
        uiComponentsList.add(previousSongButton);
        buttonExit = new TextButton(myGdxGame.largeFontb.bitmapFont, "Return Shop", 25, 85);
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        uiComponentsList.add(buttonExit);
        widgetsInitialize();
    }

    private void widgetsInitialize() {
        widgets = new ArrayList<>();
        for (int i = 0; i < songsCount; i++) {
            widgets.add(new ArrayList<UiComponent>());
            TextView songName = new TextView(myGdxGame.largeFont.bitmapFont, MemoryLoader.loadMusicNames().get(i), GameSettings.SCR_WIDTH/2 , GameSettings.SCR_HEIGHT/2);
            songName.x -= songName.width/2;
            TextButton textButton;
            switch (MemoryLoader.loadMusicStates().get(i)){
                case 0:
                    textButton = new TextButton(myGdxGame.commonFont.bitmapFont, "BUY" + GameSettings.MUSIC_PRICE,GameSettings.SCR_WIDTH/2, GameSettings.SCR_HEIGHT/2-300 );
                    textButton.x -= textButton.width/2;
                    textButton.setOnClickListener(onBuyOnClickListener);
                    break;
                case 1:
                    textButton = new TextButton(myGdxGame.commonFont.bitmapFont, "CHOOSE",GameSettings.SCR_WIDTH/2, GameSettings.SCR_HEIGHT/2-300 );
                    textButton.x -= textButton.width/2;
                    textButton.setOnClickListener(onChooseOnClickListener);
                    break;
                default:
                    textButton = new TextButton(myGdxGame.commonFont.bitmapFont, "PLAY",GameSettings.SCR_WIDTH/2, GameSettings.SCR_HEIGHT/2-300 );
                    textButton.x -= textButton.width/2;
                    textButton.setOnClickListener(onPlayOnClickListener);
            }

            widgets.get(i).add(songName);
            widgets.get(i).add(textButton);
        }
    }

    @Override
    public void show() {
        songsCount = MemoryLoader.loadMusicNames().size();
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

    UiComponent.OnClickListener nextSongButtonOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            stopMusic();
            itemIdx += 1;
            itemIdx = MathHelper.percent(itemIdx, songsCount);
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener previousSongButtonOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            stopMusic();
            itemIdx -= 1;
            itemIdx = MathHelper.percent(itemIdx, songsCount);
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
                ArrayList<Integer> arrayList = MemoryLoader.loadMusicStates();
                arrayList.set(itemIdx, 1);
                MemoryLoader.saveMusicStates(arrayList);
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
            ArrayList<Integer> arrayList = MemoryLoader.loadMusicStates();
            for(int i = 0; i < songsCount; i++){
                if(arrayList.get(i) == 2){
                    arrayList.set(i, 1);
                }
            }
            arrayList.set(itemIdx, 2);
            MemoryLoader.saveMusicStates(arrayList);
            widgetsInitialize();
            SoundExecutor.setBackSound();
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener onPlayOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            if(((TextButton)uiComponent).text.equals("STOP")) {
                stopMusic();
            }
            else{
                ((TextButton)uiComponent).setText("STOP");
                SoundExecutor.playBackSound();
            }
        }
        @Override
        public void onClicked2() {

        }
    };
    void stopMusic(){
        TextButton textButton = (TextButton) widgets.get(itemIdx).get(1);
        if(textButton.text.equals("STOP")){
            textButton.setText("PLAY");
            SoundExecutor.stopPlaying();
        }
    }

}
