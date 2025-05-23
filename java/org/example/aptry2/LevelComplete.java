package org.example.aptry2;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LevelComplete {
    ImageView background, yellowBg, lvlCompleteTitle, lvlCompleteStar1, lvlCompleteStar2, lvlCompleteStar3;
    Button replayButton;
    ImageView replayImage;
    Button homeButton;
    Group completePageButton;

    public LevelComplete(ImageView background, ImageView yellowBg, ImageView lvlCompleteTitle,
                         ImageView lvlCompleteStar1, ImageView lvlCompleteStar2, ImageView lvlCompleteStar3,
                         Character character, ImageView replayImage, Button replayButton, String mode) throws FileNotFoundException {

        this.background = background;
        this.background.setX(0);
        this.background.setY(0);
        this.background.setFitWidth(1100);
        this.background.setFitHeight(700);
        this.background.setVisible(false);

        this.yellowBg = yellowBg;
        this.yellowBg.setX(300);
        this.yellowBg.setY(225);
        this.yellowBg.setFitWidth(500);
        this.yellowBg.setFitHeight(250);
        this.yellowBg.setVisible(false);

        this.lvlCompleteTitle = lvlCompleteTitle;
        this.lvlCompleteTitle.setX(360);
        this.lvlCompleteTitle.setY(290);
        this.lvlCompleteTitle.setFitWidth(380);
        this.lvlCompleteTitle.setFitHeight(40);
        this.lvlCompleteTitle.setVisible(false);

        this.lvlCompleteStar1 = lvlCompleteStar1;
        this.lvlCompleteStar1.setX(450);
        this.lvlCompleteStar1.setY(100);
        this.lvlCompleteStar1.setFitWidth(200);
        this.lvlCompleteStar1.setFitHeight(200);
        this.lvlCompleteStar1.setVisible(false);

        this.lvlCompleteStar2 = lvlCompleteStar2;
        this.lvlCompleteStar2.setX(350);
        this.lvlCompleteStar2.setY(150);
        this.lvlCompleteStar2.setFitWidth(150);
        this.lvlCompleteStar2.setFitHeight(150);
        this.lvlCompleteStar2.setVisible(false);

        this.lvlCompleteStar3 = lvlCompleteStar3;
        this.lvlCompleteStar3.setX(600);
        this.lvlCompleteStar3.setY(150);
        this.lvlCompleteStar3.setFitWidth(150);
        this.lvlCompleteStar3.setFitHeight(150);
        this.lvlCompleteStar3.setVisible(false);

        this.replayButton = replayButton;
        replayImage.setFitHeight(100);
        replayImage.setFitWidth(100);
        this.replayButton.setGraphic(replayImage);
        this.replayButton.setLayoutX(425);
        this.replayButton.setLayoutY(350);
        this.replayButton.setBackground(null);

        // Proper classpath-safe loading for home button image
        homeButton = new Button();
        InputStream homeStream = getClass().getResourceAsStream("/image/Button_Home.png");
        if (homeStream == null) {
            throw new FileNotFoundException("Could not find: /image/Button_Home.png");
        }
        ImageView homeImage = new ImageView(new Image(homeStream));
        homeImage.setFitHeight(100);
        homeImage.setFitWidth(100);
        homeButton.setGraphic(homeImage);
        homeButton.setLayoutX(575);
        homeButton.setLayoutY(350);
        homeButton.setBackground(null);

        homeButton.setOnMouseClicked(e -> {
            MediaPlayerSingleton.play("/backgroundMusic.m4a");
            character.stop = false;
            try {
                Stage stage = (Stage) homeButton.getScene().getWindow();
                switch (mode) {
                    case "Beginner" -> new LevelEasy().start(stage);
                    case "Intermediate" -> new LevelMedium().start(stage);
                    case "Advanced" -> new LevelHard().start(stage);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        completePageButton = new Group(this.replayButton, homeButton);
        completePageButton.setVisible(false);
    }

    public void switchToLvlComplete(boolean collectStar, boolean followSequence, boolean openTreasureBox) {
        MediaPlayerSingleton.play("/levelComplete.m4a");
        this.background.setVisible(true);
        this.yellowBg.setVisible(true);
        this.lvlCompleteTitle.setVisible(true);
        this.completePageButton.setVisible(true);
        this.replayButton.setVisible(true);

        int starCount = 0;
        if (collectStar) starCount++;
        if (followSequence) starCount++;
        if (openTreasureBox) starCount++;

        if (starCount >= 1) {
            this.lvlCompleteStar1.setVisible(true);
            if (starCount >= 2) {
                this.lvlCompleteStar2.setVisible(true);
                if (starCount == 3) {
                    this.lvlCompleteStar3.setVisible(true);
                }
            }
        }
    }
}
