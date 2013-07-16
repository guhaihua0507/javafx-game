package com.ghh.game.wp;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import com.ghh.game.Weapon;

public class Shuriken extends Weapon {
	private ImageView		imageView;
	private Timeline		animation;
	private FadeTransition	fadeTransition;

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
		animation = new Timeline();
		animation.setAutoReverse(false);
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				double rotate = (imageView.getRotate() + 10) % 360;
				imageView.setRotate(rotate);
			}
		}));
		animation.play();
	}

	@Override
	public void destroy(EventHandler<ActionEvent> eh) {
		if (animation != null) {
			animation.stop();
		}
		fadeTransition = FadeTransitionBuilder.create()
		.duration(Duration.millis(150))
		.node(this)
		.fromValue(1)
		.toValue(0.2)
		.cycleCount(3)
		.onFinished(eh)
		.autoReverse(true)
		.build();
		
		fadeTransition.play();
	}
}
