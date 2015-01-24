package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.*;
import java.util.ArrayList;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
        Data.Init();
        Data.LoadUsers();
        this.setScreen(new MainScreen(this));
	}
}
