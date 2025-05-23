package org.example.aptry2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Beginner_Level1 extends Application {
    public static ArrayList<Platform> platformList = new ArrayList<>();
    public static final List<String> predefinedColourSequence = List.of("Red", "Orange", "Yellow", "Green", "Blue", "Black");
    public static int starCount = 0;
    public static boolean pass1 = false;

    private ColourSequence beginnerSeq;
    private PausePage pause;
    private Button replayButton;
    ImageView replayImage;
    private Group root;
    private LevelComplete lvlComplete;
    Progress progress;
    Character character;
    Star star;
    TreasureBox treasureBox;
    Image treasureBoxUnopen;
    MediaPlayerSingleton backgroundMusic = new MediaPlayerSingleton();

    public Beginner_Level1() throws FileNotFoundException {}

    @Override
    public void start(Stage stage) throws IOException {
        initializeGame(stage);
        AnimationTimer timer = createGameLoop(stage);
        timer.start();
    }

    private void initializeGame(Stage stage) throws IOException {
        MediaPlayerSingleton.play("/Grasslands Theme.m4a");
        root = new Group();

        setupBackground();
        setupProgress();
        setupCharacter();
        setupPlatforms();
        setupTreasureBox();
        setupStar();
        setupColourSequence();
        setupReplayButton();
        setupPauseButton();
        setupLevelComplete();

        Scene scene = createScene(stage);
        stage.setTitle("Horizon Hop");
        stage.setScene(scene);
        stage.show();
    }

    private void setupBackground() throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream("/image/Bg_Beginner.jpeg");
        if (is == null) {
            throw new FileNotFoundException("Could not find Bg_Beginner.jpeg");
        }
        Image image = new Image(is);
        ImageView background = new ImageView(image);

        background.setFitHeight(700);
        background.setFitWidth(1100);
        root.getChildren().add(background);
    }

    private void setupProgress() throws FileNotFoundException {
        Image progressBg = new Image(getClass().getResourceAsStream("/image/Progress_Background.png"));
        progress = new Progress(progressBg, predefinedColourSequence);
        root.getChildren().addAll(progress.background, progress.current, progress.red, progress.title, progress.orange, progress.yellow, progress.green, progress.blue);
    }

    private void setupCharacter() throws FileNotFoundException {
        character = new Character(ChooseCharacterController.selectedCharacter, false, true, progress, predefinedColourSequence);
        character.setPosition(0, 560);
        character.setSize(50, 100);
        character.setCharacterAppearance();
        root.getChildren().add(character.imageView);
    }

    private void setupPlatforms() throws FileNotFoundException {
        addPlatform("Red", "Platform_Beginner_Red_1x6.png", 191, 506, 226, 38);
        addPlatform("Orange", "Platform_Beginner_Orange_1x3.png", 380, 390, 113, 38);
        addPlatform("Yellow", "Platform_Beginner_Yellow_1x7.png", 532, 429, 263, 38);
        addPlatform("Green", "Platform_Beginner_Green_1x4.png", 797, 351, 150, 38);
        addPlatform("Blue", "Platform_Beginner_Blue_1x6.png", 494, 234, 226, 38);
        addPlatform("Black", "Platform_Beginner_Black_1x8.png", 115, 195, 301, 38);
    }

    private void addPlatform(String color, String fileName, double x, double y, double width, double height) throws FileNotFoundException {
        Image platformImg = new Image(getClass().getResourceAsStream("/image/" + fileName));
        Platform platform = new Platform(color, platformImg, platformList);
        platform.setPlatformPosition(x, y);
        platform.setSize(width, height);
        root.getChildren().add(platform.platformImage);
    }

    private void setupTreasureBox() throws FileNotFoundException {
        treasureBoxUnopen = new Image(getClass().getResourceAsStream("/image/TreasureBox_Beginner_Unopen.png"));
        Image treasureBoxOpen = new Image(getClass().getResourceAsStream("/image/TreasureBox_Beginner_Open.png"));
        treasureBox = new TreasureBox(treasureBoxUnopen, treasureBoxOpen);
        treasureBox.setSize(60, 50);
        treasureBox.setPosition(152, 195 - treasureBox.imageView.getFitHeight());
        root.getChildren().add(treasureBox.imageView);
    }

    private void setupStar() throws FileNotFoundException {
        Image starImage = new Image(getClass().getResourceAsStream("/image/Star.png"));
        star = new Star(starImage);
        star.setPosition(800, 280);
        star.setSize(60, 60);
        root.getChildren().add(star.starImage);
    }

    private void setupLevelComplete() throws FileNotFoundException {
        ImageView lvlCompleteBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_Bg.png")));
        ImageView lvlCompleteYellowBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_Bg_Yellow.png")));
        ImageView lvlCompleteTitle = new ImageView(new Image(getClass().getResourceAsStream("/image/LevelComplete_title.png")));
        ImageView lvlCompleteStar1 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        ImageView lvlCompleteStar2 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        ImageView lvlCompleteStar3 = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        lvlComplete = new LevelComplete(lvlCompleteBackground, lvlCompleteYellowBackground, lvlCompleteTitle, lvlCompleteStar1, lvlCompleteStar2, lvlCompleteStar3, character, replayImage, replayButton, "Beginner");
        root.getChildren().addAll(lvlCompleteBackground, lvlCompleteYellowBackground, lvlCompleteTitle, lvlCompleteStar1, lvlCompleteStar2, lvlCompleteStar3, lvlComplete.completePageButton);
    }

    private void setupColourSequence() throws FileNotFoundException {
        Image seqBg = new Image(getClass().getResourceAsStream("/image/ColourSequence_Beginner.png"));
        beginnerSeq = new ColourSequence(seqBg, predefinedColourSequence);
        root.getChildren().add(beginnerSeq.hintGroup);
    }

    private void setupReplayButton() throws FileNotFoundException {
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
                Beginner_Level1 level1 = new Beginner_Level1();
                character.levelComplete = false;
                level1.start(stage1);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        });
        root.getChildren().add(replayButton);
    }

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
                if (!beginnerSeq.hintGroup.isVisible() && !lvlComplete.background.isVisible()) {
                    replayButton.setVisible(true);
                    pause = new PausePage(character, replayButton, replayImage, "Beginner");
                    root.getChildren().add(pause.pauseGroup);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        root.getChildren().add(pauseButton);
    }

    private Scene createScene(Stage stage) {
        Scene scene = new Scene(root, 1100, 700);
        scene.setOnKeyPressed(character::characterMove);
        scene.setOnKeyReleased(character::characterStop);
        scene.setOnMouseClicked(e -> {
            beginnerSeq.hintGroup.setVisible(false);
            character.stop = false;
        });
        return scene;
    }

    private AnimationTimer createGameLoop(Stage stage) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!character.levelComplete && !character.stop) {
                    character.updatePosition(predefinedColourSequence, platformList, treasureBox, Beginner_Level1.starCount);
                    progress.currentProgressCheck();
                    if (progress.platformCount < predefinedColourSequence.size() - 1) {
                        progress.nextProgressCheck();
                    } else {
                        progress.current.setImage(treasureBoxUnopen);
                    }
                    if (progress.isFail) {
                        progress.fail();
                    }
                    if (!star.collected) {
                        star.isCollected(character.getCoordinates());
                    }
                } else if (character.levelComplete) {
                    lvlComplete.switchToLvlComplete(star.collected, !progress.isFail, character.levelComplete);
                    Beginner_Level1.pass1 = true;
                    this.stop();
                }
            }
        };
    }

    public static void main(String[] args) {
        launch();
    }
}
