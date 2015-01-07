package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class PictureScreen extends ScreenAdapter {
	private Game game;
	private Image imgC1;
	private Image imgC2;
	private Image imgC3;
	private Image choosenPicture;
	private Stage stage;
	private String choosenWord;

	public PictureScreen(Game g) {
		game = g;
		Texture countdown1 = new Texture(Gdx.files.internal("countdown1.jpg"));
		Texture countdown2 = new Texture(Gdx.files.internal("countdown2.jpg"));
		Texture countdown3 = new Texture(Gdx.files.internal("countdown3.jpg"));
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		imgC1 = new Image(countdown1);
		imgC1.setPosition(0, 0);
		imgC1.setHeight(Gdx.graphics.getHeight());
		imgC1.setWidth(Gdx.graphics.getWidth());
		imgC1.setVisible(false);
		imgC1.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				game.setScreen(new GameScreen(game, choosenWord));
			}
		});

		imgC2 = new Image(countdown2);
		imgC2.setPosition(0, 0);
		imgC2.setHeight(Gdx.graphics.getHeight());
		imgC2.setWidth(Gdx.graphics.getWidth());
		imgC2.setVisible(false);
		imgC2.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				imgC1.setVisible(true);
			}
		});

		imgC3 = new Image(countdown3);
		imgC3.setPosition(0, 0);
		imgC3.setHeight(Gdx.graphics.getHeight());
		imgC3.setWidth(Gdx.graphics.getWidth());
		imgC3.setVisible(false);
		imgC3.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				imgC2.setVisible(true);
			}
		});

		initPicture();
		stage.addActor(imgC3);
		stage.addActor(imgC2);
		stage.addActor(imgC1);
	}

	public void initPicture() {
		Array<String> wordsToSpell = new Array<String>();
		wordsToSpell.add("CAT");
		wordsToSpell.add("DOG");
		wordsToSpell.add("CAR");
		wordsToSpell.add("COMPUTER");
		wordsToSpell.add("RADIO");
		wordsToSpell.add("MONITOR");
		wordsToSpell.add("BEER");
		wordsToSpell.add("TREE");
		wordsToSpell.add("PRINTER");
		wordsToSpell.add("KEYBOARD");
		wordsToSpell.add("CHAIR");
		choosenWord = wordsToSpell.get(MathUtils.random(wordsToSpell.size - 1));

		choosenWord = "CAT";
		choosenWord += ".jpg";

		Texture choosenTexture = new Texture(Gdx.files.internal("pictures/" + choosenWord));
		choosenPicture = new Image(choosenTexture);
		choosenPicture.setPosition(0, 0);
		choosenPicture.setHeight(Gdx.graphics.getHeight());
		choosenPicture.setWidth(Gdx.graphics.getWidth());
		choosenPicture.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				imgC3.setVisible(true);
			}
		});
		stage.addActor(choosenPicture);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}
