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
	private Image imgLineOneOn_On;
	private Image imgLineOneOn_Off;
	private Image imgLineOneOff_On;
	private Image imgLineOneOff_Off;
	private Image imgLineTwoOn_On;
	private Image imgLineTwoOn_Off;
	private Image imgLineTwoOff_On;
	private Image imgLineTwoOff_Off;
	private Image imgExit;
	private TextField userName;

	public OptionScreen(SpellingBalancingGame g) {
		game = g;
		int lineOne = Gdx.graphics.getHeight() / 2;
		int lineTwo = Gdx.graphics.getHeight() / 8 * 3;
		int lineThree = Gdx.graphics.getHeight() / 4;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		Texture On = new Texture(Gdx.files.internal("On.png"));
		Texture Off = new Texture(Gdx.files.internal("Off.png"));
		Texture On_Off = new Texture(Gdx.files.internal("On_Off.png"));
		Texture Off_Off = new Texture(Gdx.files.internal("Off_Off.png"));
		imgLineOneOn_On = new Image(On);
		imgLineOneOn_Off = new Image(On_Off);
		imgLineOneOff_On = new Image(Off);
		imgLineOneOff_Off = new Image(Off_Off);
		imgLineTwoOn_On = new Image(On);
		imgLineTwoOn_Off = new Image(On_Off);
		imgLineTwoOff_On = new Image(Off);
		imgLineTwoOff_Off = new Image(Off_Off);

		Texture texture = new Texture(Gdx.files.internal("StartScreen.jpg"));
		Image imgBackground = new Image(texture);
		imgBackground.setPosition(0, 0);
		imgBackground.setAlign(Align.bottomRight);
		imgBackground.setHeight(Gdx.graphics.getHeight());
		imgBackground.setWidth(Gdx.graphics.getWidth());

		Texture exit = new Texture(Gdx.files.internal("Exit.png"));
		imgExit = new Image(exit);
		imgExit.setPosition(Gdx.graphics.getWidth() / 8 * 7, Gdx.graphics.getHeight() / 8 * 7);
		imgExit.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgExit.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				SpellingBalancingGame.playerName = userName.getText();
				SpellingBalancingGame.save();
				game.setScreen(new MainScreen(game));
			}
		});

		Label name = new Label("Name:", skin);
		name.setPosition(Gdx.graphics.getWidth() / 10, lineOne);
		name.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
		name.setFontScale(2);

		userName = new TextField(SpellingBalancingGame.playerName, skin);
		userName.setPosition(Gdx.graphics.getWidth() / 4 + 10, lineOne);
		userName.setSize(Gdx.graphics.getWidth() / 4 + 10, Gdx.graphics.getHeight() / 10);

		Label sound = new Label("Sound:", skin);
		sound.setPosition(Gdx.graphics.getWidth() / 10, lineTwo);
		sound.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
		sound.setFontScale(2);

		if (SpellingBalancingGame.sound) {
			imgLineOneOff_On.setVisible(false);
		} else {
			imgLineOneOn_On.setVisible(false);
		}
		imgLineOneOn_On.setPosition(Gdx.graphics.getWidth() / 10 * 3, lineTwo);
		imgLineOneOn_On.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

		imgLineOneOn_Off.setPosition(Gdx.graphics.getWidth() / 10 * 3, lineTwo);
		imgLineOneOn_Off.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgLineOneOn_Off.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				SpellingBalancingGame.sound = true;
				imgLineOneOff_On.setVisible(false);
				imgLineOneOn_On.setVisible(true);
			}
		});

		imgLineOneOff_On.setPosition(Gdx.graphics.getWidth() / 100 * 45, lineTwo);
		imgLineOneOff_On.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

		imgLineOneOff_Off.setPosition(Gdx.graphics.getWidth() / 100 * 45, lineTwo);
		imgLineOneOff_Off.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgLineOneOff_Off.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				SpellingBalancingGame.sound = false;
				imgLineOneOn_On.setVisible(false);
				imgLineOneOff_On.setVisible(true);
			}
		});

		Label music = new Label("Music:", skin);
		music.setPosition(Gdx.graphics.getWidth() / 10, lineThree);
		music.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
		music.setFontScale(2);

		if (SpellingBalancingGame.music) {
			imgLineTwoOn_Off.setVisible(false);
			imgLineTwoOff_On.setVisible(false);
		} else {
			imgLineTwoOn_On.setVisible(false);
			imgLineTwoOff_Off.setVisible(false);
		}
		imgLineTwoOn_On.setPosition(Gdx.graphics.getWidth() / 10 * 3, lineThree);
		imgLineTwoOn_On.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

		imgLineTwoOn_Off.setPosition(Gdx.graphics.getWidth() / 10 * 3, lineThree);
		imgLineTwoOn_Off.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgLineTwoOn_Off.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				SpellingBalancingGame.music = true;
				imgLineTwoOn_Off.setVisible(false);
				imgLineTwoOff_On.setVisible(false);
				imgLineTwoOn_On.setVisible(true);
				imgLineTwoOff_Off.setVisible(true);
			}
		});

		imgLineTwoOff_On.setPosition(Gdx.graphics.getWidth() / 100 * 45, lineThree);
		imgLineTwoOff_On.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

		imgLineTwoOff_Off.setPosition(Gdx.graphics.getWidth() / 100 * 45, lineThree);
		imgLineTwoOff_Off.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		imgLineTwoOff_Off.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				SpellingBalancingGame.music = false;
				imgLineTwoOn_On.setVisible(false);
				imgLineTwoOff_Off.setVisible(false);
				imgLineTwoOn_Off.setVisible(true);
				imgLineTwoOff_On.setVisible(true);
			}
		});

		// _off muessen als letztes eingefügt werden, für hohe Priorität beim
		// zeichnen
		stage.addActor(imgBackground);
		stage.addActor(imgExit);
		stage.addActor(imgLineOneOn_Off);
		stage.addActor(imgLineOneOff_Off);
		stage.addActor(imgLineOneOn_On);
		stage.addActor(imgLineOneOff_On);
		stage.addActor(music);
		stage.addActor(sound);
		stage.addActor(imgLineTwoOn_Off);
		stage.addActor(imgLineTwoOff_Off);
		stage.addActor(imgLineTwoOn_On);
		stage.addActor(imgLineTwoOff_On);
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