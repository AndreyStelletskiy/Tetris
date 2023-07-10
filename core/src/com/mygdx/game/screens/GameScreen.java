package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.TetraminoFive;
import com.mygdx.game.map.tetraminos.TetraminoFour;
import com.mygdx.game.map.tetraminos.TetraminoOne;
import com.mygdx.game.map.tetraminos.TetraminoThree;
import com.mygdx.game.map.tetraminos.TetraminoTwo;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.UiComponent;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    ArrayList<ImageView> healthBar;
    MyGdxGame myGdxGame;
    Map gameMap, miniMap;
    int time = 0;
    int moveTime = 20;
    AbstractTetramino currentTetramino, nextTetramino;
    AbstractTetramino[] forRandomArray;
    Random random;

    int gameState;
    TextButton Stop;


    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameState = 0;

        random = new Random();

        gameMap = new Map(300, 620, 20, 20, 30);
        miniMap = new Map(900, 1600, 5, 5, 30);

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



        UIInitialize();


    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (gameState == 0) {
            time = (time + 1) % moveTime;

            if (time == 0) {
                Gdx.app.debug("" + currentTetramino.INDEX, "" + currentTetramino.coordinatesX[1] + " " + currentTetramino.coordinatesY[1]);
                currentTetramino.moveDown(gameMap);
                if (currentTetramino.isMovable == false) {
                    miniMap.deleteTetramino(nextTetramino);
                    try {
                        currentTetramino = nextTetramino.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    gameMap.summon(currentTetramino);
                    try {
                        nextTetramino = forRandomArray[random.nextInt(5)].clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    miniMap.summon(nextTetramino);
                }
            }
        }
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if(component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
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

    public void UIInitialize(){
        uiComponentsList = new ArrayList<>();
        ImageView toleftButton = new ImageView(50, 50, 220, 220, "Buttons/toleft.png");
        toleftButton.setOnClickListener(toleftButtonClickListener);
        ImageView torightButton = new ImageView(810, 50, 220, 220, "Buttons/toright.png");
        torightButton.setOnClickListener(torightButtonClickListener);
        ImageView toleftrButton = new ImageView(50, 250, 220, 220, "Buttons/toleftr.png");
        toleftrButton.setOnClickListener(toleftrButtonClickListener);
        ImageView torightrButton = new ImageView(810, 250, 220, 220, "Buttons/torightr.png");
        torightrButton.setOnClickListener(torightrButtonClickListener);
        ImageView todounButton = new ImageView(430, 145, 220, 220, "Buttons/todoun.png");
        todounButton.setOnClickListener(todounButtonClickListener);
        Stop = new TextButton(myGdxGame.largeFontb.bitmapFont, "Pause", 780, 600);
        Stop.setOnClickListener(pauseButtonClickListener);
        uiComponentsList.add(Stop);
        uiComponentsList.add(toleftButton);
        uiComponentsList.add(torightButton);
        uiComponentsList.add(toleftrButton);
        uiComponentsList.add(torightrButton);
        uiComponentsList.add(todounButton);
    }

    UiComponent.OnClickListener toleftButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "toleftButtonClickListener");
        }
    };
    UiComponent.OnClickListener torightButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "torightButtonClickListener");
        }
    };
    UiComponent.OnClickListener toleftrButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "toleftrButtonClickListener");

        }
    };
    UiComponent.OnClickListener torightrButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "torightrButtonClickListener");
        }
    };
    UiComponent.OnClickListener todounButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "todounButtonClickListener");
        }
    };
    UiComponent.OnClickListener pauseButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            gameState = 1 - gameState;
            if(gameState==0){Stop.setText("Pause");}
            if(gameState==1){Stop.setText("Renew");}
        }
    };


}