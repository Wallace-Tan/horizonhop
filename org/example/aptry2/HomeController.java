package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button letsGoButton;

    @FXML
    private Button modeButton;

    @FXML
    private Button settingsButton;

    @FXML
    public void initialize() {
        letsGoButton.setOnAction(event -> {
            try {
                ModePage modepage = new ModePage();
                modepage.start((Stage) letsGoButton.getScene().getWindow());
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
    }

    public void navigateToProfileSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/profileSetting.fxml"));
            Parent root = loader.load();

            ProfileSettingController controller = loader.getController();
            controller.setPreviousPage("home");

            Stage stage = (Stage) settingsButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
