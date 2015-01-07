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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends ScreenAdapter {
	private Game game;
	private TextField letter;
	private int numberOfLetters;
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shaperenderer;

	private Sphere bucket;
	private Sphere circle;
	private Sphere circleCollision;
	private Vector3 rotation;
	private Vector3 touchPos;
	Sphere spawningOuterSphere;
	Sphere spawningInterSphere;
	private Array<Sphere> raindrops;

	public GameScreen(Game g) {
		game = g;
		Vector3 sphereCenter;
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		letter = new TextField("test", skin);
		letter.setPosition(100, 100);
		numberOfLetters = 5;

		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// Camera initiation
		camera = new OrthographicCamera();

		System.out.println("Höhe: " + Gdx.graphics.getHeight());
		System.out.println("Breite: " + Gdx.graphics.getWidth());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// Rechteck um den Eimer. Wird in der Mitte vom Screen erstellt mit 20
		// Pixeln überhalb vom Boden
		bucket = new Sphere(new Vector3(Gdx.graphics.getWidth() / 2 - 64 / 2, Gdx.graphics.getHeight() / 2 - 64, 0), 32);

		// Kreis auf dem die Kugel ballanciert wird. Es gibt einen Kreis der
		// angezeigt wird und einen weiteren der bestimmt, wann die Kugel
		// "runterfällt"
		sphereCenter = new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

		circleCollision = new Sphere(sphereCenter, Gdx.graphics.getHeight() / 2);
		circle = new Sphere(sphereCenter, Gdx.graphics.getHeight() / 2 - bucket.radius);

		raindrops = new Array<Sphere>();
		spawnLetters(sphereCenter);

	}

	// Funktion zum erstellen von random Tropfen in ein Array
	private void spawnLetters(Vector3 sphereCenter) {
		float x = 0;
		float y = 0;
		boolean collision = false;

		spawningOuterSphere = new Sphere(sphereCenter, Gdx.graphics.getHeight() / 2 - bucket.radius - 2 * 32);
		spawningInterSphere = new Sphere(sphereCenter, 2 * bucket.radius + 32);

		Iterator<Sphere> iter;
		for (int i = 0; i < numberOfLetters; i++) {
			x = MathUtils.random(Gdx.graphics.getWidth() / 2 - (Gdx.graphics.getHeight() / 2 - bucket.radius) + 32,
					Gdx.graphics.getWidth() / 2 + (Gdx.graphics.getHeight() / 2 - bucket.radius) - 32);
			y = MathUtils.random(bucket.radius, Gdx.graphics.getHeight() - bucket.radius);
			Sphere raindrop = new Sphere(new Vector3(x, y, 0), 52);

			collision = false;
			iter = raindrops.iterator();
			while (iter.hasNext()) {
				Sphere testDummy = iter.next();
				if (testDummy.overlaps(raindrop)) {
					collision = true;
				}
			}
			if (raindrop.overlaps(spawningOuterSphere) && !raindrop.overlaps(spawningInterSphere) && collision == false) {
				raindrop.radius = 32;
				raindrops.add(raindrop);
			} else {
				i--;
			}

		}
	}

	public void render(float delta) {
		// Grundfarbe
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Camera aktualisieren (Sollte jeden Frame gemacht werden)
		camera.update();

		// Renderer für das Board
		shaperenderer = new ShapeRenderer();
		shaperenderer.setProjectionMatrix(camera.combined);
		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(0, 1, 0, 1);
		shaperenderer.circle(circle.center.x, circle.center.y, circle.radius);
		// //DEBUG
		// shaperenderer.setColor(0, 1, 1, 1);
		// shaperenderer.circle(spawningOuterSphere.center.x,
		// spawningOuterSphere.center.y, spawningOuterSphere.radius);
		// shaperenderer.setColor(1, 1, 0, 1);
		// shaperenderer.circle(spawningInterSphere.center.x,
		// spawningInterSphere.center.y, spawningInterSphere.radius);
		// shaperenderer.setColor(1, 1, 0, 1);
		// shaperenderer.circle(bucket.center.x, bucket.center.y,
		// bucket.radius);

		shaperenderer.end();

		if (!circleCollision.overlaps(bucket))
			System.out.println("fail!!!!");

		// // auf Touch reagieren
		// if (Gdx.input.isTouched()) {
		// touchPos = new Vector3();
		//
		// touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		// camera.unproject(touchPos);
		// bucket.center.x = touchPos.x - 64 / 2;
		// bucket.center.y = 80;
		// }

		// Bewegungssensor
		rotation = new Vector3();
		if (Gdx.input.getRoll() > 0) {
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
		// // nach fester Zeit neuen Tropfen spawnen
		// if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
		// spawnRaindrop();

		// Tropfen Kollision
		Iterator<Sphere> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Sphere raindrop = iter.next();
			if (raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
			// DEBUG
			// shaperenderer.setProjectionMatrix(camera.combined);
			// shaperenderer.begin(ShapeType.Filled);
			// shaperenderer.setColor(1, 1, 0, 1);
			// shaperenderer.circle(raindrop.center.x, raindrop.center.y,
			// raindrop.radius);
			// shaperenderer.end();
		}

		// alles zeichnen mit der batch für openGL
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.center.x - bucket.radius, bucket.center.y - bucket.radius);
		for (Sphere raindrop : raindrops) {
			batch.draw(dropImage, raindrop.center.x - raindrop.radius, raindrop.center.y - raindrop.radius);
		}
		batch.end();
	}
}
