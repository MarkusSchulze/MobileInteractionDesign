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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionScreen extends ScreenAdapter {
	private SpellingBalancingGame game;
	private Stage stage;
	private Image imgOn1;
	private Image imgOn2;
	private Image imgOff1;
	private Image imgOff2;
	private TextField userName;

	public OptionScreen(SpellingBalancingGame g) {
		game = g;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		final Texture On = new Texture(Gdx.files.internal("On.png"));
		final Texture Off = new Texture(Gdx.files.internal("Off.png"));
		final Texture On_Off = new Texture(Gdx.files.internal("On_Off.png"));
		final Texture Off_Off = new Texture(Gdx.files.internal("Off_Off.png"));

		Texture texture = new Texture(Gdx.files.internal("StartScreen.jpg"));
		Image imgBackground = new Image(texture);
		imgBackground.setPosition(0, 0);
		imgBackground.setAlign(Align.bottomRight);
		imgBackground.setHeight(Gdx.graphics.getHeight());
		imgBackground.setWidth(Gdx.graphics.getWidth());

		Label name = new Label("Name:", skin);
		name.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 2);
		name.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
		name.setFontScale(2);

		userName = new TextField("Player1", skin);
		userName.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2);
		userName.setSize(Gdx.graphics.getWidth() / 4 + 10, Gdx.graphics.getHeight() / 10);

		Label sound = new Label("Sound:", skin);
		sound.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8 * 3);
		sound.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
		sound.setFontScale(2);

		if (game.sound) {
			imgOn2 = new Image(On);
			imgOff2 = new Image(Off_Off);
		} else {
			imgOn2 = new Image(On_Off);
			imgOff2 = new Image(Off);
		}
		// imgOn2 = new Image(On);
		imgOn2.setPosition(Gdx.graphics.getWidth() / 10 * 3, Gdx.graphics.getHeight() / 8 * 3);
		imgOn2.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgOn2.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				imgOn2 = new Image(On);
				imgOff2 = new Image(Off_Off);
			}
		});

		// imgOff2 = new Image(Off);
		imgOff2.setPosition(Gdx.graphics.getWidth() / 100 * 45, Gdx.graphics.getHeight() / 8 * 3);
		imgOff2.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgOff2.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				imgOn2 = new Image(On_Off);
				imgOff2 = new Image(Off);
			}
		});

		Label music = new Label("Music:", skin);
		music.setPosition(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 4);
		music.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
		music.setFontScale(2);

		if (game.music) {
			imgOn1 = new Image(On);
			imgOff1 = new Image(Off_Off);
		} else {
			imgOn1 = new Image(On_Off);
			imgOff1 = new Image(Off);
		}
		// imgOn1 = new Image(On);
		imgOn1.setPosition(Gdx.graphics.getWidth() / 10 * 3, Gdx.graphics.getHeight() / 4);
		imgOn1.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgOn1.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				imgOn1 = new Image(On);
				imgOff1 = new Image(Off_Off);
			}
		});

		// imgOff1 = new Image(Off);
		imgOff1.setPosition(Gdx.graphics.getWidth() / 100 * 45, Gdx.graphics.getHeight() / 4);
		imgOff1.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgOff1.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int image) {
				imgOn1 = new Image(On_Off);
				imgOff1 = new Image(Off);
				imgOn1.setVisible(false);
				// TODO nochmal video angucken
				System.out.println("bla");
			}
		});

		stage.addActor(imgBackground);
		stage.addActor(imgOn1);
		stage.addActor(imgOff1);
		stage.addActor(music);
		stage.addActor(sound);
		stage.addActor(imgOn2);
		stage.addActor(imgOff2);
		stage.addActor(name);
		stage.addActor(userName);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}