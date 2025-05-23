package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class ModePageController {

    @FXML
    private Button modeButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        logoutButton.setOnAction(event -> {
            showAlert(Alert.AlertType.CONFIRMATION, "LogOut", "Confirm Log Out?");
            try {
                LandingPage landingpage = new LandingPage();
                landingpage.start((Stage) logoutButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        modeButton.setOnAction(event -> {
            try {
                ModePage modepage = new ModePage();
                modepage.start((Stage) modeButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsButton.setOnAction(event -> navigateToProfileSettings());

        easyButton.setOnAction(event -> {
            try {
                LevelEasy leveleasy = new LevelEasy();
                leveleasy.start((Stage) easyButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mediumButton.setOnAction(event -> {
            try {
                LevelMedium levelmedium = new LevelMedium();
                levelmedium.start((Stage) mediumButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hardButton.setOnAction(event -> {
            try {
                LevelHard levelhard = new LevelHard();
                levelhard.start((Stage) hardButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void navigateToProfileSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/profileSetting.fxml"));
            Parent root = loader.load();

            ProfileSettingController controller = loader.getController();
            controller.setPreviousPage("mode");

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

