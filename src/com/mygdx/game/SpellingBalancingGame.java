package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SpellingBalancingGame extends Game {
	public final static String file = ".SpellingGame";
	public static boolean soundEnabled = true;
	public final static int[] highscores = new int[] { 100, 80, 50, 30, 10 };

	@SuppressWarnings("unused")
	public boolean music;
	public boolean sound;

	@Override
	public void create() {
		music = true;
		sound = false;
		try {
			FileHandle filehandle = Gdx.files.external(file);

			String[] strings = filehandle.readString().split("\n");

			soundEnabled = Boolean.parseBoolean(strings[0]);
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i + 1]);
			}
		} catch (Throwable e) {
			// :( It's ok we have defaults
		}
		setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

}
