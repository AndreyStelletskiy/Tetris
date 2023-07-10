package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.TetraminoFive;
import com.mygdx.game.map.tetraminos.TetraminoFour;
import com.mygdx.game.map.tetraminos.TetraminoOne;
import com.mygdx.game.map.tetraminos.TetraminoThree;
import com.mygdx.game.map.tetraminos.TetraminoTwo;
import com.mygdx.game.ui.Blackout;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    ArrayList<ImageView> healthBar;
    MyGdxGame myGdxGame;
    Map gameMap, miniMap;
    int time = 0;
    int moveTime = 180;
    AbstractTetramino currentTetramino, nextTetramino;
    AbstractTetramino[] forRandomArray;
    Random random;


    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        random = new Random();

        gameMap = new Map(300, 500, 20, 20, 30);
        miniMap = new Map(800, 1600, 5, 5, 30);

        uiComponentsList = new ArrayList<>();
        forRandomArray = new AbstractTetramino[5];
        forRandomArray[0] = new TetraminoOne(2,2);
        forRandomArray[1] = new TetraminoTwo(2,2);
        forRandomArray[2] = new TetraminoThree(2,2);
        forRandomArray[4] = new TetraminoFive(2,2);
        forRandomArray[3] = new TetraminoFour(2,2);

        try {
            currentTetramino = forRandomArray[random.nextInt( 5)].clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        gameMap.summon(currentTetramino);

        try {
            nextTetramino = forRandomArray[random.nextInt( 5)].clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        miniMap.summon(nextTetramino);

        Gdx.app.debug("current", "" + currentTetramino.INDEX);






    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        time = (time + 1) % moveTime;
        if (time == -1){
            Gdx.app.debug("moving down", "moving down");
            currentTetramino.moveDown(gameMap);
            if(currentTetramino.isMovable == false){
                miniMap.deleteTetramino(nextTetramino);
                try {
                    currentTetramino =  nextTetramino.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                gameMap.summon(currentTetramino);
                try {
                    nextTetramino = forRandomArray[random.nextInt( 5)].clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                miniMap.summon(nextTetramino);
            }
        }
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

        gameMap.draw(myGdxGame);
        miniMap.draw(myGdxGame);

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