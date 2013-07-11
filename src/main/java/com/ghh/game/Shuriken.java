package com.ghh.game;

import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Shuriken extends Weapon {
	private ImageView	imageView;
	
	public Shuriken() {
		super(30, 32, 32);
		init();
	}

	private void init() {
		imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/shuriken.png")));
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		getChildren().add(imageView);
		super.initEvent();
	}
	
	@Override
	protected void fire() {
		RotateTransitionBuilder.create()
			.node(imageView)
			.autoReverse(false)
			.cycleCount(Timeline.INDEFINITE)
			.fromAngle(0)
			.toAngle(360)
			.duration(Duration.millis(500))
			.build().play();
	}
}
