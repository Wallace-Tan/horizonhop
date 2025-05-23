package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseCharacterController {
    static String selectedCharacter;

    @FXML
    private ImageView bobImage;

    @FXML
    private ImageView aliceImage;

    @FXML
    private Button startButton;

    @FXML
    private Button modeButton;

    @FXML
    private Button settingsButton;


    @FXML
    public void initialize() {
        bobImage.setImage(new Image(getClass().getResourceAsStream("/bob.png")));
        aliceImage.setImage(new Image(getClass().getResourceAsStream("/alice.png")));

        modeButton.setOnAction(event -> {
            if (selectedCharacter == null) {
                showAlert(Alert.AlertType.INFORMATION, "Stop", "Please choose character!");
            } else{
                try {
                    ModePage modepage = new ModePage();
                    modepage.start((Stage) modeButton.getScene().getWindow());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        settingsButton.setOnAction(event -> navigateToProfileSettings());
        MediaPlayerSingleton.play("/backgroundMusic.m4a");
    }

    @FXML
    private void handleMouseEnteredBob(MouseEvent event) {
        bobImage.setStyle("-fx-effect: innershadow(two-pass-box, white, 15, 0.5, 0, 0);");
    }

    @FXML
    private void handleMouseExitedBob(MouseEvent event) {
        if (!"Bob".equals(selectedCharacter)) {
            bobImage.setStyle("");
        }
    }

    @FXML
    private void handleMouseClickedBob(MouseEvent event) {
        selectedCharacter = "Bob";
        bobImage.setStyle("-fx-effect: innershadow(two-pass-box, white, 15, 0.5, 0, 0);");
        aliceImage.setStyle("");
    }

    @FXML
    private void handleMouseEnteredAlice(MouseEvent event) {
        aliceImage.setStyle("-fx-effect: innershadow(two-pass-box, white, 15, 0.5, 0, 0);");
    }

    @FXML
    private void handleMouseExitedAlice(MouseEvent event) {
        if (!"Alice".equals(selectedCharacter)) {
            aliceImage.setStyle("");
        }
    }

    @FXML
    private void handleMouseClickedAlice(MouseEvent event) {
        selectedCharacter = "Alice";
        aliceImage.setStyle("-fx-effect: innershadow(two-pass-box, white, 15, 0.5, 0, 0);");
        bobImage.setStyle("");
    }

    @FXML
    private void handleStartButtonClick() {
        if(selectedCharacter != null){
            if("Bob".equals(selectedCharacter)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/home.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) startButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ("Alice".equals(selectedCharacter)){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/homeA.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) startButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void navigateToProfileSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/profileSetting.fxml"));
            Parent root = loader.load();
            ProfileSettingController controller = loader.getController();
            controller.setPreviousPage("chooseCharacter");
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
