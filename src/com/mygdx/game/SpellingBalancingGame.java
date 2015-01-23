package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SpellingBalancingGame extends Game {
	public final static String file = ".SpellingGame";
	public final static int[] highscores = new int[] { 100, 80, 50, 30, 10 };

	public static boolean music;
	public static boolean sound;
	public static String playerName;

	@Override
	public void create() {
		music = true;
		sound = true;
		playerName = "Player1";

		try {
			FileHandle filehandle = Gdx.files.external(file);

			String[] strings = filehandle.readString().split("\n");

			sound = Boolean.parseBoolean(strings[0]);
			music = Boolean.parseBoolean(strings[1]);
			playerName = strings[2];
			// for (int i = 3; i < 5; i++) {
			// highscores[i] = Integer.parseInt(strings[i + 1]);
			// }
		} catch (Throwable e) {
			// :( It's ok we have defaults
		}
		setScreen(new MainScreen(this));
	}

	public static void save() {
		try {
			FileHandle filehandle = Gdx.files.external(file);

			filehandle.writeString(Boolean.toString(sound) + "\n", false);
			filehandle.writeString(Boolean.toString(music) + "\n", false);
			filehandle.writeString(playerName + "\n", false);
			// for (int i = 3; i < 5; i++) {
			// filehandle.writeString(Integer.toString(highscores[i]) + "\n",
			// true);
			// }
		} catch (Throwable e) {
		}
	}

	// public static void addScore(int score) {
	// for (int i = 0; i < 5; i++) {
	// if (highscores[i] < score) {
	// for (int j = 4; j > i; j--)
	// highscores[j] = highscores[j - 1];
	// highscores[i] = score;
	// break;
	// }
	// }
	// }

	@Override
	public void render() {
		super.render();
	}

}
