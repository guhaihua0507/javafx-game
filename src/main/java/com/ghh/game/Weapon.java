package com.ghh.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public abstract class Weapon extends Parent {
	public static enum Status {
		ready, dragging, moving, destroying, removed
	}

	protected int		width;
	protected int		height;
	protected double	speedX	= 0.0f;
	protected double	speedY	= 0.0f;
	protected int		power	= 10;

	protected Status	status	= Status.ready;
	protected Point2D	_dragAnchor;
	protected double	_startX;
	protected double	_startY;
	protected long		_lastUpdateNanoTime;

	private long		_startTime;

	public Weapon(int power, int width, int height) {
		this.power = power;
		this.width = width;
		this.height = height;
		setCursor(Cursor.HAND);
	}

	protected void initEvent() {
		final Parent o = this;
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (status != Status.ready) {
					return;
				}
				_startX = o.getTranslateX();
				_startY = o.getTranslateY();
				_dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				_startTime = System.currentTimeMillis();
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (status != Status.ready && status != Status.dragging) {
					return;
				}
				status = Status.dragging;
				double offsetX = me.getSceneX() - _dragAnchor.getX();
				double offsetY = me.getSceneY() - _dragAnchor.getY();
				o.setTranslateX(_startX + offsetX);
				o.setTranslateY(_startY + offsetY);
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (status != Status.dragging) {
					return;
				}
				status = Status.moving;
				long _time = System.currentTimeMillis() - _startTime;
				double offsetX = o.getTranslateX() - _startX;
				double offsetY = o.getTranslateY() - _startY;
				calcSpeed(offsetX, offsetY, _time);
				_lastUpdateNanoTime = System.nanoTime();
				fire();
			}
		});
	}

	protected void calcSpeed(double offsetX, double offsetY, long period) {
		// double distance = Math.hypot(offsetX, offsetY);
		speedX = offsetX / period;
		speedY = offsetY / period;
	}

	public Status getStatus() {
		return status;
	}

	protected abstract void fire();
	
	public abstract void destroy(EventHandler<ActionEvent> eh);

	public void update(long now) {
		if (status != Status.moving) {
			return;
		}
		double deltaTime = (now - _lastUpdateNanoTime) / 1800000;
		double x = speedX * deltaTime + getTranslateX();
		double y = speedY * deltaTime + getTranslateY();
		setTranslateX(x);
		setTranslateY(y);
		_lastUpdateNanoTime = now;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
