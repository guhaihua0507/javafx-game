package com.ghh.game;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Zombie extends Parent {
	private final int	width	= 90;
	private final int	height	= 90;
	private ImageView	imageView;

	private double		speedy	= 2.0f;

	public Zombie() {
		imageView = new ImageView();
		imageView.setImage(new Image(Bomb.class.getResourceAsStream("/Zombie-icon.png")));
		imageView.setFitHeight(width);
		imageView.setFitWidth(height);
		getChildren().add(imageView);
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
