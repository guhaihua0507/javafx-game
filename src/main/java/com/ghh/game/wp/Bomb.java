package com.ghh.game.wp;

import javafx.animation.FadeTransitionBuilder;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import com.ghh.game.Weapon;

public class Bomb extends Weapon {
	private ImageView	imageView;
	private Transition transition;

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
		transition = ScaleTransitionBuilder.create()
			.node(imageView)
			.duration(Duration.millis(200))
			.autoReverse(true)
			.cycleCount(Timeline.INDEFINITE)
			.toX(0.8f)
			.toY(0.8f)
			.build();
			
		transition.play();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void destroy(EventHandler<ActionEvent> eh) {
		if (transition != null) {
			transition.stop();
		}
		transition = FadeTransitionBuilder.create()
		.duration(Duration.millis(150))
		.node(this)
		.fromValue(1)
		.toValue(0.2)
		.cycleCount(3)
		.onFinished(eh)
		.autoReverse(true)
		.build();
		
		transition.play();
	}

}
