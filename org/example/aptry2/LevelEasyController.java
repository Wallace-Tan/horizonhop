package org.example.aptry2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelEasyController {

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView openimage1;

    @FXML
    private ImageView openimage2;

    @FXML
    private ImageView bob;

    @FXML
    private ImageView alice;

    @FXML
    private Button button;

    ImageView character;

    public void initialize() throws IOException {

        bob.setImage(new Image(getClass().getResourceAsStream("/bobmap.png")));
        alice.setImage(new Image(getClass().getResourceAsStream("/alicemap.png")));

        if ("Bob".equals(ChooseCharacterController.selectedCharacter)){
            character = bob;
            alice.setVisible(false);
        } else{
            character = alice;
            bob.setVisible(false);
        }

        character.setLayoutX(Character.positionX);
        character.setLayoutY(Character.positionY);

        openimage1.setVisible(Character.level1Complete);
        openimage2.setVisible(Character.level2Complete);

        image1.setVisible(!Character.level1Complete);
        image2.setVisible(!Character.level2Complete);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tutorialEasy.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) image1.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    public void Level2() throws IOException {
        Stage stage = (Stage) image2.getScene().getWindow();
        Beginner_Level2 level2 = new Beginner_Level2();
        level2.start(stage);
    }

    public void next() {
        if (Beginner_Level1.pass1){
            close1();
            Beginner_Level1.pass1 = false;
        } else if (Beginner_Level2.pass2){
            close2();
            Beginner_Level2.pass2 = false;
        }
    }

    void close1(){
        image1.setVisible(false);
        openimage1.setVisible(true);
        character.setLayoutX(446);
        character.setLayoutY(95);
        Character.level1Complete = true;
        Character.positionX = character.getLayoutX();
        Character.positionY = character.getLayoutY();
    }

    void close2(){
        image1.setVisible(false);
        openimage1.setVisible(true);
        image2.setVisible(false);
        openimage2.setVisible(true);
        character.setLayoutX(499);
        character.setLayoutY(303);
        Character.level2Complete = true;
        Character.positionX = character.getLayoutX();
        Character.positionY = character.getLayoutY();
    }
}



