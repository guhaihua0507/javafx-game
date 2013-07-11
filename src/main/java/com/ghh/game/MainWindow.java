package com.ghh.game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindow extends Application {
	private final int			WIDTH	= 400;
	private final int			HEIGHT	= 600;
	private final int			COLUMNS	= 4;

	private AnchorPane			root;

	private Weapon				weapon;
	private List<List<Zombie>>	zombies;
	private Timeline			ztl;

	private AnimationTimer		timer;

	@Override
	public void start(Stage stage) throws Exception {
		root = FXMLLoader.load(getClass().getResource("GamePanel.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		play();
	}

	private void initGameContext() {
		zombies = new ArrayList<List<Zombie>>(COLUMNS);
		for (int i = 0; i < COLUMNS; i++) {
			zombies.add(new ArrayList<Zombie>());
		}
	}

	private void play() {
		initGameContext();

		ztl = new Timeline();
		ztl.setCycleCount(Timeline.INDEFINITE);
		ztl.getKeyFrames().add(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				int random = (int) (Math.random() * COLUMNS);
				addZombie(random);
			}
		}));
		ztl.play();

		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				updateZombies();
				if (!shoot(weapon, now)) {
					addWeapon();
				}
				hitZombie();
			}
		};
		timer.start();
		addWeapon();
	}

	private void hitZombie() {
		for (int col = 0; col < COLUMNS; col++) {
			List<Zombie> column = zombies.get(col);
			if (column.isEmpty()) {
				continue;
			}
			Zombie z = column.get(0);
			Rectangle2D r1 = new Rectangle2D(z.getTranslateX(), z.getTranslateY(), z.getWidth(), z.getHeight());
			Rectangle2D r2 = new Rectangle2D(weapon.getTranslateX(), weapon.getTranslateY(), weapon.getWidth(),
					weapon.getHeight());
			if (r1.intersects(r2)) {
				z.setLife(z.getLife() - weapon.getPower());
				if (z.getLife() <= 0) {
					column.remove(z);
					root.getChildren().remove(z);
				}
				addWeapon();
			}
		}
	}

	private void updateZombies() {
		for (int col = 0; col < COLUMNS; col++) {
			List<Zombie> column = zombies.get(col);
			List<Zombie> _toRemove = new ArrayList<Zombie>();
			for (int i = 0; i < column.size(); i++) {
				Zombie z = column.get(i);
				if (z.getTranslateY() > HEIGHT - 100) {
					_toRemove.add(z);
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
		}
	}

	private boolean shoot(Weapon b, long l) {
		if (b == null) {
			return false;
		}
		b.update(l);
		double x = b.getTranslateX();
		double y = b.getTranslateY();
		if (x < 0 - b.getWidth() || x > root.getPrefWidth() || y < 0 - b.getHeight() || y > root.getPrefHeight()) {
			return false;
		} else {
			return true;
		}
	}

	private void addZombie(int index) {
		List<Zombie> list = zombies.get(index);
		Zombie z = new Zombie();
		int _col_width = (int) (WIDTH / COLUMNS);
		double x = _col_width * index + (_col_width - z.getWidth()) / 2;
		double y = z.getHeight() * -1;
		if (!list.isEmpty()) {
			Zombie last = list.get(list.size() - 1);
			y = last.getTranslateY() - z.getHeight() - 10;
		}
		z.setTranslateX(x);
		z.setTranslateY(y);

		root.getChildren().add(z);
		list.add(z);
	}

	private void addWeapon() {
		if (weapon != null) {
			root.getChildren().remove(weapon);
		}
		weapon = newWeapon();
		weapon.setTranslateX((WIDTH - weapon.getWidth()) / 2);
		weapon.setTranslateY(HEIGHT - weapon.getHeight());
		root.getChildren().add(weapon);
	}

	private Weapon newWeapon() {
		Weapon weapon;
		int random = (int) (Math.random() * 3);
		switch (random) {
		case 0:
			weapon = new Shuriken();
			break;
		case 1:
			weapon = new Bomb();
			break;
		case 2:
			weapon = new Hammer();
			break;
		default:
			weapon = new Shuriken();
		}
		return weapon;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
