package com.example.gamedemo;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane scene;
    @FXML
    private Rectangle player;
    @FXML
    private Rectangle platform1, platform2, platform3, platform4,
            platform5, platform6, platform7, platform8;
    @FXML
    private Circle coin1, coin2, coin3, coin4, coin5, coin6,
            coin7, coin8, coin9, coin10;

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private int movementVariable = 5;

    private double sceneTop, sceneBottom, sceneLeft, sceneRight;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneTop = 0;
        sceneBottom = scene.getPrefHeight();
        System.out.println(scene.getPrefHeight());
        sceneLeft = 0;
        sceneRight = scene.getPrefWidth();

        movementSetup();

        collectionCoins.start();
        collisions.start();
        moving.start();
    }

    AnimationTimer collectionCoins = new AnimationTimer() {
        @Override
        public void handle(long l) {
            List<Circle> coins = Arrays.asList(
                    coin1, coin2, coin3, coin4, coin5,
                    coin6, coin7, coin8, coin9, coin10
            );

            for (Circle coin : coins) {
                if (player.getBoundsInParent().intersects(coin.getBoundsInParent())) {
                    scene.getChildren().remove(coin);
                }
            }
        }
    };

    AnimationTimer collisions = new AnimationTimer() {
        @Override
        public void handle(long l) {

            double playerTop = player.getLayoutY();
            double playerBottom = player.getLayoutY() + player.getHeight();
            double playerLeft = player.getLayoutX();
            double playerRight = player.getLayoutX() + player.getWidth();

            if (playerTop < sceneTop) {
                player.setLayoutY(sceneTop + 1);
            }
            if (playerBottom > sceneBottom) {
                player.setLayoutY(sceneBottom - player.getHeight() - 1);
            }
            if (playerLeft < sceneLeft) {
                player.setLayoutX(sceneLeft + 1);
            }
            if (playerRight > sceneRight) {
                player.setLayoutX(sceneRight - player.getWidth() - 1);
            }

            List<Rectangle> platforms = Arrays.asList(
                    platform1, platform2, platform3, platform4,
                    platform5, platform6, platform7, platform8
            );

            for (Rectangle platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    double platformTop = platform.getLayoutY();
                    double platformBottom = platform.getLayoutY() + platform.getHeight();
                    double platformLeft = platform.getLayoutX();
                    double platformRight = platform.getLayoutX() + platform.getWidth();

                    if (playerBottom > platformTop && playerTop < platformTop) {
                        player.setLayoutY(platformTop - player.getHeight());
                    }
                    if (playerTop < platformBottom && playerBottom > platformBottom) {
                        player.setLayoutY(platformBottom);
                    }
                    if (playerLeft < platformRight && playerRight > platformRight) {
                        player.setLayoutX(platformRight);
                    }
                    if (playerRight > platformLeft && playerLeft < platformLeft) {
                        player.setLayoutX(platformLeft - player.getWidth());
                    }

                }
            }
        }
    };

    AnimationTimer moving = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (wPressed.get()){
                player.setLayoutY(player.getLayoutY() - movementVariable);
            }
            if (sPressed.get()) {
                player.setLayoutY(player.getLayoutY() + movementVariable);
            }
            if (aPressed.get()) {
                player.setLayoutX(player.getLayoutX() - movementVariable);
            }
            if (dPressed.get()) {
                player.setLayoutX(player.getLayoutX() + movementVariable);
            }
        }
    };

    public void movementSetup(){
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case W:
                    wPressed.set(true);
                    break;
                case A:
                    aPressed.set(true);
                    break;
                case S:
                    sPressed.set(true);
                    break;
                case D:
                    dPressed.set(true);
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()){
                case W:
                    wPressed.set(false);
                    break;
                case A:
                    aPressed.set(false);
                    break;
                case S:
                    sPressed.set(false);
                    break;
                case D:
                    dPressed.set(false);
                    break;
                default:
                    break;
            }
        });
    }


}
