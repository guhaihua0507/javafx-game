package com.ghh.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Zombie extends Parent {
	private final int	width	= 33;
	private final int	height	= 33;
	private ImageView	imageView;

	private double		speedy	= 1.0f;
	private int			mIndex	= 0;

	public Zombie() {
		imageView = new ImageView();
		imageView.setImage(new Image(Bomb.class.getResourceAsStream("/c.png")));
		imageView.setViewport(new Rectangle2D(0, 0, width, height));
		getChildren().add(imageView);
	}

	public void updateView() {
		mIndex++;
		mIndex = mIndex % 9;
		imageView.setViewport(new Rectangle2D(width * (mIndex / 3), 0, width, height));
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
