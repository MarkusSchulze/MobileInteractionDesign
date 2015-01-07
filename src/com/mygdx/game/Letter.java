package com.mygdx.game;

import com.badlogic.gdx.math.collision.Sphere;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Letter {
	private Label label;
	private Sphere sphere;

	public Letter(Sphere s, Label l) {
		label = l;
		sphere = s;
	}

	public Sphere getSphere() {
		return sphere;
	}

	public Label getLabel() {
		return label;
	}
}
