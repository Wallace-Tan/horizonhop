package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TutorialMediumController {

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

    private Image medium1Image;
    private Image medium2Image;
    private Image skipButtonImage;
    private Image startButtonImage;
    private Image nextButtonImage;
    private Image prevButtonImage;

    private boolean showingEasy1;

    @FXML
    public void initialize() {
        medium1Image = new Image(getClass().getResourceAsStream("/medium1.png"));
        medium2Image = new Image(getClass().getResourceAsStream("/medium2.png"));
        skipButtonImage = new Image(getClass().getResourceAsStream("/skipButton.png"));
        startButtonImage = new Image(getClass().getResourceAsStream("/startButton.png"));
        nextButtonImage = new Image(getClass().getResourceAsStream("/nextButton.png"));
        prevButtonImage = new Image(getClass().getResourceAsStream("/prevButton.png"));

        bannerImageView.setImage(medium1Image);
        skipButtonImageView.setImage(skipButtonImage);
        nextButtonImageView.setImage(nextButtonImage);
        prevButtonImageView.setImage(prevButtonImage);

        showingEasy1 = true;
        updateButtons();
    }

    @FXML
    private void handleNextButtonClick() {
        showingEasy1 = false;
        bannerImageView.setImage(medium2Image);
        skipButtonImageView.setImage(startButtonImage);
        updateButtons();
    }

    @FXML
    private void handlePrevButtonClick() {
        showingEasy1 = true;
        bannerImageView.setImage(medium1Image);
        skipButtonImageView.setImage(skipButtonImage);
        updateButtons();
    }

    @FXML
    private void handleSkipButtonClick() throws IOException {
        if (!showingEasy1){
            try{
                Stage stage = (Stage) nextButton.getScene().getWindow();
                Intermediate_Level1_PlatformPlacement intermediateLevel1 = new Intermediate_Level1_PlatformPlacement();
                intermediateLevel1.start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            try{
                Stage stage = (Stage) nextButton.getScene().getWindow();
                Intermediate_Level1_PlatformPlacement intermediateLevel1 = new Intermediate_Level1_PlatformPlacement();
                intermediateLevel1.start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void updateButtons() {
        nextButton.setVisible(showingEasy1);
        prevButton.setVisible(!showingEasy1);
    }
}

