package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LandingPageController {

    @FXML
    private Button LoginButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private void initialize() {
        LoginButton.setOnAction(event -> {
            try {
                LoginPage loginpage = new LoginPage();
                loginpage.start((Stage) LoginButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        SignUpButton.setOnAction(event -> {
            try {
                SignInPage signuppage = new SignInPage();
                signuppage.start((Stage) SignUpButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
