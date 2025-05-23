package org.example.aptry2;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Beginner_Level2 extends Application {
    public static ArrayList<Platform> platformList = new ArrayList<>();
    public static final List<String> predefinedColourSequence = List.of("Red", "Orange", "Yellow", "Green", "Blue", "Black");
    public static int starCount = 0;
    public static boolean pass2 = false;

    ColourSequence beginnerSeq;
    PausePage pause;
    Button replayButton;
    Group root;
    LevelComplete lvlComplete;
    ImageView replayImage;
    Character character;

    public Beginner_Level2() {}

    @Override
    public void start(Stage stage) throws IOException {
        // Clear platformList so no duplicate platforms remain from previous runs
        platformList.clear();

        MediaPlayerSingleton.play("/Grasslands Theme.m4a");

        Image progressBg = new Image(getClass().getResourceAsStream("/image/Progress_Background.png"));
        Progress progress = new Progress(progressBg, predefinedColourSequence);

        character = new Character(ChooseCharacterController.selectedCharacter, false, true, progress, predefinedColourSequence);
        character.setPosition(0, 560);
        character.setSize(50, 100);
        character.setCharacterAppearance();
        character.stop = false;

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/image/Bg_Beginner.jpeg")));
        background.setFitHeight(700);
        background.setFitWidth(1100);

        // Create platforms and add them to platformList
        Platform platformRed = new Platform("Red", new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Red_1x6.png")), platformList);
        platformRed.setPlatformPosition(191, 506);
        platformRed.setSize(226, 38);

        Platform platformOrange = new Platform("Orange", new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Orange_1x3.png")), platformList);
        platformOrange.setPlatformPosition(380, 390);
        platformOrange.setSize(113, 38);

        Platform platformYellow = new Platform("Yellow", new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Yellow_1x7.png")), platformList);
        platformYellow.setPlatformPosition(532, 429);
        platformYellow.setSize(263, 38);

        Image platformGreenImg = new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Green_1x4.png"));
        Image platformPurpleImg = new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Purple_1x4.png"));
        Platform platformGreen = new Platform("Green", platformGreenImg, platformList);
        platformGreen.setPlatformPosition(797, 351);
        platformGreen.setSize(150, 38);

        Platform platformBlue = new Platform("Blue", new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Blue_1x6.png")), platformList);
        platformBlue.setPlatformPosition(494, 234);
        platformBlue.setSize(226, 38);

        Platform platformBlack = new Platform("Black", new Image(getClass().getResourceAsStream("/image/Platform_Beginner_Black_1x8.png")), platformList);
        platformBlack.setPlatformPosition(115, 195);
        platformBlack.setSize(301, 38);

        Image treasureBoxUnopen = new Image(getClass().getResourceAsStream("/image/TreasureBox_Beginner_Unopen.png"));
        Image treasureBoxOpen = new Image(getClass().getResourceAsStream("/image/TreasureBox_Beginner_Open.png"));
        TreasureBox treasureBox = new TreasureBox(treasureBoxUnopen, treasureBoxOpen);
        treasureBox.setSize(60, 50);
        treasureBox.setPosition(152, 195 - treasureBox.imageView.getFitHeight());

        Image starImage = new Image(getClass().getResourceAsStream("/image/Star.png"));
        Star star = new Star(starImage);
        star.setPosition(800, 280);
        star.setSize(60, 60);

        ImageView lvlCompleteBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_Bg.png")));
        ImageView lvlCompleteYellowBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_Bg_Yellow.png")));
        ImageView lvlCompleteTitle = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_title.png")));
        ImageView lvlCompleteStar1 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        ImageView lvlCompleteStar2 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        ImageView lvlCompleteStar3 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));

        Image seqBg = new Image(getClass().getResourceAsStream("/image/ColourSequence_Beginner.png"));
        beginnerSeq = new ColourSequence(seqBg, predefinedColourSequence);

        replayButton = new Button();
        replayImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Replay.png")));
        replayImage.setFitHeight(150);
        replayImage.setFitWidth(150);
        replayButton.setGraphic(replayImage);
        replayButton.setLayoutX(475);
        replayButton.setLayoutY(275);
        replayButton.setBackground(null);
        replayButton.setVisible(false);
        replayButton.setOnMouseClicked(e -> {
            character.stop = true;
            try {
                Stage stage1 = (Stage) replayButton.getScene().getWindow();
                Beginner_Level2 level2 = new Beginner_Level2();
                character.levelComplete = false;
                level2.start(stage1);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        });

        Button pauseButton = new Button();
        ImageView pauseImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Pause.png")));
        pauseImage.setFitHeight(50);
        pauseImage.setFitWidth(50);
        pauseButton.setGraphic(pauseImage);
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(20);
        pauseButton.setBackground(null);
        pauseButton.setOnMouseClicked(e -> {
            character.stop = true;
            try {
                if (!beginnerSeq.hintGroup.isVisible() && !lvlComplete.background.isVisible()) {
                    replayButton.setVisible(true);
                    pause = new PausePage(character, replayButton, replayImage, "Beginner");
                    root.getChildren().add(pause.pauseGroup);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create root group and add all nodes explicitly
        root = new Group();
        root.getChildren().addAll(
                background,
                platformRed.platformImage,
                platformOrange.platformImage,
                platformYellow.platformImage,
                platformGreen.platformImage,
                platformBlue.platformImage,
                platformBlack.platformImage,
                treasureBox.imageView,
                star.starImage,
                progress.background,
                progress.current,
                progress.red,
                progress.title,
                progress.orange,
                progress.yellow,
                progress.green,
                progress.blue,
                beginnerSeq.hintGroup,
                pauseButton,
                replayButton,
                lvlCompleteBackground,
                lvlCompleteYellowBackground,
                lvlCompleteTitle,
                lvlCompleteStar1,
                lvlCompleteStar2,
                lvlCompleteStar3,
                character.imageView // add character last so it draws above
        );

        lvlComplete = new LevelComplete(lvlCompleteBackground, lvlCompleteYellowBackground,
                lvlCompleteTitle, lvlCompleteStar1, lvlCompleteStar2, lvlCompleteStar3,
                character, replayImage, replayButton, "Beginner");
        root.getChildren().add(lvlComplete.completePageButton);

        Scene scene = new Scene(root, 1100, 700);

        // Attach key event handlers to scene for character movement
        scene.setOnKeyPressed(character::characterMove);
        scene.setOnKeyReleased(character::characterStop);

        // On mouse click, resume game and hide hint group
        scene.setOnMouseClicked(e -> {
            beginnerSeq.hintGroup.setVisible(false);
            character.stop = false;
        });

        // Disable focus on buttons so keys go to scene
        pauseButton.setFocusTraversable(false);
        replayButton.setFocusTraversable(false);

        stage.setTitle("Beginner Level 2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
