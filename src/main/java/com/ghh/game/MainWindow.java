package com.ghh.game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindow extends Application {
	private final int			WIDTH	= 400;
	private final int			HEIGHT	= 600;
	private final int			COLUMNS	= 4;

	private AnchorPane			root;
	private Timeline			timeline;

	private Bomb				bomb;
	private List<List<Zombie>>	zombies;

	@Override
	public void start(Stage stage) throws Exception {
		root = FXMLLoader.load(getClass().getResource("GamePanel.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		play();
		stage.show();
	}

	private void initGameContext() {
		zombies = new ArrayList<List<Zombie>>(COLUMNS);
		for (int i = 0; i < COLUMNS; i++) {
			zombies.add(new ArrayList<Zombie>());
		}
	}

	private void play() {
		initGameContext();
		bomb = newBomb();
		root.getChildren().add(bomb);
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(35), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				zombieMove();
				if (!shoot(bomb)) {
					root.getChildren().remove(bomb);
					bomb = newBomb();
					root.getChildren().add(bomb);
				}
			}
		});
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	private void zombieMove() {
		for (int col = 0; col < COLUMNS; col++) {
			List<Zombie> column = zombies.get(col);
			if (column.isEmpty()) {
				addZombie(col, column);
			}
			List<Zombie> _toRemove = new ArrayList<Zombie>();
			boolean addNew = false;
			for (int i = 0; i < column.size(); i++) {
				Zombie z = column.get(i);
				if (z.getTranslateY() > HEIGHT - 100) {
					_toRemove.add(z);
				}
				if (i == column.size() - 1) {
					if (z.getTranslateY() > 0) {
						addNew = true;
					}
				}
				z.setTranslateY(z.getSpeedy() + z.getTranslateY());
			}
			/*
			 * remove zombies
			 */
			{
				column.removeAll(_toRemove);
				root.getChildren().removeAll(_toRemove);
			}
			if (addNew) {
				addZombie(col, column);
			}
		}
	}

	private boolean shoot(Bomb b) {
		if (b == null) {
			return false;
		}
		double x = b.getSpeedx() + b.getTranslateX();
		double y = b.getSpeedy() + b.getTranslateY();
		if (x < 0 - b.getWidth() || x > root.getPrefWidth() || y < 0 - b.getHeight() || y > root.getPrefHeight()) {
			return false;
		} else {
			b.setTranslateX(x);
			b.setTranslateY(y);
			return true;
		}
	}

	private void addZombie(int index, List<Zombie> column) {
		Zombie z = newZombie(index);
		root.getChildren().add(z);
		column.add(z);
	}

	private Bomb newBomb() {
		Bomb bomb = new Bomb();
		bomb.setTranslateX((WIDTH - bomb.getWidth()) / 2);
		bomb.setTranslateY(HEIGHT - bomb.getHeight());
		bomb.setSpeedx((Math.random() - 0.5f) * 6 + 1);
		bomb.setSpeedy((Math.random() * 3 + 1) * -1);
		return bomb;
	}

	private Zombie newZombie(int col) {
		Zombie z = new Zombie();
		int _col_width = (int) (WIDTH / COLUMNS);
		int x = _col_width * col + (_col_width - z.getWidth()) / 2;
		z.setTranslateX(x);
		z.setTranslateY(z.getHeight() * -1);
		return z;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
