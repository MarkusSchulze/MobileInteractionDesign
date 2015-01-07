package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		this.setScreen(new MainScreen(this));
	}

}
