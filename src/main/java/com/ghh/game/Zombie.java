package com.ghh.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Zombie extends Parent {
	private int			life	= 80;
	private final int	width	= 80;
	private final int	height	= 80;
	private ImageView	imageView;
	private Image		image1	= new Image(Bomb.class.getResourceAsStream("/zombie.png"));
	private Image		image2	= new Image(Bomb.class.getResourceAsStream("/zombie2.png"));
	private double		speedy	= 0.2f;

	public Zombie() {
		imageView = new ImageView();
		imageView.setImage(image1);
		// imageView.setViewport(new Rectangle2D(0, 0, width, height));
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		getChildren().add(imageView);
		march();
	}

	private void march() {
		// TimelineBuilder
		// .create()
		// .cycleCount(Timeline.INDEFINITE)
		// .keyFrames(
		// new KeyFrame(Duration.ZERO,
		// new KeyValue(imageView.viewportProperty(), new Rectangle2D(0, 0,
		// width, height))),
		// new KeyFrame(Duration.millis(500),
		// new KeyValue(imageView.viewportProperty(), new Rectangle2D(width, 0,
		// width, height))),
		// new KeyFrame(Duration.millis(1000),
		// new KeyValue(imageView.viewportProperty(), new Rectangle2D(width * 2,
		// 0, width, height))))
		// .build()
		// .play();
		TimelineBuilder.create().cycleCount(Timeline.INDEFINITE).autoReverse(true)
				.keyFrames(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent ae) {
						if (imageView.getImage() == image1) {
							imageView.setImage(image2);
						} else {
							imageView.setImage(image1);
						}
					}
				})).build().play();
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public double getSpeedy() {
		return speedy;
	}

	public void setSpeedy(double speedy) {
		this.speedy = speedy;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
