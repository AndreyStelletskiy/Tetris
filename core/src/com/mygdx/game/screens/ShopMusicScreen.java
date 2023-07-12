package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MathHelper;

import java.util.ArrayList;

public class ShopMusicScreen implements Screen {
    MyGdxGame myGdxGame;
    ArrayList<UiComponent> uiComponentsList;
    int itemIdx = 0;
    int songsCount = 3;
    ArrayList<ArrayList<UiComponent>> widgets;

    public ShopMusicScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        uiInitialize();
    }

    private void uiInitialize() {
        ImageView nextSongButton = new ImageView(GameSettings.SCR_WIDTH - 100, GameSettings.SCR_HEIGHT/2 - 50, 60, 60, "Buttons/toright.png");
        ImageView previousSongButton = new ImageView(40, GameSettings.SCR_HEIGHT/2 - 50, 60, 60, "Buttons/toleft.png");
        nextSongButton.setOnClickListener(nextSongButtonOnClickListener);
        previousSongButton.setOnClickListener(previousSongButtonOnClickListener);
        widgets = new ArrayList<>();
        uiComponentsList = new ArrayList<>();
        uiComponentsList.add(nextSongButton);
        uiComponentsList.add(previousSongButton);
        for (int i = 0; i < songsCount; i++) {
            widgets.add(new ArrayList<UiComponent>());
            TextView songName = new TextView(myGdxGame.largeFont.bitmapFont, "song name" + i, GameSettings.SCR_WIDTH/2, GameSettings.SCR_HEIGHT/2);
            widgets.get(i).add(songName);
        }
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
            for(UiComponent component : widgets.get(itemIdx)){
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
        public void onClicked() {
            itemIdx += 1;
            itemIdx = MathHelper.percent(itemIdx, songsCount);
        }
    };
    UiComponent.OnClickListener previousSongButtonOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            itemIdx -= 1;
            itemIdx = MathHelper.percent(itemIdx, songsCount);
        }
    };
}
