package org.example.aptry2;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PausePage {
    Image pauseBg = new Image(getClass().getResourceAsStream("/image/Bg_Pause.png"));
    Group pauseGroup;
    ImageView background;
    Button resumeButton;
    Button homeButton;

    public PausePage(Character character, Button replayButton, ImageView replayImage, String mode) throws FileNotFoundException {
        this.background = new ImageView(pauseBg);
        this.background.setFitWidth(600);
        this.background.setFitHeight(250);
        this.background.setX(250);
        this.background.setY(225);

        replayImage.setFitHeight(150);
        replayImage.setFitWidth(150);
        replayButton.setGraphic(replayImage);
        replayButton.setLayoutX(475);
        replayButton.setLayoutY(275);

        resumeButton = new Button();
        ImageView resumeImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Resume.png")));
        resumeImage.setFitHeight(150);
        resumeImage.setFitWidth(150);
        resumeButton.setGraphic(resumeImage);
        resumeButton.setLayoutX(287.5);
        resumeButton.setLayoutY(275);
        resumeButton.setBackground(null);
        resumeButton.setOnMouseClicked(e->{
            character.stop = false;
            this.background.setVisible(false);
            this.resumeButton.setVisible(false);
            replayButton.setVisible(false);
            this.homeButton.setVisible(false);
        });

        homeButton = new Button();
        ImageView homeImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Home.png")));
        homeImage.setFitHeight(150);
        homeImage.setFitWidth(150);
        homeButton.setGraphic(homeImage);
        homeButton.setLayoutX(662.5);
        homeButton.setLayoutY(275);
        homeButton.setBackground(null);
        homeButton.setOnMouseClicked(e->{
            character.stop = false;
            MediaPlayerSingleton.play("/backgroundMusic.m4a");
            if (mode.equals("Beginner")){
                try {
                    Stage stage = (Stage) homeButton.getScene().getWindow();
                    LevelEasy easy = new LevelEasy();
                    easy.start(stage);
                } catch (Exception ioe) {
                    throw new RuntimeException(ioe);
                }
            } else if (mode.equals("Intermediate")){
                try {
                    Stage stage = (Stage) homeButton.getScene().getWindow();
                    LevelMedium medium = new LevelMedium();
                    medium.start(stage);
                } catch (Exception ioe) {
                    throw new RuntimeException(ioe);
                }
            } else if (mode.equals("Advanced")){
                try {
                    Stage stage = (Stage) homeButton.getScene().getWindow();
                    LevelHard hard = new LevelHard();
                    hard.start(stage);
                } catch (Exception ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        });

        pauseGroup = new Group(this.background, replayButton, resumeButton, homeButton);
    }
}
