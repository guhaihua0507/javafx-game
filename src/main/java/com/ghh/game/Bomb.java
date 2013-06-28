package com.ghh.game;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bomb extends Parent {
	private final int	width	= 76;
	private final int	height	= 76;

	private double		speedx	= 0.0f;
	private double		speedy	= 0.0f;

	private ImageView	imageView;

	public Bomb() {
		imageView = new ImageView();
		imageView.setImage(new Image(Bomb.class.getResourceAsStream("/Bomb-Cool-icon.png")));
		getChildren().add(imageView);
	}

	public double getSpeedx() {
		return speedx;
	}

	public void setSpeedx(double speedx) {
		this.speedx = speedx;
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
