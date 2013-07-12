package com.ghh.game.wp;

import com.ghh.game.Weapon;

import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Bomb extends Weapon {
	private ImageView	imageView;

	public Bomb() {
		super(100, 72, 72);
		imageView = new ImageView();
		imageView.setImage(new Image(Bomb.class.getResourceAsStream("/bomb.png")));
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		getChildren().add(imageView);
		super.initEvent();
	}

	@Override
	protected void fire() {
		ScaleTransitionBuilder.create()
			.node(imageView)
			.duration(Duration.millis(200))
			.autoReverse(true)
			.cycleCount(Timeline.INDEFINITE)
			.toX(0.8f)
			.toY(0.8f)
			.build()
			.play();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
