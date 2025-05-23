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

public class LevelMediumController {
    @FXML
    private ImageView medium1;

    @FXML
    private ImageView medium2;

    @FXML
    private ImageView medium3;

    @FXML
    private ImageView medium4;

    @FXML
    private ImageView medium5;

    @FXML
    private ImageView openmedium1;

    @FXML
    private ImageView openmedium2;

    @FXML
    private ImageView openmedium3;

    @FXML
    private ImageView openmedium4;

    @FXML
    private ImageView openmedium5;

    @FXML
    private ImageView bob;

    @FXML
    private ImageView alice;

    @FXML
    private Button button;

    ImageView character;

    public void initialize(){
        bob.setImage(new Image(getClass().getResourceAsStream("/bobmap1.png")));
        alice.setImage(new Image(getClass().getResourceAsStream("/alicemap1.png")));

        if ("Bob".equals(ChooseCharacterController.selectedCharacter)){
            character = bob;
            alice.setVisible(false);
        } else{
            character = alice;
            bob.setVisible(false);
        }

        character.setLayoutX(Character.positionX1);
        character.setLayoutY(Character.positionY1);

        openmedium1.setVisible(Character.mediumlevel1Complete);

        medium1.setVisible(!Character.mediumlevel1Complete);

        if (Character.levelComplete){
            next();
        }

        button.setOnAction(event -> {
            try {
                ModePage modepage = new ModePage();
                modepage.start((Stage) button.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void Level1() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tutorialMedium.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) medium1.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void next() {
        if (Intermediate_Level1.pass1){
            close1();
            Intermediate_Level1.pass1 = false;
        }

    }
    void close1(){
        medium1.setVisible(false);
        openmedium1.setVisible(true);
        character.setLayoutX(430);
        character.setLayoutY(335);
        Character.mediumlevel1Complete = true;
        Character.positionX1 = character.getLayoutX();
        Character.positionY1 = character.getLayoutY();
    }
}
