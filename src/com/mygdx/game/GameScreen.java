package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends ScreenAdapter {
	private Game game;
	private Label letterCounter;
	private Label timer;
	private int numberOfLetters;
	private String theWORD;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private long startTime;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shaperenderer;
	private Stage stage;

	private Sphere bucket;
	private Sphere circle;
	private Sphere circleCollision;
	private Vector3 rotation;
	private Array<Letter> letters;

	// Features
	// TODO Better movement for the Ball
	// TODO A ball image, NO bucket... maybe with animation
	// TODO Highscore
	// TODO Option menu
	// OPTIONAL
	// different ball movement for different undergrounds (ice, street, sticky)
	// Multilanguage
	// Input for Usernames
	// Multiple levels with more Letters

	public GameScreen(Game g, String choosenWord) {
		theWORD = choosenWord;
		game = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		shaperenderer = new ShapeRenderer();
		startTime = TimeUtils.millis();

		Vector3 sphereCenter;
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		letterCounter = new Label("", skin);
		letterCounter.setPosition(0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8);
		letterCounter.setSize(Gdx.graphics.getWidth() / 10 * 3, Gdx.graphics.getHeight() / 8);
		letterCounter.setFontScale(3);
		stage.addActor(letterCounter);

		timer = new Label("60", skin);
		timer.setPosition(Gdx.graphics.getWidth() / 10 * 9, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8);
		timer.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
		timer.setFontScale(3);
		stage.addActor(timer);

		System.out.println(choosenWord);
		numberOfLetters = choosenWord.length();

		// load the images for the bucket, 64x64 pixels
		bucketImage = new Texture(Gdx.files.internal("sphere.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("flip_the_flop.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// Camera initiation
		camera = new OrthographicCamera();

		System.out.println("Hoehe: " + Gdx.graphics.getHeight());
		System.out.println("Breite: " + Gdx.graphics.getWidth());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// Rechteck um den Eimer. Wird in der Mitte vom Screen erstellt mit 20
		// Pixeln oberhalb vom Boden
		bucket = new Sphere(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0), 32);

		// Kreis auf dem die Kugel ballanciert wird. Es gibt einen Kreis der
		// angezeigt wird und einen weiteren der bestimmt, wann die Kugel
		// "runterfaellt"
		sphereCenter = new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

		circleCollision = new Sphere(sphereCenter, Gdx.graphics.getHeight() / 2);
		circle = new Sphere(sphereCenter, Gdx.graphics.getHeight() / 2 - bucket.radius);

		spawnLetters(sphereCenter, choosenWord, skin);
	}

	// Funktion zum erstellen von random Tropfen in ein Array
	private void spawnLetters(Vector3 sphereCenter, String choosenWord, Skin skin) {
		float x = 0;
		float y = 0;
		float sphereRadius = 32;
		boolean collision = false;
		Sphere spawningOuterSphere = new Sphere(sphereCenter, Gdx.graphics.getHeight() / 2 - bucket.radius - 2 * sphereRadius);
		System.out.println(2 * bucket.radius + sphereRadius);
		Sphere spawningInterSphere = new Sphere(sphereCenter, 2 * bucket.radius);
		// raindrops = new Array<Sphere>();
		letters = new Array<Letter>();
		Iterator<Letter> iter;
		choosenWord += "x";

		for (int i = 0; i < numberOfLetters; i++) {
			// bestimme die Position zufaellig
			// x goes from
			// (ScreenWidth / 2) - radius from the plattform + radius from the
			// character
			// to (ScreenWidth / 2) + radius from the plattform - radius from
			// the character
			x = MathUtils.random(Gdx.graphics.getWidth() / 2 - (Gdx.graphics.getHeight() / 2 - bucket.radius) + sphereRadius, Gdx.graphics.getWidth() / 2
					+ (Gdx.graphics.getHeight() / 2 - bucket.radius) - sphereRadius);
			// y goes from
			// radius from the bucket + radius from the character
			// to ScreenHeight - radius from the bucket - radius from the
			// character
			y = MathUtils.random(bucket.radius + sphereRadius, Gdx.graphics.getHeight() - bucket.radius - sphereRadius);
			Sphere newLetter = new Sphere(new Vector3(x, y, 0), 52);

			// prüfe, ob die Position mit den anderen Buchstaben oder dem
			// inneren oder äußerem Kreis kollidiert
			collision = false;
			iter = letters.iterator();
			while (iter.hasNext()) {
				Letter testDummy = iter.next();
				if (testDummy.getSphere().overlaps(newLetter)) {
					collision = true;
				}
			}
			if (newLetter.overlaps(spawningOuterSphere) && !newLetter.overlaps(spawningInterSphere) && collision == false) {
				newLetter.radius = sphereRadius;
				Label newLabel = new Label(choosenWord.substring(0, 1), skin);
				choosenWord = choosenWord.substring(1, choosenWord.length());
				newLabel.setPosition(x - newLetter.radius / 2, y - newLetter.radius);
				newLabel.setSize(newLetter.radius * 2, newLetter.radius * 2);
				newLabel.setFontScale(3);

				Letter insertLetter = new Letter(newLetter, newLabel);
				letters.add(insertLetter);
				stage.addActor(insertLetter.getLabel());
			} else {
				i--;
			}

		}
	}

	public void update() {
		String temp1;
		String temp2;
		stage.act();
		// Camera aktualisieren (Sollte jeden Frame gemacht werden)
		camera.update();
		Integer intTimer = 60 - (int) TimeUtils.timeSinceMillis(startTime) / 1000;

		if (!circleCollision.overlaps(bucket)) {
			System.out.println("fail!!!!");
			rainMusic.stop();
			game.setScreen(new LostScreen(game));
		}

		// Bewegungssensor TODO kann man noch cooler machen FOR LI
		rotation = new Vector3();
		int tolerance = 10; // <- probably needs to be tuned
		if (Gdx.input.getRoll() > tolerance) {
			rotation.set(bucket.center.x, bucket.center.y++, 0);

			camera.unproject(rotation);
			bucket.center.y++;
		} else {
			rotation.set(bucket.center.x, bucket.center.y--, 0);
			camera.unproject(rotation);
			bucket.center.y--;
		}
		if (Gdx.input.getPitch() < 0) {
			rotation.set(bucket.center.x++, bucket.center.y, 0);
			camera.unproject(rotation);
			bucket.center.x++;
		} else {
			rotation.set(bucket.center.x--, bucket.center.y, 0);
			camera.unproject(rotation);
			bucket.center.x--;
		}

		// Tropfen Kollision
		Iterator<Letter> iter = letters.iterator();
		while (iter.hasNext()) {
			Letter collisionLetter = iter.next();
			if (collisionLetter.getSphere().overlaps(bucket)) {
				dropSound.play();
				collisionLetter.getLabel().setVisible(false);
				letterCounter.setText(letterCounter.getText().toString() + collisionLetter.getLabel().getText());
				iter.remove();
				// Vergleiche aktuelles Wort mit dem gesuchten Wort
				temp1 = letterCounter.getText().toString();
				temp2 = theWORD.substring(0, letterCounter.getText().length);
				// wenn das Wort flasch buchstabiert wurde, gehe zum LostScreen
				if (temp1.equals(temp2) == false) {
					rainMusic.stop();
					game.setScreen(new LostScreen(game));
					// wenn das Wort gleich lang und nicht falsch ist, dann hat
					// man gewonnen
				} else if (letterCounter.getText().length == theWORD.length()) {
					rainMusic.stop();
					game.setScreen(new WinScreen(game));
				}
			}
		}

		// Timer countdown
		timer.setText(intTimer.toString());
		if (timer.getText().toString().equals("0")) {
			game.setScreen(new LostScreen(game));
		}
	}

	public void draw() {
		// Grundfarbe
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Renderer fuer das Board
		shaperenderer.setProjectionMatrix(camera.combined);
		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(0, 1, 0, 1);
		shaperenderer.circle(circle.center.x, circle.center.y, circle.radius);
		// // DEBUG drawing spheres
		// Vector3 sphereCenter = new Vector3(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, 0);
		// Sphere spawningOuterSphere = new Sphere(sphereCenter,
		// Gdx.graphics.getHeight() / 2 - bucket.radius - 2 *
		// letters.get(0).getSphere().radius);
		// Sphere spawningInterSphere = new Sphere(sphereCenter, 2 *
		// bucket.radius);
		// shaperenderer.setColor(0, 1, 1, 1);
		// shaperenderer.circle(spawningOuterSphere.center.x,
		// spawningOuterSphere.center.y, spawningOuterSphere.radius);
		// shaperenderer.setColor(1, 1, 0, 1);
		// shaperenderer.circle(spawningInterSphere.center.x,
		// spawningInterSphere.center.y, spawningInterSphere.radius);
		// shaperenderer.setColor(1, 1, 0, 1);
		// shaperenderer.circle(bucket.center.x, bucket.center.y,
		// bucket.radius);
		//
		// Iterator<Letter> iter = letters.iterator();
		// while (iter.hasNext()) {
		// Letter collisionLetter = iter.next();
		// shaperenderer.setColor(1, 1, 0, 1);
		// shaperenderer.circle(collisionLetter.getSphere().center.x,
		// collisionLetter.getSphere().center.y,
		// collisionLetter.getSphere().radius);
		// }
		shaperenderer.end();

		// batch für die Images. könnte wahrscheinlich auch in der stage gemacht
		// werden, müsste man testen
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.center.x - bucket.radius, bucket.center.y - bucket.radius);
		// for (Sphere raindrop : raindrops) {
		// batch.draw(dropImage, raindrop.center.x - raindrop.radius,
		// raindrop.center.y - raindrop.radius);
		// }
		batch.end();
		stage.draw();
	}

	public void render(float delta) {
		update();
		draw();

		// // auf Touch reagieren
		// if (Gdx.input.isTouched()) {
		// touchPos = new Vector3();
		//
		// touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		// camera.unproject(touchPos);
		// bucket.center.x = touchPos.x - 64 / 2;
		// bucket.center.y = 80;
		// }
	}
}
