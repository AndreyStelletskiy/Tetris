package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.tetraminos.AbstractTetramino;
import com.mygdx.game.map.tetraminos.TetraminoFive;
import com.mygdx.game.map.tetraminos.TetraminoFour;
import com.mygdx.game.map.tetraminos.TetraminoOne;
import com.mygdx.game.map.tetraminos.TetraminoSeven;
import com.mygdx.game.map.tetraminos.TetraminoSix;
import com.mygdx.game.map.tetraminos.TetraminoThree;
import com.mygdx.game.map.tetraminos.TetraminoTwo;
import com.mygdx.game.ui.DoubleClickImageView;
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
    ImageView StopB;
    ImageView buttonExit;

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

        gameMap = new Map((GameSettings.SCR_WIDTH - gameMapWidht * blockSize) / 2-100 - (gameMapWidht - 1) / 10 * blockSize, 640, gameMapWidht, gameMapHeight, blockSize);
        miniMap = new Map(900, 1600, 5, 5, 30);


        currentTetramino = createTetraminoWithSameType(random.nextInt(7));

        gameMap.summon(currentTetramino);


        nextTetramino = createTetraminoWithSameType(random.nextInt(7));

        miniMap.summon(nextTetramino);

        UIInitialize();


    }

    @Override
    public void show() {
        SoundExecutor.pauseStartPlaying();
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
                if( time <= difficultyStep * GameSettings.STEPS_ADDING_COLUMNS) {
                    gameMap.deleteTetramino(currentTetramino);
                    gameMap.deleteTetramino(previewTetramino);
                    gameMap.addColumns(1);

                    gameMap.addTetramino(currentTetramino);
                    gameMap.addTetramino(previewTetramino);
                    if (localScore > 500) {
                        gameMapWidht += 2;
                        gameMap.posX = (GameSettings.SCR_WIDTH - gameMapWidht * blockSize) / 2 - (gameMapWidht - 1) / 10 * blockSize;
                    }
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
                    nextTetramino = createTetraminoWithSameType(random.nextInt(7));
                    miniMap.summon(nextTetramino);
                }
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        for (UiComponent component : uiComponentsList) {
            if(component.isVisible){
            component.draw(myGdxGame);}
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
        previewTetramino.X = currentTetramino.X;
        previewTetramino.Y = currentTetramino.Y;
        for (int i = 0; i < 4; i++) {
            previewTetramino.vectorX[i] = currentTetramino.vectorX[i];
            previewTetramino.vectorY[i] = currentTetramino.vectorY[i];
        }
        previewTetramino.updateCoords(gameMap);
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

        updateScore();

        gameState = 2;
        myGdxGame.setScreen(myGdxGame.gameOverScreen);

    }

    private void updateScore() {
        MemoryLoader.saveScore(MemoryLoader.loadScore() + localScore);
        ArrayList<Integer> arrayList = MemoryLoader.loadScoreBoard();
        arrayList.add(localScore);
        Collections.sort(arrayList);
        Collections.reverse(arrayList);
        arrayList.remove(arrayList.size()-1);
        MemoryLoader.saveScoreBoard(arrayList);
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

        ImageView toLeftButton = new ImageView(30, 50, 220, 220, "Buttons/toleft.png");
        toLeftButton.setOnClickListener(toLeftButtonClickListener);
        ImageView toRightButton = new ImageView(830, 50, 220, 220, "Buttons/toright.png");
        toRightButton.setOnClickListener(toRightButtonClickListener);
        ImageView toLeftRButton = new ImageView(100, 260, 220, 220, "Buttons/rotatel.png");
        toLeftRButton.setOnClickListener(toLeftRButtonClickListener);
        ImageView toRightRButton = new ImageView(750, 260, 220, 220, "Buttons/rotater.png");
        toRightRButton.setOnClickListener(toRightRButtonClickListener);
        DoubleClickImageView toDownButton = new DoubleClickImageView(430, 145, 220, 220, "Buttons/todown.png");
        toDownButton.setOnDoubleClickListener(toDown1ButtonClickListener);
        TextView score = new TextView(myGdxGame.commonFont.bitmapFont, "Score", 920, 1875);
        //TextView scoreR = new TextView(myGdxGame.commonFont.bitmapFont, "0", 935, 1825);
        //Stop = new TextButton(myGdxGame.largeFontb.bitmapFont, "Pause", 720, 600);
        StopB = new ImageView(900, 1400, 150,150, "Buttons/pause.png");
        StopB.setOnClickListener(pauseButtonClickListener);
        buttonExit = new ImageView(900, 1200, 150, 150,"Buttons/home.png");
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        buttonExit.isVisible=false;
        uiComponentsList.add(StopB);
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
        public void onClicked(UiComponent uiComponent) {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.moveLeft(gameMap);
            gameMap.addTetramino(currentTetramino);
            SoundExecutor.playlrSound();
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener toRightButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.moveRight(gameMap);
            gameMap.addTetramino(currentTetramino);
            SoundExecutor.playlrSound();
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener toLeftRButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.rotateLeft(gameMap);
            gameMap.addTetramino(currentTetramino);
            SoundExecutor.playrevSound();

        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener toRightRButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            gameMap.deleteTetramino(currentTetramino);
            currentTetramino.rotateRight(gameMap);
            gameMap.addTetramino(currentTetramino);
            SoundExecutor.playrevSound();
        }

        @Override
        public void onClicked2() {

        }
    };
    DoubleClickImageView.OnDoubleClickListener toDown1ButtonClickListener = new DoubleClickImageView.OnDoubleClickListener() {

        @Override
        public void onClicked1() {
            if (currentTetramino.isMovable) {
                currentTetramino.moveDown(gameMap);
                SoundExecutor.playlrSound();
            }
        }

        @Override
        public void onClicked2() {
            while (currentTetramino.isMovable) {
                currentTetramino.moveDown(gameMap);
            }
            SoundExecutor.playDropSound();
        }
    };
    UiComponent.OnClickListener toDown2ButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            if (currentTetramino.isMovable) {
                currentTetramino.moveDown(gameMap);
            }
        }

        @Override
        public void onClicked2() {

        }
    };

    UiComponent.OnClickListener pauseButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            switch (gameState) {
                case 1:
                    gameState = 0;
                    break;
                case 0:
                    gameState = 1;
                    break;
            }
            if (gameState == 0) {
                StopB.setImgTexture(new Texture("Buttons/pause.png"));
                SoundExecutor.resumePlaying();
                buttonExit.isVisible=false;
            }
            if (gameState == 1) {
                StopB.setImgTexture(new Texture("Buttons/resume.png"));
                SoundExecutor.pausePlaying();
                buttonExit.isVisible=true;
            }
        }

        @Override
        public void onClicked2() {

        }
    };
    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {

            updateScore();

            myGdxGame.setScreen(myGdxGame.menuScreen);
        }

        @Override
        public void onClicked2() {

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
            case 5:
                return new TetraminoFive(4, 4);
            //return new TetraminoFour(4,4);
            case 6:
                return new TetraminoSix(4, 4);
            default:
                return new TetraminoSeven(4, 4);
        }
    }

    void keyListen() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            toLeftButtonClickListener.onClicked( new UiComponent(0,0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            toDown1ButtonClickListener.onClicked2();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            toDown2ButtonClickListener.onClicked( new UiComponent(0,0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            toRightButtonClickListener.onClicked( new UiComponent(0,0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            toRightRButtonClickListener.onClicked( new UiComponent(0,0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            toLeftRButtonClickListener.onClicked( new UiComponent(0,0));
        }
    }

}