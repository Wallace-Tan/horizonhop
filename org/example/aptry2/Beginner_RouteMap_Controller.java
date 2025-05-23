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

public class Beginner_RouteMap_Controller
{

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    @FXML
    private ImageView image5;

    @FXML
    private ImageView openimage1;

    @FXML
    private ImageView openimage2;

    @FXML
    private ImageView openimage3;

    @FXML
    private ImageView openimage4;

    @FXML
    private ImageView openimage5;

    @FXML
    private ImageView bob;

    @FXML
    private ImageView alice;

    @FXML
    private Button button;
    public boolean level1Complete = false;
    public boolean level2Complete = false;
    public boolean level3Complete = false;
    public boolean level4Complete = false;
    public boolean level5Complete = false;

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

        openimage1.setVisible(level1Complete);
        openimage2.setVisible(level2Complete);
        openimage3.setVisible(level3Complete);
        openimage4.setVisible(level4Complete);
        openimage5.setVisible(level5Complete);

        image1.setVisible(!level1Complete);
        image2.setVisible(!level2Complete);
        image3.setVisible(!level3Complete);
        image4.setVisible(!level4Complete);
        image5.setVisible(!level5Complete);

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
        level1Complete = true;
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
        level2Complete = true;
        Character.positionX = character.getLayoutX();
        Character.positionY = character.getLayoutY();
    }
}



