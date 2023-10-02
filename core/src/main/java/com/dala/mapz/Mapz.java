package com.dala.mapz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dala.mapz.screens.GameScreen;
import com.dala.mapz.screens.RestartScreen;
import com.dala.mapz.screens.MenuScreen;
import com.dala.mapz.gamebase.element.Material;
import com.dala.mapz.utils.Constants;
import com.dala.mapz.utils.SoundManager;

/**
 * Classe principale.
 */
public class Mapz extends Game {
	private static Mapz gameInstance;
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private RestartScreen restartScreen;
	private SpriteBatch batch;
	private OrthographicCamera camera;


	@Override
	public void create () {
		gameScreen = new GameScreen();
		menuScreen = new MenuScreen();
		restartScreen = new RestartScreen();
		gameInstance = this;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
		batch = new SpriteBatch();
		this.setScreen(menuScreen);
		Gdx.input.setInputProcessor(menuScreen.getStage());
	}

	@Override
	public void render () {
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height)
	{
		this.getScreen().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public static Mapz getInstance() {
		return gameInstance;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setRestartScreen() {
		Gdx.input.setInputProcessor(restartScreen.getStage());
		setScreen(restartScreen);
	}

	public void setGameScreen() {
		Gdx.input.setInputProcessor(null);
		gameScreen.reset();
		setScreen(gameScreen);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	@Override
	public void dispose () {
		gameScreen.dispose();
		menuScreen.dispose();
		restartScreen.dispose();
		batch.dispose();
		Material.dispose();
		SoundManager.dispose();
	}
}
