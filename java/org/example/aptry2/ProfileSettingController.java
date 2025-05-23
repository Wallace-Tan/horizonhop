package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileSettingController {
    private AES aes; // AES encryption/decryption instance
    String previousPage; // To track the previous page

    @FXML
    private ImageView profileImageView; // Profile image view

    @FXML
    private TextField usernameField; // Username text field

    @FXML
    private TextField passwordField; // Password text field

    @FXML
    private TextField emailField; // Email text field

    @FXML
    private Button editButton; // Edit button

    @FXML
    private Button saveButton; // Save button

    @FXML
    private Button backButton; // Back button

    @FXML
    private Circle profileCircleClip; // Circle clip for profile image

    @FXML
    public void initialize() throws Exception {
        aes = new AES();
        aes.init();

        // Set profile image based on selected character
        if ("Alice".equals(ChooseCharacterController.selectedCharacter)) {
            profileImageView.setImage(new Image(getClass().getResourceAsStream("/alice.png")));
        } else {
            profileImageView.setImage(new Image(getClass().getResourceAsStream("/bob.png")));
        }
        profileCircleClip.setRadius(75);
        profileImageView.setClip(profileCircleClip);

        saveButton.setVisible(false); // Hide save button initially
        backButton.setVisible(true); // Show back button

        // Set initial values from decrypted data
        usernameField.setText(LoginPageController.decryptedName);
        passwordField.setText(LoginPageController.decryptedPassword);
        emailField.setText(LoginPageController.decryptedEmail);

        // Set fields to non-editable
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        emailField.setEditable(false);

        backButton.setOnAction(event -> handleBackButtonClick()); // Set back button action

        // Set profile image click action to change profile picture
        profileImageView.setOnMouseClicked(event -> {
            if (usernameField.isEditable()) {
                handleChangeProfilePicture();
            }
        });
    }

    // Method to set previous page
    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    // Handle back button click based on the previous page
    @FXML
    private void handleBackButtonClick() {
        if ("home".equals(previousPage)) {
            try {
                Home home = new Home();
                home.start((Stage) backButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("chooseCharacter".equals(previousPage)) {
            try {
                ChooseCharacter chooseCharacter = new ChooseCharacter();
                chooseCharacter.start((Stage) backButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("mode".equals(previousPage)) {
            try {
                ModePage modepage = new ModePage();
                modepage.start((Stage) backButton.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("homeA".equals(previousPage)) {
            try {
                HomeA homeA = new HomeA();
                homeA.start((Stage) backButton.getScene().getWindow());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Handle edit button click to enable editing
    @FXML
    private void handleEditButtonClick() {
        saveButton.setVisible(true);
        editButton.setVisible(false);
        saveButton.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-min-width: 200; -fx-max-height: 35; -fx-min-height: 30;");
        usernameField.setStyle("-fx-border-radius: 30; -fx-max-height: 35; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: yellow");
        passwordField.setStyle("-fx-border-radius: 30; -fx-max-height: 35; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: yellow");
        emailField.setStyle("-fx-border-radius: 30; -fx-max-height: 35; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: yellow");
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        emailField.setEditable(true);
    }

    // Handle save button click to save the updated information
    @FXML
    private void handleSaveButtonClick() throws Exception {
        saveButton.setVisible(false);
        editButton.setVisible(true);
        usernameField.setStyle("-fx-border-color: white; -fx-border-radius: 30; -fx-background-color: transparent; -fx-max-height: 35;");
        passwordField.setStyle("-fx-border-color: white; -fx-border-radius: 30; -fx-background-color: transparent; -fx-max-height: 35;");
        emailField.setStyle("-fx-border-color: white; -fx-border-radius: 30; -fx-background-color: transparent; -fx-max-height: 35;");
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        emailField.setEditable(false);

        // Encrypt the updated values
        String encryptedName = aes.encrypt(usernameField.getText());
        String encryptedPassword = aes.encrypt(passwordField.getText());
        String encryptedEmail = aes.encrypt(emailField.getText());
        tryPerson encryptedPerson = new tryPerson(encryptedName, encryptedPassword, encryptedEmail);
        updatePerson(encryptedPerson); // Update person in the file
        LoginPageController.updateDecryptedValues(usernameField.getText(), passwordField.getText(), emailField.getText());
        showAlert(Alert.AlertType.INFORMATION, "Edit Success", "Edit Success");
    }

    // Method to update person in the file
    void updatePerson(tryPerson updatedPerson) throws Exception {
        File file = new File("userinfo.bin");
        if (!file.exists()) {
            return;
        }

        List<tryPerson> persons = new ArrayList<>();
        // Read existing persons from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    tryPerson person = (tryPerson) ois.readObject();
                    persons.add(person);
                } catch (EOFException e) {
                    break;
                }
            }
        }

        // Update the person in the list
        for (int i = 0; i < persons.size(); i++) {
            tryPerson person = persons.get(i);
            String decryptedName = aes.decrypt(person.getName());
            if (decryptedName.equals(LoginPageController.decryptedName)) {
                persons.set(i, updatedPerson);
                break;
            }
        }

        // Write the updated list back to the file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (tryPerson person : persons) {
                oos.writeObject(person);
            }
        }
    }

    // Method to show alert dialogs
    void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Handle profile picture change
    @FXML
    public void handleChangeProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(profileImageView.getScene().getWindow());
        if (selectedFile != null) {
            profileImageView.setImage(new Image(selectedFile.toURI().toString()));
            profileImageView.setFitHeight(200);
            profileImageView.setFitWidth(160);
        }
    }
}