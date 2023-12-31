package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.DifficultyMapHeight;
import com.mygdx.game.utils.DifficultyMapWight;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MemoryLoader;
import com.mygdx.game.utils.SoundExecutor;

import java.util.ArrayList;

public class SettingScreen implements Screen {
    ArrayList<UiComponent> uiComponentsList;
    MyGdxGame myGdxGame;
    TextButton soundsButton;
    TextView difficultyWight;
    TextButton difficultyWightButton;

    TextView difficultyHeight;
    TextButton difficultyHeightButton;
    TextButton resetButton;


    public SettingScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        uiInitialize();
    }
    private void uiInitialize(){
        uiComponentsList = new ArrayList<>();

        ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/homeBG.png");
        TextView title = new TextView(myGdxGame.largeFont.bitmapFont, "Settings", -1, 1825);

        soundsButton = new TextButton(myGdxGame.largeFont1.bitmapFont, getSoundButtonText(), 120, 1400);
        soundsButton.setOnClickListener(onChangeMusicClickListener);
        TextButton buttonExit = new TextButton(myGdxGame.largeFont.bitmapFont, "Return home", 25, 175);
        buttonExit.setOnClickListener(onReturnButtonClickListener);

        difficultyWight = new TextView(myGdxGame.largeFont1.bitmapFont, getDifficultyLabelText(MemoryLoader.loadDifficultyMapWight()), 850, 1600);
        difficultyWightButton = new TextButton(myGdxGame.largeFont1.bitmapFont, "Change map wight = ", 120, 1600);
        difficultyWightButton.setOnClickListener(onChangeDifficultyWightClickListener);

        difficultyHeight = new TextView(myGdxGame.largeFont1.bitmapFont, getDifficultyLabelText(MemoryLoader.loadDifficultyMapHeight()), 850, 1500);
        difficultyHeightButton = new TextButton(myGdxGame.largeFont1.bitmapFont, "Change map height = ", 120, 1500);
        difficultyHeightButton.setOnClickListener(onChangeDifficultyHeightClickListener);

        resetButton = new TextButton(myGdxGame.largeFont1.bitmapFont, "Clear all saves", 25, 325);
        resetButton.setOnClickListener(onResetButtonClickListener);

        uiComponentsList.add(background);
        uiComponentsList.add(title);
        uiComponentsList.add(buttonExit);
        uiComponentsList.add(soundsButton);
        uiComponentsList.add(difficultyWight);
        uiComponentsList.add(difficultyWightButton);
        uiComponentsList.add(difficultyHeight);
        uiComponentsList.add(difficultyHeightButton);
        uiComponentsList.add(resetButton);
    }
    private String getDifficultyLabelText(DifficultyMapWight difficultyLevelWight) {
        switch (difficultyLevelWight) {
            case EASY:
                return "20";
            case HARD:
                return "28";
            case MEDIUM:
                return "24";
            default:
                return "(error)";
        }
    }

    private String getDifficultyLabelText(DifficultyMapHeight difficultyMapHeight) {
        switch (difficultyMapHeight) {
            case EASY:
                return "30";
            case HARD:
                return "38";
            case MEDIUM:
                return "34";
            default:
                return "(error)";
        }
    }

    @Override
    public void show() {
        SoundExecutor.pauseStartPlaying();
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

    private String getSoundButtonText() {
        boolean musicState = MemoryLoader.loadMusicState();
        if (musicState) return "Turn on game music";
        else return "Turn off game music";
    }
    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {

            myGdxGame.setScreen(myGdxGame.menuScreen);
        }

        @Override
        public void onClicked2() {

        }
    };


    UiComponent.OnClickListener onResetButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            MemoryLoader.clearAllSaves();
            uiInitialize();
        }

        @Override
        public void onClicked2() {

        }
    };


    UiComponent.OnClickListener onChangeMusicClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            boolean isMusicOn = MemoryLoader.loadMusicState();
            MemoryLoader.saveMusicState(!isMusicOn);
            soundsButton.text = getSoundButtonText();
        }

        @Override
        public void onClicked2() {

        }
    };

    UiComponent.OnClickListener onChangeDifficultyWightClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {
            DifficultyMapWight difficultyMapWight = MemoryLoader.loadDifficultyMapWight();
            switch (difficultyMapWight) {
                case EASY:
                    difficultyMapWight = DifficultyMapWight.MEDIUM;
                    break;
                case MEDIUM:
                    difficultyMapWight = DifficultyMapWight.HARD;
                    break;
                case HARD:
                    difficultyMapWight = DifficultyMapWight.EASY;
                    break;
            }
            difficultyWight.text = getDifficultyLabelText(difficultyMapWight);
            MemoryLoader.saveDifficultyLevelWight(difficultyMapWight);


        }

        @Override
        public void onClicked2() {

        }
    };

    UiComponent.OnClickListener onChangeDifficultyHeightClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked(UiComponent uiComponent) {

            DifficultyMapHeight difficultyMapHeight = MemoryLoader.loadDifficultyMapHeight();
            switch (difficultyMapHeight) {
                case EASY:
                    difficultyMapHeight = DifficultyMapHeight.MEDIUM;
                    break;
                case MEDIUM:
                    difficultyMapHeight = DifficultyMapHeight.HARD;
                    break;
                case HARD:
                    difficultyMapHeight = DifficultyMapHeight.EASY;
                    break;
            }
            difficultyHeight.text = getDifficultyLabelText(difficultyMapHeight);
            MemoryLoader.saveDifficultyLevelHeight(difficultyMapHeight);

        }

        @Override
        public void onClicked2() {

        }
    };

}
