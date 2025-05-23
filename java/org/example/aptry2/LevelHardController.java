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

public class LevelHardController {
    @FXML
    private ImageView hard1;

    @FXML
    private ImageView openhard1;

    @FXML
    private ImageView bob;

    @FXML
    private ImageView alice;

    @FXML
    private Button button;

    ImageView character;

    public void initialize() throws IOException {
        bob.setImage(new Image(getClass().getResourceAsStream("/bobmap1.png")));
        alice.setImage(new Image(getClass().getResourceAsStream("/alicemap1.png")));

        if ("Bob".equals(ChooseCharacterController.selectedCharacter)){
            character = bob;
            alice.setVisible(false);
        } else{
            character = alice;
            bob.setVisible(false);
        }

        character.setLayoutX(Character.positionX2);
        character.setLayoutY(Character.positionY2);
        openhard1.setVisible(Character.hardlevel1Complete);

        hard1.setVisible(!Character.hardlevel1Complete);

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
    public void Level1() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tutorialHard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) hard1.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void next() {
        if (Advanced_Level1.pass1){
            close1();
            Advanced_Level1.pass1 = false;
        }
    }

    void close1(){
        hard1.setVisible(false);
        openhard1.setVisible(true);
        character.setLayoutX(570);
        character.setLayoutY(65);
        Character.hardlevel1Complete = true;
        Character.positionX2 = character.getLayoutX();
        Character.positionY2 = character.getLayoutY();
    }
}
