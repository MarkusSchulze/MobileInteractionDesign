package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HighscoreScreen extends ScreenAdapter {
	private Game game;
	private Stage stage;
	private Image imgLost;


	public HighscoreScreen(Game g) {
		game = g;
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Texture texture = new Texture(Gdx.files.internal("StartScreen.jpg"));
        Image imgBackground = new Image(texture);
        imgBackground.setPosition(0, 0);
        imgBackground.setAlign(Align.bottomRight);
        imgBackground.setHeight(Gdx.graphics.getHeight());
        imgBackground.setWidth(Gdx.graphics.getWidth());


		stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        float ScreenHeight = Gdx.graphics.getHeight();
        float ScreenWidth = Gdx.graphics.getWidth();
        stage.addActor(imgBackground);

        for (int i=0;i<5 && i<Data.UsersDatas.size();i++)
        {
            Label name = new Label(Data.UsersDatas.get(i).getName(), skin);
            name.setPosition(Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight()*(5-i) / 6);
            name.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
            name.setFontScale(2);

            Integer score=Data.UsersDatas.get(i).getWordlist().size();
            Label scoretext = new Label(score.toString(), skin);
            scoretext.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()*(5-i) / 6);
            scoretext.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
            scoretext.setFontScale(2);

            stage.addActor(scoretext);
            stage.addActor(name);
        }

        TextButton btnStart = new TextButton("return", skin);
        btnStart.setPosition(ScreenWidth* 2/ 3, ScreenHeight / 10);
        btnStart.setSize(ScreenWidth / 4, ScreenHeight / 10);
        btnStart.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.setScreen(new MainScreen(game));
            }
        });
        stage.addActor(btnStart);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}
}
