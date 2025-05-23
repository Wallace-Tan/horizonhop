package org.example.aptry2;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Advanced_Level1 extends Application {
    // List to hold platforms
    public static ArrayList<Platform> platformList = new ArrayList<>();
    // Positions for various colored platforms
    public static double redX;
    public static double redY;
    public static double orangeX;
    public static double orangeY;
    public static double yellowX;
    public static double yellowY;
    public static double greenX;
    public static double greenY;
    public static double blueX;
    public static double blueY;
    public static double blackX;
    public static double blackY;
    // Boolean flag for level pass
    public static boolean pass1 = false;
    // Sequence for advanced level colors
    public ColourSequence advancedSeq;
    // PausePage object for the pause screen
    private PausePage pause;
    // Replay button and its image
    private Button replayButton;
    ImageView replayImage;
    // Root group for all scene elements
    Group root = new Group();
    // Level complete screen
    LevelComplete lvlComplete;
    // Character object for the player
    Character character;

    // Method to setup the replay button
    private void setupReplayButton() throws FileNotFoundException {
        replayButton = new Button();
        replayImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Replay.png")));
        replayButton.setBackground(null);
        replayButton.setVisible(false);
        replayButton.setOnMouseClicked(e -> {
            character.stop = true;
            try {
                Stage stage1 = (Stage) replayButton.getScene().getWindow();
                Advanced_Level1_PlatformPlacement level1 = new Advanced_Level1_PlatformPlacement();
                character.levelComplete = false;
                level1.start(stage1);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        });
        root.getChildren().add(replayButton);
    }

    // Method to setup the pause button
    private void setupPauseButton() throws FileNotFoundException {
        Button pauseButton = new Button();
        ImageView pauseImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Pause.png")));
        pauseImage.setFitHeight(50);
        pauseImage.setFitWidth(50);
        pauseButton.setDefaultButton(false);
        pauseButton.setFocusTraversable(false);
        pauseButton.setGraphic(pauseImage);
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(20);
        pauseButton.setBackground(null);

        pauseButton.setOnMouseClicked(e -> {
            character.stop = true;
            try {
                if (!advancedSeq.hintGroup.isVisible() && !lvlComplete.background.isVisible()) {
                    replayButton.setVisible(true);
                    pause = new PausePage(character, replayButton, replayImage,"Advanced");
                    root.getChildren().add(pause.pauseGroup);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        root.getChildren().add(pauseButton);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Play background music
        MediaPlayerSingleton.play("/Grasslands Theme.m4a");

        // Create and set up the progress background
        Image progressBg = new Image(getClass().getResourceAsStream("/image/Progress_Background.png"));
        Progress progress = new Progress(progressBg, Advanced_Level1_PlatformPlacement.predefinedColourSequence);

        // Initialize the character
        character = new Character(ChooseCharacterController.selectedCharacter,true,false,progress, Advanced_Level1_PlatformPlacement.predefinedColourSequence);
        character.setPosition(1050,500);
        character.setSize(50,100);
        character.setCharacterAppearance();

        // Set up the grid image
        ImageView Grid = new ImageView(new Image(getClass().getResourceAsStream("/image/Grid.png")));
        Grid.setX(0);
        Grid.setY(0);
        Grid.setFitHeight(700);
        Grid.setFitWidth(1100);

        // Set up the background image
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/image/Bg_Advanced.png")));
        background.setX(0);
        background.setY(0);
        background.setFitHeight(700);
        background.setFitWidth(1100);

        // Set up platforms
        Image platformRedImg = new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Red_1x5.png"));
        Platform platformRed = new Platform("Red",platformRedImg,platformList);
        platformRed.setSize(189,38);
        platformRed.setPlatformPosition(redX,redY);

        Image platformOrangeImg = new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Orange_1x4.png"));
        Platform platformOrange = new Platform("Orange",platformOrangeImg,platformList);
        platformOrange.setSize(150,38);
        platformOrange.setPlatformPosition(orangeX,orangeY);

        Image platformYellowImg = new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Yellow_1x4.png"));
        Platform platformYellow = new Platform("Yellow",platformYellowImg,platformList);
        platformYellow.setSize(150,38);
        platformYellow.setPlatformPosition(yellowX,yellowY);

        Image platformGreenImg = new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Green_1x3.png"));
        Platform platformGreen = new Platform("Green",platformGreenImg,platformList);
        platformGreen.setSize(112,38);
        platformGreen.setPlatformPosition(greenX,greenY);

        Image platformBlueImg = new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Blue_1x5.png"));
        Platform platformBlue = new Platform("Blue",platformBlueImg,platformList);
        platformBlue.setSize(189,38);
        platformBlue.setPlatformPosition(blueX,blueY);

        Image platformBlackImg = new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Black_1x5.png"));
        Platform platformBlack = new Platform("Black",platformBlackImg,platformList);
        platformBlack.setPlatformPosition(blackX,blackY);
        platformBlack.setSize(189,38);

        // Set up the treasure box
        Image treasureBoxUnopen = new Image(getClass().getResourceAsStream("/image/TreasureBox_Advanced_Unopen.png"));
        Image treasureBoxOpen = new Image(getClass().getResourceAsStream("/image/TreasureBox_Advanced_Open.png"));
        TreasureBox treasureBox = new TreasureBox(treasureBoxUnopen,treasureBoxOpen);
        treasureBox.setSize(60,50);
        treasureBox.setPosition((blackX + 189/2 - 60),blackY-treasureBox.imageView.getFitHeight());

        // Set up the star
        Image starImage = new Image(getClass().getResourceAsStream("/image/Star.png"));
        Star star = new Star(starImage);
        star.setPosition(600, 200);
        star.setSize(60, 60);

        // Setup replay button
        setupReplayButton();

        // Setup level complete elements
        ImageView lvlCompleteBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_Bg.png")));
        ImageView lvlCompleteYellowBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_Bg_Yellow.png")));
        ImageView lvlCompleteTitle = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_title.png")));
        ImageView lvlCompleteStar1 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        ImageView lvlCompleteStar2 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        ImageView lvlCompleteStar3 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        lvlComplete = new LevelComplete(lvlCompleteBackground,lvlCompleteYellowBackground,lvlCompleteTitle,lvlCompleteStar1,lvlCompleteStar2,lvlCompleteStar3,character,replayImage,replayButton,"Advanced");

        // Setup color sequence
        Image seqBg = new Image(getClass().getResourceAsStream("/image/ColourSequence_Advanced.png"));
        advancedSeq = new ColourSequence(seqBg,Advanced_Level1_PlatformPlacement.predefinedColourSequence);

        // Add all elements to the root group
        root.getChildren().addAll(background,character.imageView,platformRed.platformImage,platformOrange.platformImage,platformYellow.platformImage,platformGreen.platformImage,platformBlue.platformImage,platformBlack.platformImage,treasureBox.imageView,star.starImage,progress.background,progress.current,progress.red,progress.title,progress.orange,progress.yellow,progress.green,progress.blue,lvlCompleteBackground,lvlCompleteYellowBackground,lvlCompleteTitle,lvlCompleteStar1,lvlCompleteStar2,lvlCompleteStar3,advancedSeq.hintGroup,lvlComplete.completePageButton);
        setupPauseButton();

        // Create the scene and set event handlers
        Scene scene = new Scene(root, 1100, 700);
        root.setOnMouseClicked(e->{
            advancedSeq.hintGroup.setVisible(false);
            character.stop = false;
        });
        scene.setOnKeyPressed(character::characterMove);
        scene.setOnKeyReleased(character::characterStop);
        stage.setTitle("Horizon Hop");
        stage.setScene(scene);
        stage.show();

        // Animation timer for updating the character's position and checking progress
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            if (!character.levelComplete && !character.stop) {
                character.updatePosition(Advanced_Level1_PlatformPlacement.predefinedColourSequence,platformList, treasureBox, Beginner_Level1.starCount);
                progress.currentProgressCheck();
                if (progress.platformCount < Advanced_Level1_PlatformPlacement.predefinedColourSequence.size()-1) {
                    progress.nextProgressCheck();
                } else {
                    progress.current.setImage(treasureBoxUnopen);
                }
                if (progress.isFail){
                    progress.fail();
                }
                if (!star.collected) {
                    star.isCollected(character.getCoordinates());
                }
            } else if (character.levelComplete) {
                Advanced_Level1.pass1 = true;
                lvlComplete.switchToLvlComplete(star.collected, !progress.isFail, character.levelComplete);
                this.stop();
            }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
