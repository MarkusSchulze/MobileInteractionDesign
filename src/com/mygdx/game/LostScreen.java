package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LostScreen extends ScreenAdapter {
	private SpellingBalancingGame game;
	private Stage stage;
	private Image imgLost;
	private Image imgExit;
	private Image imgRetry;

	public LostScreen(SpellingBalancingGame g, final String lastWord) {
		game = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		Texture lost = new Texture(Gdx.files.internal("LostScreen.jpg"));

		imgLost = new Image(lost);
		imgLost.setPosition(0, 0);
		imgLost.setHeight(Gdx.graphics.getHeight());
		imgLost.setWidth(Gdx.graphics.getWidth());
		imgLost.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new MainScreen(game));
			}
		});

		Label letterCounter = new Label(lastWord, skin);
		letterCounter.setPosition(Gdx.graphics.getWidth() / 4 * 3, Gdx.graphics.getHeight() / 8);
		letterCounter.setSize(Gdx.graphics.getWidth() / 10 * 3, Gdx.graphics.getHeight() / 8);
		letterCounter.setFontScale(3);
		stage.addActor(letterCounter);

		Texture retry = new Texture(Gdx.files.internal("restart.png"));
		imgRetry = new Image(retry);
		imgRetry.setPosition(Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 20);
		imgRetry.setSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		imgRetry.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new PictureScreen(game, lastWord));
			}
		});

		Texture exit = new Texture(Gdx.files.internal("Exit.png"));
		imgExit = new Image(exit);
		imgExit.setPosition(Gdx.graphics.getWidth() / 8 * 7, Gdx.graphics.getHeight() / 8 * 7);
		imgExit.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgExit.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new MainScreen(game));
			}
		});

		stage.addActor(imgLost);
		stage.addActor(imgExit);
		stage.addActor(imgRetry);
		stage.addActor(letterCounter);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}
