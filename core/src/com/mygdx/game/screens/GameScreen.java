package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MemoryLoader;
import com.mygdx.game.utils.SoundExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    ArrayList<UiComponent> uiComponentsListGame;
    MyGdxGame myGdxGame;
    Map gameMap, miniMap;
    int time = 0;
    int moveTime = GameSettings.MOVE_TIME;
    int difficultyStep = GameSettings.DIFFICULTY_STEP;
    public int localScore = 0;
    AbstractTetramino currentTetramino, previewTetramino, nextTetramino;

    Random random;

    int gameState, blockSize;
    public int gameMapWidht;
    public int gameMapHeight;
    TextButton Stop;
    TextButton buttonExit;

    public int getLocalScore() {
        return localScore;
    }

    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameState = 0;
        gameMapWidht = MemoryLoader.loadDifficultyMapWight().getDifficultyMapWightIdx();

        //Gdx.app.debug("gameScreen", ""+ MemoryLoader.loadDifficultyMapHeight().getDifficultyMapHeightIdx());
        gameMapHeight = MemoryLoader.loadDifficultyMapHeight().getDifficultyMapHeightIdx();
        blockSize = 30 * 20 / gameMapWidht;
        random = new Random();

        gameMap = new Map((GameSettings.SCR_WIDTH - gameMapWidht * blockSize) / 2 - (gameMapWidht - 1) / 10 * blockSize, 640, gameMapWidht, gameMapHeight, blockSize);
        miniMap = new Map(900, 1600, 5, 5, 30);


        currentTetramino = createTetraminoWithSameType(random.nextInt(5));

        gameMap.summon(currentTetramino);


        nextTetramino = createTetraminoWithSameType(random.nextInt(5));

        miniMap.summon(nextTetramino);

        UIInitialize();


    }

    @Override
    public void show() {
        SoundExecutor.playBackSound();
    }

    @Override
    public void render(float delta) {
        //обрабатывает нажатие клавиш
        keyListen();
        //обрабатывает касания
        touchListen();
        myGdxGame.timer = (myGdxGame.timer + 1) % 2;

        previewTetraminoRender();

        if (gameState == 0) {
            time ++;

            if(time % difficultyStep == 0){
                if( time <= difficultyStep * GameSettings.STEPS_ADDING_COLUMNS){
                    gameMap.addColumns(1);
                    for (int i = 0; i < 4; i++) {
                        currentTetramino.coordinatesX[i]++;
                        previewTetramino.coordinatesX[i]++;
                    }
                    gameMapWidht+=2;
                    gameMap.posX =( GameSettings.SCR_WIDTH - gameMapWidht * blockSize) / 2 - (gameMapWidht - 1) / 10 * blockSize;
                }
                else{
                    moveTime -= 1;
                    if(moveTime == 0){
                        moveTime = 1;
                    }
                }
            }

            if (time%moveTime == 0) {
                //Gdx.app.debug("" + currentTetramino.INDEX, "" + currentTetramino.coordinatesX[1] + " " + currentTetramino.coordinatesY[1]);
                currentTetramino.moveDown(gameMap);
                for (int i = 0; i < gameMapHeight; i++) {
                    if (gameMap.isStringFull(i)) {
                        //Gdx.app.debug("" + i, "string colored");
                        gameMap.colorString(i);
                    }
                }
            }
            if (time%moveTime == 0) {
                if (!currentTetramino.isMovable) {
                    ArrayList<Integer> stringsToDelete = new ArrayList<>();
                    for (int i = 0; i < gameMapHeight; i++) {
                        if (gameMap.isStringFull(i)) {
                            stringsToDelete.add(i);
                        }
                    }
                    for (int i = 0; i < stringsToDelete.size(); i++) {
                        gameMap.deleteString(stringsToDelete.get(i) - i);
                    }
                    if (stringsToDelete.size() >= 4)
                        localScore += stringsToDelete.size() * gameMapWidht * 4;
                    else {
                        localScore += stringsToDelete.size() * gameMapWidht;
                    }

                    miniMap.deleteTetramino(nextTetramino);
                    currentTetramino = createTetraminoWithSameType(nextTetramino.INDEX);
                    if (!gameMap.summon(currentTetramino)) {
                        gameOver();
                    }
                    nextTetramino = createTetraminoWithSameType(random.nextInt(5));
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
        for (UiComponent component : uiComponentsListGame) {
            component.draw(myGdxGame);
        }
        //score Drawing
        TextView scoreR = new TextView(myGdxGame.commonFont.bitmapFont, "" + localScore, 935, 1825);
        scoreR.draw(myGdxGame);


        gameMap.draw(myGdxGame);
        miniMap.draw(myGdxGame);

        myGdxGame.batch.end();

    }

    private void previewTetraminoRender() {
        if (previewTetramino != null) {
            gameMap.deleteTetramino(previewTetramino);
        }
        previewTetramino = createTetraminoWithSameType(currentTetramino.INDEX);
        for (int i = 0; i < 4; i++) {
            previewTetramino.coordinatesX[i] = currentTetramino.coordinatesX[i];
            previewTetramino.coordinatesY[i] = currentTetramino.coordinatesY[i];
        }
        previewTetramino.INDEX = -2;
        gameMap.deleteTetramino(currentTetramino);
        while (previewTetramino.isMovable) {
            previewTetramino.moveDown(gameMap);
        }
        gameMap.addTetramino(previewTetramino);
        gameMap.addTetramino(currentTetramino);
        //Gdx.app.debug("" + previewTetramino.INDEX, "" + previewTetramino.coordinatesX[1] + " " + previewTetramino.coordinatesY[1]);

    }

    private void touchListen() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if (component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
            for (UiComponent component : uiComponentsListGame) {
                if (component.isVisible && gameState == 0)
                    component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }
    }

    private void gameOver() {
        MemoryLoader.saveScore(MemoryLoader.loadScore() + localScore);
        ArrayList<Integer> arrayList = MemoryLoader.loadScoreBoard();
        arrayList.add(localScore);
        Collections.sort(arrayList);
        arrayList.remove(0);
        MemoryLoader.saveScoreBoard(arrayList);

        gameState = 2;
        myGdxGame.setScreen(myGdxGame.gameOverScreen);

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
        uiComponentsListGame = new ArrayList<>();

        ImageView toLeftButton = new ImageView(50, 50, 220, 220, "Buttons/toleft.png");
        toLeftButton.setOnClickListener(toLeftButtonClickListener);
        ImageView toRightButton = new ImageView(810, 50, 220, 220, "Buttons/toright.png");
        toRightButton.setOnClickListener(toRightButtonClickListener);
        ImageView toLeftRButton = new ImageView(50, 250, 220, 220, "Buttons/toleftr.png");
        toLeftRButton.setOnClickListener(toLeftRButtonClickListener);
        ImageView toRightRButton = new ImageView(810, 250, 220, 220, "Buttons/torightr.png");
        toRightRButton.setOnClickListener(toRightRButtonClickListener);
        ImageView toDownButton = new ImageView(430, 145, 220, 220, "Buttons/todoun.png");
        toDownButton.setOnClickListener(toDown1ButtonClickListener);
        TextView score = new TextView(myGdxGame.commonFont.bitmapFont, "Score", 920, 1875);
        //TextView scoreR = new TextView(myGdxGame.commonFont.bitmapFont, "0", 935, 1825);
        Stop = new TextButton(myGdxGame.largeFontb.bitmapFont, "Pause", 780, 600);
        Stop.setOnClickListener(pauseButtonClickListener);
        buttonExit = new TextButton(myGdxGame.largeFontb.bitmapFont, "", 25, 85);
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        uiComponentsList.add(Stop);
        uiComponentsList.add(score);
        //uiComponentsList.add(scoreR);
        uiComponentsListGame.add(toLeftButton);
        uiComponentsListGame.add(toRightButton);
        uiComponentsListGame.add(toLeftRButton);
        uiComponentsListGame.add(toRightRButton);
        uiComponentsListGame.add(toDownButton);
        uiComponentsList.add(buttonExit);
    }

    UiComponent.OnClickListener toLeftButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.moveLeft(gameMap);
            gameMap.addTetramino(currentTetramino);
        }
    };
    UiComponent.OnClickListener toRightButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.moveRight(gameMap);
            gameMap.addTetramino(currentTetramino);
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
    UiComponent.OnClickListener toDown1ButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            while (currentTetramino.isMovable) {
                currentTetramino.moveDown(gameMap);
            }
        }
    };
    UiComponent.OnClickListener toDown2ButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            if (currentTetramino.isMovable) {
                currentTetramino.moveDown(gameMap);
            }
        }
    };

    UiComponent.OnClickListener pauseButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            switch (gameState) {
                case 1:
                    gameState = 0;
                    break;
                case 0:
                    gameState = 1;
                    break;
            }
            if (gameState == 0) {
                Stop.setText("Pause");
                SoundExecutor.resumePlaying();
                buttonExit.setText("");
            }
            if (gameState == 1) {
                Stop.setText("Continue");
                SoundExecutor.pausePlaying();
                buttonExit.setText("Return Home");
            }
        }
    };
    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            MemoryLoader.saveScore(MemoryLoader.loadScore() + localScore);
            ArrayList<Integer> arrayList = MemoryLoader.loadScoreBoard();
            arrayList.add(localScore);
            Collections.sort(arrayList);
            arrayList.remove(arrayList.size()-1);
            MemoryLoader.saveScoreBoard(arrayList);
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

    AbstractTetramino createTetraminoWithSameType(int type) {
        switch (type) {
            case 1:
                return new TetraminoOne(4, 4);
            //return new TetraminoFour(4,4);
            case 2:
                return new TetraminoTwo(4, 4);
            //return new TetraminoFour(4,4);
            case 3:
                return new TetraminoThree(4, 4);
            //return new TetraminoFour(4,4);
            case 4:
                return new TetraminoFour(4, 4);
            //return new TetraminoFour(4,4);
            default:
                return new TetraminoFive(4, 4);
            //return new TetraminoFour(4,4);
        }
    }

    void keyListen() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            toLeftButtonClickListener.onClicked();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            toDown1ButtonClickListener.onClicked();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            toDown2ButtonClickListener.onClicked();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            toRightButtonClickListener.onClicked();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            toRightRButtonClickListener.onClicked();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            toLeftRButtonClickListener.onClicked();
        }
    }

}