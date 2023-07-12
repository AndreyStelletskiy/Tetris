package com.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingScreen;
import com.mygdx.game.screens.ShopAssetScreen;
import com.mygdx.game.screens.ShopMusicScreen;
import com.mygdx.game.screens.ShopScreen;
import com.mygdx.game.utils.GameSettings;

public class MyGdxGame extends Game {

	public OrthographicCamera camera;
	public SpriteBatch batch;
	public Vector3 touch;

	public MyCustomFont commonFont;
	public MyCustomFont largeFont;
	public MyCustomFont largeFontb;
	public MyCustomFont largeFont1;
	public MyCustomFont bblargeFont;
	public MyCustomFont secondaryFont;

	public GameOverScreen gameOverScreen;
	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public ShopScreen shopScreen;
	public ShopMusicScreen shopMusicScreen;
	public ShopAssetScreen shopAssetScreen;
	public SettingScreen settingScreen;

	public int timer;


	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		touch = new Vector3();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

		LoadingScreen loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

		largeFont = new MyCustomFont(100, "fonts/arnamu.ttf", new Color(1, 1, 1, 1));
		largeFontb = new MyCustomFont(100, "fonts/arnamu.ttf", new Color(0, 0, 0, 1));
		bblargeFont = new MyCustomFont(130, "fonts/arnamu.ttf", new Color(1, 1, 1, 1));
		largeFont1 = new MyCustomFont(75, "fonts/arnamu.ttf", new Color(1, 1, 1, 1));
		commonFont = new MyCustomFont(50, "fonts/arnamu.ttf", new Color(1, 1, 1, 1));
		secondaryFont = new MyCustomFont(50, "fonts/arnamu.ttf", new Color(0.5f, 0.5f, 1, 1));




		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		menuScreen = new MenuScreen(this);
		shopScreen = new ShopScreen(this);
		settingScreen = new SettingScreen(this);
		shopAssetScreen = new ShopAssetScreen(this);
		shopMusicScreen = new ShopMusicScreen(this);



		setScreen(menuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
