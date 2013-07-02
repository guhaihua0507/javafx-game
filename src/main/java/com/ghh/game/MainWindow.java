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
	private final int		WIDTH	= 400;
	private final int		HEIGHT	= 600;
	private AnchorPane		root;
	private Timeline		timer;

	private Bomb			bomb;
	private List<Zombie>	zombies	= new ArrayList<Zombie>();

	@Override
	public void start(Stage stage) throws Exception {
		root = FXMLLoader.load(getClass().getResource("GamePanel.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		play();
		stage.show();
	}

	private void play() {
		for (int i = 0; i < 3; i++) {
			Zombie z = createZombie(i);
			zombies.add(z);
			root.getChildren().add(z);
		}
		bomb = createBomb();
		root.getChildren().add(bomb);
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				zombieMove();
				if (!shoot(bomb)) {
					root.getChildren().remove(bomb);
					bomb = createBomb();
					root.getChildren().add(bomb);
				}
			}
		});
		timer.getKeyFrames().add(kf);
		timer.play();
	}

	private void zombieMove() {
		for (int i = 2; i >= 0; i--) {
			Zombie z = zombies.get(i);
			if (z.getTranslateY() > HEIGHT) {
				root.getChildren().remove(z);
				int col = zombies.indexOf(z);
				zombies.remove(i);
				Zombie newZomb = createZombie(col);
				root.getChildren().add(newZomb);
				zombies.add(col, newZomb);
				continue;
			}
			z.setTranslateY(z.getSpeedy() + z.getTranslateY());
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

	private Bomb createBomb() {
		Bomb bomb = new Bomb();
		bomb.setTranslateX((WIDTH - bomb.getWidth()) / 2);
		bomb.setTranslateY(HEIGHT - bomb.getHeight());
		bomb.setSpeedx((Math.random() - 0.5f) * 6 + 1);
		bomb.setSpeedy((Math.random() * 3 + 1) * -1);
		return bomb;
	}

	private Zombie createZombie(int col) {
		Zombie z = new Zombie();
		int _col_width = (int) (WIDTH / 3);
		int x = _col_width * col + (_col_width - z.getWidth()) / 2;
		z.setTranslateX(x);
		z.setTranslateY(0);
		return z;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
