package com.ghh.game;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Bomb extends Parent {
	private final int	width		= 76;
	private final int	height		= 76;

	private double		speedx		= 0.0f;
	private double		speedy		= 0.0f;

	private ImageView	imageView;

	private double		startX;
	private double		startY;
	private boolean		dragged		= false;
	private boolean		dragging	= false;
	private boolean		moving		= false;
	private Point2D		dragAnchor;
	private long		startTime;
	private long		updateTime;

	public Bomb() {
		imageView = new ImageView();
		imageView.setImage(new Image(Bomb.class.getResourceAsStream("/Bomb-Cool-icon.png")));
		getChildren().add(imageView);
		initEvent();
	}

	private void initEvent() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (dragged || moving) {
					return;
				}
				// when mouse is pressed, store initial position
				startX = Bomb.this.getTranslateX();
				startY = Bomb.this.getTranslateY();
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				startTime = System.currentTimeMillis();
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (dragged || moving) {
					return;
				}
				dragging = true;
				double offsetX = me.getSceneX() - dragAnchor.getX();
				double offsetY = me.getSceneY() - dragAnchor.getY();
				Bomb.this.setTranslateX(startX + offsetX);
				Bomb.this.setTranslateY(startY + offsetY);
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!dragging || dragged || moving) {
					return;
				}
				dragged = true;
				moving = true;
				long _time = System.currentTimeMillis() - startTime;
				double offsetX = Bomb.this.getTranslateX() - startX;
				double offsetY = Bomb.this.getTranslateY() - startY;
				speedx = offsetX / _time;
				speedy = offsetY / _time;
				updateTime = System.currentTimeMillis();
			}
		});
	}

	public void update() {
		if (!moving) {
			return;
		}
		long deltaTime = (System.currentTimeMillis() - updateTime) / 5;
		double x = speedx * deltaTime + getTranslateX();
		double y = speedy * deltaTime + getTranslateY();
		setTranslateX(x);
		setTranslateY(y);
		updateTime = System.currentTimeMillis();
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
