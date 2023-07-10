package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.Map;

public class TestScreen implements Screen {
    MyGdxGame myGdxGame;
    Map map;

    public TestScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        map = new Map(50, 10, 6, 10, 20);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        map.draw(myGdxGame);
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
