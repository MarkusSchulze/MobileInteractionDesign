package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WinScreen extends ScreenAdapter {
	private SpellingBalancingGame game;
	private Stage stage;
	private Image imgWin;

	public WinScreen(SpellingBalancingGame g) {
		game = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Texture lost = new Texture(Gdx.files.internal("WinScreen.jpg"));

		imgWin = new Image(lost);
		imgWin.setPosition(0, 0);
		imgWin.setHeight(Gdx.graphics.getHeight());
		imgWin.setWidth(Gdx.graphics.getWidth());
		imgWin.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new MainScreen(game));
			}
		});
		stage.addActor(imgWin);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}
