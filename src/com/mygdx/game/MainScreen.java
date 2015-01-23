package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainScreen extends ScreenAdapter {

	private SpellingBalancingGame game;
	private Stage stage;

	public MainScreen(SpellingBalancingGame g) {
		game = g;
		float ScreenHeight = Gdx.graphics.getHeight();
		float ScreenWidth = Gdx.graphics.getWidth();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		Texture texture = new Texture(Gdx.files.internal("StartScreen.jpg"));

		Image imgBackground = new Image(texture);
		imgBackground.setPosition(0, 0);
		imgBackground.setAlign(Align.bottomRight);
		imgBackground.setHeight(Gdx.graphics.getHeight());
		imgBackground.setWidth(Gdx.graphics.getWidth());

		TextButton btnStart = new TextButton("Start Game", skin);
		btnStart.setPosition(ScreenWidth / 5, ScreenHeight / 2);
		btnStart.setSize(ScreenWidth / 4, ScreenHeight / 10);
		btnStart.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new PictureScreen(game, ""));
			}
		});

		TextButton btnOptions = new TextButton("Options", skin);
		btnOptions.setPosition(ScreenWidth / 5, ScreenHeight / 8 * 3);
		btnOptions.setSize(ScreenWidth / 4, ScreenHeight / 10);
		btnOptions.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new OptionScreen(game));
			}
		});

		TextButton btnScore = new TextButton("Score", skin);
		btnScore.setPosition(ScreenWidth / 5, ScreenHeight / 4);
		btnScore.setSize(ScreenWidth / 4, ScreenHeight / 10);

		stage.addActor(imgBackground);
		stage.addActor(btnScore);
		stage.addActor(btnOptions);
		stage.addActor(btnStart);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}
