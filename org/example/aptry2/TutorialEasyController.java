package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TutorialEasyController {

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView bannerImageView;

    @FXML
    private ImageView nextButtonImageView;

    @FXML
    private ImageView prevButtonImageView;

    @FXML
    private ImageView skipButtonImageView;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button skipButton;

    private Image easy1Image;
    private Image easy2Image;
    private Image skipButtonImage;
    private Image startButtonImage;
    private Image nextButtonImage;
    private Image prevButtonImage;

    private boolean showingEasy1;

    @FXML
    public void initialize() {
        easy1Image = new Image(getClass().getResourceAsStream("/easy1.png"));
        easy2Image = new Image(getClass().getResourceAsStream("/easy2.png"));
        skipButtonImage = new Image(getClass().getResourceAsStream("/skipButton.png"));
        startButtonImage = new Image(getClass().getResourceAsStream("/startButton.png"));
        nextButtonImage = new Image(getClass().getResourceAsStream("/nextButton.png"));
        prevButtonImage = new Image(getClass().getResourceAsStream("/prevButton.png"));

        bannerImageView.setImage(easy1Image);
        skipButtonImageView.setImage(skipButtonImage);
        nextButtonImageView.setImage(nextButtonImage);
        prevButtonImageView.setImage(prevButtonImage);

        showingEasy1 = true;
        updateButtons();
    }

    @FXML
    private void handleNextButtonClick() {
        showingEasy1 = false;
        bannerImageView.setImage(easy2Image);
        skipButtonImageView.setImage(startButtonImage);
        updateButtons();
    }

    @FXML
    private void handlePrevButtonClick() {
        showingEasy1 = true;
        bannerImageView.setImage(easy1Image);
        skipButtonImageView.setImage(skipButtonImage);
        updateButtons();
    }

    @FXML
    private void handleSkipButtonClick() throws IOException {
        if (!showingEasy1){
            Stage stage = (Stage) nextButton.getScene().getWindow();
            Beginner_Level1 level1 = new Beginner_Level1();
            level1.start(stage);
        }else {
            Stage stage = (Stage) nextButton.getScene().getWindow();
            Beginner_Level1 level1 = new Beginner_Level1();
            level1.start(stage);
        }
    }

    private void updateButtons() {
        nextButton.setVisible(showingEasy1);
        prevButton.setVisible(!showingEasy1);
    }
}

