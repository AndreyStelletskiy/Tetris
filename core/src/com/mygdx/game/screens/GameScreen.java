package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.GameSettings;

import java.util.ArrayList;

public class GameScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    MyGdxGame myGdxGame;


    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        uiComponentsList = new ArrayList<>();
        //ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/homeBG.png");
        //uiComponentsList.add(background);
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
                if (component.isVisible ) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }



        ScreenUtils.clear(190, 190, 190, 1);
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