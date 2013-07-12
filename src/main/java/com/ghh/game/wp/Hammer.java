package com.ghh.game.wp;

import com.ghh.game.Weapon;

import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Hammer extends Weapon {
	private ImageView	imageView;
	
	public Hammer() {
		super(20, 64, 64);
		init();
	}

	private void init() {
		imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/hammer.png")));
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
			.duration(Duration.millis(1000))
			.build()
			.play();
	}

}
