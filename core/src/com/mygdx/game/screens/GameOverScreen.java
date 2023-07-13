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

public class GameOverScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    MyGdxGame myGdxGame;
    TextView bResultst;
    TextView prResultst;
    TextView titleGOt;

    public GameOverScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    private void uiInitialize() {
        uiComponentsList = new ArrayList<>();

        ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/homeBG.png");
        TextView title = new TextView(myGdxGame.largeFont.bitmapFont, "Game Over", -1, 1825);
        TextView titleGO = new TextView(myGdxGame.largeFont.bitmapFont, "Your results", 35, 1600);
        titleGOt = new TextView(myGdxGame.largeFont.bitmapFont,""+myGdxGame.gameScreen.getLocalScore() , 605, 1600);

        TextView prResults = new TextView(myGdxGame.largeFont.bitmapFont, "Total score", 35, 1450);
        prResultst = new TextView(myGdxGame.largeFont.bitmapFont, ""+MemoryLoader.loadScore(), 525, 1450);
        TextView bResults = new TextView(myGdxGame.largeFont.bitmapFont, "Best result", 35, 1300);
        bResultst = new TextView(myGdxGame.largeFont.bitmapFont, ""+MemoryLoader.loadScoreBoard().get(0), 525, 1300);



        TextButton buttonExit = new TextButton(myGdxGame.largeFont.bitmapFont, "To home", 25, 175);
        buttonExit.setOnClickListener(onReturnButtonClickListener);
        TextButton buttonStart = new TextButton(myGdxGame.largeFont.bitmapFont, "restart", 725, 175);
        buttonStart.setOnClickListener(onButtonStartClicked);
        uiComponentsList.add(background);
        uiComponentsList.add(prResults);
        uiComponentsList.add(prResultst);
        uiComponentsList.add(bResults);
        uiComponentsList.add(bResultst);
        uiComponentsList.add(title);
        uiComponentsList.add(titleGO);
        uiComponentsList.add(titleGOt);
        uiComponentsList.add(buttonExit);
        uiComponentsList.add(buttonStart);
    }


    @Override
    public void show() {
        uiInitialize();
        myGdxGame.gameScreen = new GameScreen(myGdxGame);
        SoundExecutor.stopPlaying();
        SoundExecutor.playGameOutSound();

    }

    @Override
    public void render(float delta) {

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

    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked1", "onReturnButtonClicked1");
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }

        @Override
        public void onClicked2() {
            onClicked();
        }
    };
    UiComponent.OnClickListener onButtonStartClicked = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }

        @Override
        public void onClicked2() {
            onClicked();
        }
    };
}
