package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;

public class LoginPageController {
    private AES aes; // AES encryption instance
    boolean userFound; // Flag to check if the user is found
    static String decryptedName; // Decrypted username
    static String decryptedPassword; // Decrypted password
    static String decryptedEmail; // Decrypted email

    // Method to update decrypted values
    public static void updateDecryptedValues(String name, String password, String email) {
        decryptedName = name;
        decryptedPassword = password;
        decryptedEmail = email;
    }

    // FXML annotations for UI elements
    @FXML
    private ImageView lockImageView1Toggle;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private Button backButton;

    @FXML
    private Button signupButton;

    private Image lockImage; // Image for locked state
    private Image openLockImage; // Image for unlocked state

    @FXML
    public void initialize() throws Exception {
        aes = new AES();
        aes.init(); // Initialize AES instance

        lockImage = new Image(getClass().getResourceAsStream("/lock.png"));
        openLockImage = new Image(getClass().getResourceAsStream("/openlock.png"));

        textField2.setVisible(false); // Hide the text field initially

        // Set action for signup button to navigate to the sign-in page
        signupButton.setOnAction(event -> {
            try {
                SignInPage signinpage = new SignInPage();
                signinpage.start((Stage) signupButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Set action for back button to navigate to the landing page
        backButton.setOnAction(event -> {
            try {
                LandingPage landing = new LandingPage();
                landing.start((Stage) backButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Method to toggle password field visibility
    @FXML
    public void togglePasswordField1() {
        textField2.setVisible(false); // Hide the text field initially
        if (passwordField1.isVisible()) {
            textField2.setText(passwordField1.getText());
            textField2.setPromptText("Password");
            textField2.setVisible(true); // Show text field
            passwordField1.setVisible(false); // Hide password field
            lockImageView1Toggle.setImage(openLockImage); // Set image to open lock
        } else {
            passwordField1.setText(textField2.getText());
            passwordField1.setVisible(true); // Show password field
            textField2.setVisible(false); // Hide text field
            lockImageView1Toggle.setImage(lockImage); // Set image to lock
        }
    }

    // Method to handle login action
    @FXML
    public void LoginAction() {
        if (textField2.isVisible()) {
            passwordField1.setText(textField2.getText());
        }
        String name = textField1.getText();
        String password = passwordField1.getText();
        if (name.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Failed", "Please fill in all fields");
        } else {
            userFound = false;
            try {
                readAndDecrypt(name, password);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        textField1.clear();
        textField2.clear();
        passwordField1.clear();
    }

    // Method to read and decrypt user data from file
    void readAndDecrypt(String name, String password) throws IOException, ClassNotFoundException {
        File file = new File("userinfo.bin");
        if (!file.exists()) {
            showAlert(Alert.AlertType.WARNING, "Login Failed", "User does not exist");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    tryPerson readPerson = (tryPerson) ois.readObject();
                    decryptedName = aes.decrypt(readPerson.getName());
                    decryptedPassword = aes.decrypt(readPerson.getPassword());
                    decryptedEmail = aes.decrypt(readPerson.getEmail());
                    if (decryptedName.equals(name)) {
                        if (decryptedPassword.equals(password)) {
                            showAlert(Alert.AlertType.INFORMATION, "Login Success", "Login Success");
                            userFound = true;
                            // Load the next scene upon successful login
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/chooseCharacter.fxml"));
                            Parent root = loader.load();
                            Stage stage = (Stage) textField1.getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                            break;
                        } else {
                            showAlert(Alert.AlertType.WARNING, "Login Failed", "Incorrect username or password");
                            return;
                        }
                    }
                } catch (EOFException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!userFound) {
                showAlert(Alert.AlertType.WARNING, "Login Failed", "User does not exist");
            }
        }
    }

    // Method to show alert messages
    void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}