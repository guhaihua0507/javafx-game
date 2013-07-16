package com.ghh.game.wp;

import javafx.animation.FadeTransitionBuilder;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import com.ghh.game.Weapon;

public class Hammer extends Weapon {
	private ImageView		imageView;
	private Transition		transition;
	
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
		transition = RotateTransitionBuilder.create()
			.node(imageView)
			.autoReverse(false)
			.cycleCount(Timeline.INDEFINITE)
			.fromAngle(0)
			.toAngle(360)
			.duration(Duration.millis(1000))
			.build();
		transition.play();
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
