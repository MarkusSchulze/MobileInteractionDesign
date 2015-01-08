package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LostScreen extends ScreenAdapter {
	private Game game;
	private Stage stage;
	private Image imgLost;

	public LostScreen(Game g) {
		game = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
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
		stage.addActor(imgLost);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}
