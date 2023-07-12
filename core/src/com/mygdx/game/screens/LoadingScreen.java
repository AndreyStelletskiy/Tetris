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

import java.util.ArrayList;

public class LoadingScreen implements Screen {
    MyGdxGame myGdxGame;
    ArrayList<UiComponent> uiComponentsList;


    public LoadingScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        uiComponentsList = new ArrayList<>();

        ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/img.png");
        uiComponentsList.add(background);
    }

    @Override
    public void show() {
        Gdx.app.debug("rrr", "start");
    }


    @Override
    public void render(float delta) {

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
}
