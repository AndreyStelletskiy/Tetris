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
import com.mygdx.game.utils.MemoryLoader;
import com.mygdx.game.utils.SoundExecutor;

import java.util.ArrayList;

public class MenuScreen implements Screen {

    public MyGdxGame myGdxGame;
    ArrayList<UiComponent> uiComponentsList;
    TextView scr1;
    TextView scr2;
    TextView scr3;
    TextView scr4;
    TextView scr5;
    TextView scrTotal;


    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        uiComponentsList = new ArrayList<>();

        ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/homeBG.png");
        TextView title = new TextView(myGdxGame.largeFont.bitmapFont, "Tetris", -1, 1825);

        TextView scrboard = new TextView(myGdxGame.largeFont.bitmapFont, "Score Board!", 45, 1500);
        TextView totalScore = new TextView(myGdxGame.largeFont.bitmapFont, "Total Coins", 45, 1625);
        TextView scrboard1 = new TextView(myGdxGame.largeFont1.bitmapFont, "1.", 95, 1400);
        TextView scrboard2 = new TextView(myGdxGame.largeFont1.bitmapFont, "2.", 95, 1300);
        TextView scrboard3 = new TextView(myGdxGame.largeFont1.bitmapFont, "3.", 95, 1200);
        TextView scrboard4 = new TextView(myGdxGame.largeFont1.bitmapFont, "4.", 95, 1100);
        TextView scrboard5 = new TextView(myGdxGame.largeFont1.bitmapFont, "5.", 95, 1000);


        ArrayList<Integer> scoreBoard = MemoryLoader.loadScoreBoard();
        Gdx.app.debug("score", scoreBoard.toString());
        scr1 = new TextView(myGdxGame.largeFont1.bitmapFont, "" + scoreBoard.get(0), 175, 1400);
        scr2 = new TextView(myGdxGame.largeFont1.bitmapFont, "" + scoreBoard.get(1), 175, 1300);
        scr3 = new TextView(myGdxGame.largeFont1.bitmapFont, "" + scoreBoard.get(2), 175, 1200);
        scr4 = new TextView(myGdxGame.largeFont1.bitmapFont, "" + scoreBoard.get(3), 175, 1100);
        scr5 = new TextView(myGdxGame.largeFont1.bitmapFont, "" + scoreBoard.get(4), 175, 1000);
        scrTotal = new TextView(myGdxGame.largeFont.bitmapFont, "" + MemoryLoader.loadScore(), 575, 1625);

        TextButton buttonStart = new TextButton(myGdxGame.bblargeFont.bitmapFont, "Play", 75, 850);
        buttonStart.setOnClickListener(onButtonStartClicked);

        TextButton buttonShop = new TextButton(myGdxGame.largeFont.bitmapFont, "Shop", 75, 700);
        buttonShop.setOnClickListener(onButtonShopClicked);

        TextButton buttonSettings = new TextButton(myGdxGame.largeFont.bitmapFont, "Setting", 75, 550);
        buttonSettings.setOnClickListener(onButtonSettingsClicked);

        TextButton buttonExit = new TextButton(myGdxGame.largeFont.bitmapFont, "Exit", 35, 175);
        buttonExit.setOnClickListener(onButtonExitClicked);

        uiComponentsList.add(background);
        uiComponentsList.add(title);
        uiComponentsList.add(scrboard);
        uiComponentsList.add(scrboard1);
        uiComponentsList.add(scrboard2);
        uiComponentsList.add(scrboard3);
        uiComponentsList.add(scrboard4);
        uiComponentsList.add(scrboard5);
        uiComponentsList.add(scr1);
        uiComponentsList.add(scr2);
        uiComponentsList.add(scr3);
        uiComponentsList.add(scr4);
        uiComponentsList.add(scr5);
        uiComponentsList.add(totalScore);
        uiComponentsList.add(scrTotal);
        uiComponentsList.add(buttonStart);
        uiComponentsList.add(buttonShop);
        uiComponentsList.add(buttonSettings);
        uiComponentsList.add(buttonExit);

    }

    @Override
    public void show() {
        SoundExecutor.stopPlaying();
        SoundExecutor.playStartSound();
        ArrayList<Integer> scoreBoard = MemoryLoader.loadScoreBoard();
        Gdx.app.debug("score", scoreBoard.toString());
        scr1.text = "" + scoreBoard.get(0);
        scr2.text = "" + scoreBoard.get(1);
        scr3.text = "" + scoreBoard.get(2);
        scr4.text = "" + scoreBoard.get(3);
        scr5.text = "" + scoreBoard.get(4);
        scrTotal.text = "" + MemoryLoader.loadScore();
        myGdxGame.gameScreen = new GameScreen(myGdxGame);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if (component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.begin();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

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


    private final UiComponent.OnClickListener onButtonStartClicked = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            Gdx.app.debug("onClicked1", "onButtonStartClicked");
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }

        @Override
        public void onClicked2() {

        }
    };


    private final UiComponent.OnClickListener onButtonShopClicked = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            Gdx.app.debug("onClicked1", "onButtonShopClicked");
            myGdxGame.setScreen(myGdxGame.shopScreen);
        }

        @Override
        public void onClicked2() {

        }
    };

    private final UiComponent.OnClickListener onButtonSettingsClicked = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            Gdx.app.debug("onClicked1", "onButtonSettingClicked");
            myGdxGame.setScreen(myGdxGame.settingScreen);

        }

        @Override
        public void onClicked2() {

        }
    };

    private final UiComponent.OnClickListener onButtonExitClicked = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            Gdx.app.debug("onClicked1", "onButtonExitClicked");
            Gdx.app.exit();
        }

        @Override
        public void onClicked2() {

        }
    };
}