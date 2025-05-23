package org.example.aptry2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HintHardController {

    @FXML
    private Pane circleProgress;

    @FXML
    private Arc arcProgress;

    @FXML
    private Label timerLabel;

    private int timeLeft = 10;
    private Timeline timeline;

    @FXML
    public void initialize() {
        arcProgress.setLength(360);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeLeft > 0) {
                timeLeft--;
                timerLabel.setText(String.valueOf(timeLeft));
                arcProgress.setLength(-timeLeft * 36);
            } else {
                timeline.stop();
                moveToNextPage();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveToNextPage() {
        Stage stage = (Stage) circleProgress.getScene().getWindow();
        try {
            Advanced_Level1_PlatformPlacement advancedLevel1PlatformPlacement = new Advanced_Level1_PlatformPlacement();
            advancedLevel1PlatformPlacement.start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
