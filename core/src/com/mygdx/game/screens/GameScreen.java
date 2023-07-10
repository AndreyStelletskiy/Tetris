package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actors.Bee;
import com.mygdx.game.actors.Mosquito;
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
    ArrayList<UiComponent> uiComponentsListEndOfGame;
    ArrayList<UiComponent> uiComponentsListPause;
    ArrayList<Texture> mosquitoTextureList;
    ArrayList<Texture> beeTextureList;
    ArrayList<Mosquito> mosquitoList;
    ArrayList<Bee> beeList;
    ArrayList<ImageView> healthBar;
    MyGdxGame myGdxGame;

    int aliveMosquitoesCount;
    int aliveBeeCount;
    GameSession gameSession;

    TextButton returnButton;
    TextView textViewSessionTime;
    TextView mosquitoCounter;
    TextButton pauseButton;
    TextButton pauseExitButton;
    TextButton pauseResumeButton;

    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        Gdx.app.debug("GameScreen", "constructor");

        gameSession = new GameSession();
        mosquitoTextureList = new ArrayList<>();
        beeTextureList = new ArrayList<>();
        uiComponentsList = new ArrayList<>();
        uiComponentsListEndOfGame = new ArrayList<>();
        uiComponentsListPause  = new ArrayList<>();
        mosquitoList = new ArrayList<>();
        uiComponentsList.add(new ImageView(0, 0, GameSettings.SCR_WIDTH,
                GameSettings.SCR_HEIGHT, "backgrounds/gameBG.jpg"));

        uiComponentsListEndOfGame.add(new Blackout());
        uiComponentsListEndOfGame.add(new TextView(myGdxGame.largeFont.bitmapFont,
                "Our congratulations", -1, 900));
        uiComponentsListEndOfGame.add(new TextView(myGdxGame.commonFont.bitmapFont,
                "Your time: ", 300, 700));
        textViewSessionTime = new TextView(myGdxGame.commonFont.bitmapFont, "", 700, 700);
        returnButton = new TextButton(myGdxGame.secondaryFont.bitmapFont, "Return home", 300, 500);
        returnButton.setOnClickListener(new UiComponent.OnClickListener() {
            @Override
            public void onClicked() {
                Gdx.app.debug("onReturnClick","return clickeds");
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        });
        pauseButton =new TextButton(myGdxGame.commonFont.bitmapFont, "PAUSE",100, 980 );
        pauseButton.setOnClickListener(new UiComponent.OnClickListener() {
            @Override
            public void onClicked() {
                gameSession.gameState = GameSession.GAME_PAUSE;
            }
        });
        mosquitoCounter = new TextView(myGdxGame.commonFont.bitmapFont, "Mosquito kills: " + 0 + " / " + MemoryLoader.loadDifficultyLevel().getCountOfEnemies(), 1400, 980);
        pauseExitButton = new TextButton(myGdxGame.commonFont.bitmapFont, "Exit", GameSettings.SCR_WIDTH / 2, GameSettings.SCR_HEIGHT / 2+100);
        pauseExitButton.setOnClickListener(new UiComponent.OnClickListener() {
            @Override
            public void onClicked() {
                myGdxGame.gameScreen = new GameScreen(myGdxGame);
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        });
        pauseResumeButton = new TextButton(myGdxGame.commonFont.bitmapFont, "Resume", GameSettings.SCR_WIDTH / 2, GameSettings.SCR_HEIGHT / 2-100);
        pauseResumeButton.setOnClickListener(new UiComponent.OnClickListener() {
            @Override
            public void onClicked() {
                gameSession.gameState = GameSession.PLAY_GAME;
            }
        });


        uiComponentsListEndOfGame.add(textViewSessionTime);
        uiComponentsListEndOfGame.add(returnButton);
        uiComponentsListPause.add(pauseExitButton);
        uiComponentsListPause.add(pauseResumeButton);

        uiComponentsList.add(mosquitoCounter);
        uiComponentsList.add(pauseButton);

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
        Gdx.app.debug("Show", "Show");
        loadMosquitoes(MemoryLoader.loadDifficultyLevel());
        loadBees(MemoryLoader.loadDifficultyLevel());
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if (component.isVisible && gameSession.gameState == 0) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
            if (gameSession.gameState == GameSession.END_OF_GAME) {
                for (UiComponent component : uiComponentsListEndOfGame) {
                    if (component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
                }
            }
            if (gameSession.gameState == GameSession.GAME_PAUSE) {
                for (UiComponent component : uiComponentsListPause) {
                    if (component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
                }
            }
        }

        for (Mosquito mosquito : mosquitoList) {
            if (mosquito.isAlive && gameSession.gameState == 0) mosquito.update();
        }
        for (Bee bee: beeList){
            if (bee.isAlive && gameSession.gameState == 0) bee.update();
        }

        ScreenUtils.clear(0, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        mosquitoCounter.text = "Mosquito kills: " + (MemoryLoader.loadDifficultyLevel().getCountOfEnemies() - aliveMosquitoesCount) + " / " + MemoryLoader.loadDifficultyLevel().getCountOfEnemies();
        for (UiComponent component : uiComponentsList) {
            component.draw(myGdxGame);
        }
        for (int i = 0; i < 3 - (6 - aliveBeeCount); i++) {
            healthBar.get(i).draw(myGdxGame);
        }


        if (gameSession.gameState == GameSession.END_OF_GAME) {
            for (UiComponent component : uiComponentsListEndOfGame) {
                component.draw(myGdxGame);
            }
        }
        if (gameSession.gameState == GameSession.GAME_PAUSE) {
            for (UiComponent component : uiComponentsListPause) {
                component.draw(myGdxGame);
            }
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
    void loadBees(DifficultyLevel difficultyLevel){
        beeList = new ArrayList<>();
        aliveBeeCount = 6;
        for(int i = 0; i < 1;  i++){
            beeTextureList.add(new Texture("tiles/bee0.png"));
        }
        Texture deadBeeTexture = new Texture("tiles/bee1.png");
        for (int i = 0; i < aliveBeeCount; i++){
            Bee bee = new Bee(beeTextureList, deadBeeTexture, difficultyLevel.getEnemySpeed(), onKillBeeListener, difficultyLevel.getSize());
            beeList.add(bee);
            uiComponentsList.add(bee.actorImgView);
        }
    }

    void loadMosquitoes(DifficultyLevel difficultyLevel) {
        mosquitoList = new ArrayList<>();
        aliveMosquitoesCount = difficultyLevel.getCountOfEnemies();

        for (int i = 0; i < 9; i++)
            mosquitoTextureList.add(new Texture("tiles/mosq" + i + ".png"));
        Texture deadMosquitoTexture = new Texture("tiles/mosq10.png");

        for (int i = 0; i < aliveMosquitoesCount; i++) {
            Mosquito mosquito = new Mosquito(mosquitoTextureList, deadMosquitoTexture,
                    difficultyLevel.getEnemySpeed(), onKillMosquitoListener, difficultyLevel.getSize());
            mosquitoList.add(mosquito);
            uiComponentsList.add(mosquito.actorImgView);
        }
    }

    Mosquito.OnKillMosquitoListener onKillMosquitoListener = new Mosquito.OnKillMosquitoListener() {
        @Override
        public void onKill() {
            aliveMosquitoesCount -= 1;
            if (aliveMosquitoesCount == 0){
                gameSession.gameState = GameSession.END_OF_GAME;
                textViewSessionTime.text = gameSession.getSessionTime();
            }
        }
    };
    Bee.OnKillMosquitoListener onKillBeeListener = new Bee.OnKillMosquitoListener() {
        @Override
        public void onKill() {
            aliveBeeCount-=1;
            if(aliveBeeCount == 3){
                gameSession.gameState = GameSession.END_OF_GAME;
            }
        }
    };

}