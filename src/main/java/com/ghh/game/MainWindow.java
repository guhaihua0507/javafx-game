package com.ghh.game;

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
	private AnchorPane	root;
	private Timeline	timer;

	private Bomb		bomb;

	@Override
	public void start(Stage stage) throws Exception {
		root = FXMLLoader.load(getClass().getResource("GamePanel.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		play();
		stage.show();
	}

	private void play() {
		bomb = createBomb();
		root.getChildren().add(bomb);
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if (bomb == null) {
					return;
				}
				double x = bomb.getSpeedx() + bomb.getTranslateX();
				double y = bomb.getSpeedy() + bomb.getTranslateY();
				if (x < 0 - bomb.getWidth() || x > root.getPrefWidth() || y < 0 - bomb.getHeight()
						|| y > root.getPrefHeight()) {
					root.getChildren().remove(bomb);
					bomb = createBomb();
					root.getChildren().add(bomb);
				} else {
					bomb.setTranslateX(x);
					bomb.setTranslateY(y);
				}
			}
		});
		timer.getKeyFrames().add(kf);
		timer.play();
	}

	private Bomb createBomb() {
		Bomb bomb = new Bomb();
		double sceneWidth = root.getPrefWidth();
		double sceneHeight = root.getPrefHeight();
		bomb.setTranslateX((sceneWidth - bomb.getWidth()) / 2);
		bomb.setTranslateY(sceneHeight - bomb.getHeight());
		bomb.setSpeedx((Math.random() - 0.5f) * 6 + 1);
		bomb.setSpeedy((Math.random() * 3 + 1) * -1);
		return bomb;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
