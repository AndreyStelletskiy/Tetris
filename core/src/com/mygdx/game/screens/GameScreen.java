package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.Map;
import com.mygdx.game.ui.Blackout;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.DifficultyLevel;
import com.mygdx.game.utils.GameSession;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MemoryLoader;

import java.util.ArrayList;

public class GameScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    ArrayList<ImageView> healthBar;
    MyGdxGame myGdxGame;
    Map gameMap;

    GameSession gameSession;

    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        Gdx.app.debug("GameScreen", "constructor");

        gameSession = new GameSession();

        uiComponentsList = new ArrayList<>();


        healthBar = new ArrayList<>();
        ImageView hearth1 = new ImageView(0,0,100, 100, "icons/hearth.png");
        ImageView hearth2 = new ImageView(150,0,100, 100, "icons/hearth.png");
        ImageView hearth3 = new ImageView(300,0,100, 100, "icons/hearth.png");
        healthBar.add(hearth1);
        healthBar.add(hearth2);
        healthBar.add(hearth3);
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



        ScreenUtils.clear(0, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        for (UiComponent component : uiComponentsList) {
            component.draw(myGdxGame);
        }

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