package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;

public class SignInPageController {
    private AES aes; // AES encryption object

    @FXML
    private ImageView lockImageView1Toggle; // ImageView to toggle password visibility for the first password field

    @FXML
    private ImageView lockImageView2Toggle; // ImageView to toggle password visibility for the second password field

    @FXML
    private PasswordField passwordField1; // First password field

    @FXML
    private PasswordField passwordField2; // Second password field

    @FXML
    private TextField textField1; // Text field for username

    @FXML
    private TextField textField2; // Text field to show first password when toggled

    @FXML
    private TextField textField3; // Text field to show second password when toggled

    @FXML
    private TextField textField4; // Text field for email

    @FXML
    private Button backButton; // Button to go back to the previous page

    @FXML
    private Button loginButton; // Button to go to the login page

    @FXML
    private CheckBox checkbox; // CheckBox to agree to terms and conditions

    private Image lockImage; // Image of the locked icon

    private Image openLockImage; // Image of the unlocked icon

    @FXML
    public void initialize() throws Exception {
        aes = new AES();
        aes.init();

        lockImage = new Image(getClass().getResourceAsStream("/lock.png"));
        openLockImage = new Image(getClass().getResourceAsStream("/openlock.png"));

        textField2.setVisible(false); // Hide first password text field by default
        textField3.setVisible(false); // Hide second password text field by default

        // Set action for login button to navigate to login page
        loginButton.setOnAction(event -> {
            try {
                LoginPage loginpage = new LoginPage();
                loginpage.start((Stage) loginButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Set action for back button to navigate to landing page
        backButton.setOnAction(event -> {
            try {
                LandingPage landing = new LandingPage();
                landing.start((Stage) backButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Method to toggle visibility of the first password field
    @FXML
    public void togglePasswordField1() {
        textField2.setVisible(false);
        if (passwordField1.isVisible()) {
            textField2.setText(passwordField1.getText());
            textField2.setPromptText("Password");
            textField2.setVisible(true);
            passwordField1.setVisible(false);
            lockImageView1Toggle.setImage(openLockImage);
        } else {
            passwordField1.setText(textField2.getText());
            passwordField1.setVisible(true);
            textField2.setVisible(false);
            lockImageView1Toggle.setImage(lockImage);
        }
    }

    // Method to toggle visibility of the second password field
    @FXML
    public void togglePasswordField2() {
        textField3.setVisible(false);
        if (passwordField2.isVisible()) {
            textField3.setText(passwordField2.getText());
            textField3.setPromptText("Confirm Password");
            textField3.setVisible(true);
            passwordField2.setVisible(false);
            lockImageView2Toggle.setImage(openLockImage);
        } else {
            passwordField2.setText(textField3.getText());
            passwordField2.setVisible(true);
            textField3.setVisible(false);
            lockImageView2Toggle.setImage(lockImage);
        }
    }

    // Method to handle sign-up action
    @FXML
    public void SignUpAction() {
        if (textField2.isVisible()) {
            passwordField1.setText(textField2.getText());
        }
        if (textField3.isVisible()) {
            passwordField2.setText(textField3.getText());
        }

        String name = textField1.getText();
        String password = passwordField1.getText();
        String confirmpassword = passwordField2.getText();
        String email = textField4.getText();
        tryPerson person = new tryPerson(name, password, email);

        // Check if any field is empty
        if (name.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Registration Failed", "Please fill in all fields");
        } else {
            // Check if passwords match
            if (password.equals(confirmpassword)) {
                // Check if terms and conditions are agreed
                if (checkbox.isSelected()) {
                    try {
                        // Check if user already exists
                        if (UserExists(name)) {
                            showAlert(Alert.AlertType.WARNING, "Registration Failed", "User already exists");
                        } else {
                            // Encrypt user details and save
                            String encryptedName = aes.encrypt(person.getName());
                            String encryptedPassword = aes.encrypt(person.getPassword());
                            String encryptedEmail = aes.encrypt(person.getEmail());
                            tryPerson encryptedPerson = new tryPerson(encryptedName, encryptedPassword, encryptedEmail);
                            savePerson(encryptedPerson);
                            showAlert(Alert.AlertType.INFORMATION, "Registration Success", "Registration Success");
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/loginpage.fxml"));
                                Parent root = loader.load();
                                Stage stage = (Stage) textField1.getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, "Registration Failed", "Please agree to the terms & conditions");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Registration Failed", "Passwords do not match");
            }
        }

        // Clear all fields after sign-up action
        textField1.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        passwordField1.clear();
        passwordField2.clear();
        checkbox.setSelected(false);
    }

    // Method to save user details to a file
    void savePerson(tryPerson person) {
        File file = new File("userinfo.bin");
        boolean append = file.exists() && file.length() > 0;
        try (FileOutputStream fos = new FileOutputStream(file, append);
             ObjectOutputStream oos = append ? new AppendableObjectOutputStream(fos) : new ObjectOutputStream(fos)) {
            oos.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if user already exists in the file
    boolean UserExists(String name) throws Exception {
        File file = new File("userinfo.bin");
        if (!file.exists() || file.length() == 0) {
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    tryPerson readPerson = (tryPerson) ois.readObject();
                    String decryptedName = aes.decrypt(readPerson.getName());
                    if (decryptedName.equals(name)) {
                        return true;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to show an alert message
    void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}