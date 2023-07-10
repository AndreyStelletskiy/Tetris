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


    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameState = 0;

        random = new Random();

        gameMap = new Map(300, 500, 20, 20, 30);
        miniMap = new Map(800, 1600, 5, 5, 30);

        forRandomArray = new AbstractTetramino[5];
        forRandomArray[0] = new TetraminoOne(2, 2);
        forRandomArray[1] = new TetraminoTwo(2, 2);
        forRandomArray[2] = new TetraminoThree(2, 2);
        forRandomArray[4] = new TetraminoFive(2, 2);
        forRandomArray[3] = new TetraminoFour(2, 2);


        currentTetramino = generateWithSameType(forRandomArray[random.nextInt(5)]);

        gameMap.summon(currentTetramino);


        nextTetramino = generateWithSameType(forRandomArray[random.nextInt(5)]);
        miniMap.summon(nextTetramino);

        UIInitialize();


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
                if (component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }

        if (gameState == 0) {
            time = (time + 1) % moveTime;

            if (time == 0) {
                Gdx.app.debug("" + currentTetramino.INDEX, "" + currentTetramino.coordinatesX[1] + " " + currentTetramino.coordinatesY[1]);
                currentTetramino.moveDown(gameMap);
                if (currentTetramino.isMovable == false) {
                    for (int i = 0; i < gameMap.height; i++) {
                        if(gameMap.isStringFull(i)){
                            gameMap.deleteString(i);
                        }
                    }
                    miniMap.deleteTetramino(nextTetramino);
                    currentTetramino = generateWithSameType(nextTetramino);
                    gameMap.summon(currentTetramino);

                    nextTetramino = generateWithSameType(forRandomArray[random.nextInt(5)]);

                    miniMap.summon(nextTetramino);
                }
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

    public void UIInitialize() {
        uiComponentsList = new ArrayList<>();
        ImageView toleftButton = new ImageView(50, 50, 220, 220, "Buttons/toleft.png");
        toleftButton.setOnClickListener(toLeftButtonClickListener);
        ImageView torightButton = new ImageView(810, 50, 220, 220, "Buttons/toright.png");
        torightButton.setOnClickListener(toRightButtonClickListener);
        ImageView toleftrButton = new ImageView(50, 250, 220, 220, "Buttons/toleftr.png");
        toleftrButton.setOnClickListener(toLeftRButtonClickListener);
        ImageView torightrButton = new ImageView(810, 250, 220, 220, "Buttons/torightr.png");
        torightrButton.setOnClickListener(toRightRButtonClickListener);
        ImageView todounButton = new ImageView(430, 145, 220, 220, "Buttons/todoun.png");
        todounButton.setOnClickListener(toDownButtonClickListener);
        uiComponentsList.add(toleftButton);
        uiComponentsList.add(torightButton);
        uiComponentsList.add(toleftrButton);
        uiComponentsList.add(torightrButton);
        uiComponentsList.add(todounButton);
    }

    UiComponent.OnClickListener toLeftButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            currentTetramino.moveLeft(gameMap);
        }
    };
    UiComponent.OnClickListener toRightButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            currentTetramino.moveRight(gameMap);
        }
    };
    UiComponent.OnClickListener toLeftRButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.rotateLeft(gameMap);
            gameMap.addTetramino(currentTetramino);
        }
    };
    UiComponent.OnClickListener toRightRButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.rotateRight(gameMap);
            gameMap.addTetramino(currentTetramino);
        }
    };
    UiComponent.OnClickListener toDownButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "todounButtonClickListener");
        }
    };
    UiComponent.OnClickListener pauseButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            gameState = 1 - gameState;
        }
    };

    public AbstractTetramino generateWithSameType(AbstractTetramino abstractTetramino) {
        switch (abstractTetramino.INDEX) {
            case 1:
                return new TetraminoOne(5, 5);
            case 2:
                return new TetraminoTwo(5, 5);

            case 3:
                return new TetraminoThree(5, 5);

            case 4:
                return new TetraminoFour(5, 5);

            default:
                return new TetraminoFive(5, 5);

        }
    }


}