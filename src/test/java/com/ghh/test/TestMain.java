package com.ghh.test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestMain extends Application {
	private Group	root;

	@Override
	public void start(Stage stage) throws Exception {
		root = new Group();
		Scene scene = new Scene(root, 600, 400, Color.rgb(39, 117, 92, 0.3f));
		stage.setScene(scene);
		stage.show();
		initComponent();
	}

	private void initComponent() {
		final ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/shuriken.png")));
		imageView.setFitWidth(32);
		imageView.setFitHeight(32);
		root.getChildren().add(imageView);

		Timeline time = new Timeline();
		time.setAutoReverse(false);
		time.setCycleCount(Timeline.INDEFINITE);
		time.getKeyFrames().add(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				double rotate = (imageView.getRotate() + 10) % 360;
				imageView.setRotate(rotate);
			}
		}));
		time.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
