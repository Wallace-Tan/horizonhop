package org.example.aptry2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Intermediate_Level1_PlatformPlacement extends Application {
    private ImageView draggedPlatform = null;
    private double offsetX;
    private double offsetY;
    private double originalX;
    private double originalY;
    private static final double GRID_X = 110;
    private static final double GRID_Y = 20;
    private static final double GRID_WIDTH = 880;
    private static final double GRID_HEIGHT = 560;
    private static final double GRID_CELL_WIDTH = 29.33;
    private static final double GRID_CELL_HEIGHT = 28.4;
    private static final double GRID_LINE_THICKNESS = 1;
    private ImageView characterBackwardStanding;
    private ImageView star;
    private ImageView treasureBox;
    private ImageView platformBlackImg;
    private ImageView platformBlueImg;
    private ImageView platformGreenImg;
    private ImageView platformYellowImg;
    private ImageView platformOrangeImg;
    private ImageView platformRedImg;
    public static final List<String> predefinedColourSequence = getShuffledColourSequence();
    private Hint intermediateHint;

    @Override
    public void start(Stage stage) throws IOException {
        MediaPlayerSingleton.play("/platformPlacement.m4a");

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/image/Bg_Intermediate.png")));
        background.setX(GRID_X);
        background.setY(GRID_Y);
        background.setFitHeight(GRID_HEIGHT);
        background.setFitWidth(GRID_WIDTH);

        ImageView blackBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/Bg_PlatformPlacement.png")));
        blackBackground.setX(0);
        blackBackground.setY(0);
        blackBackground.setFitHeight(700);
        blackBackground.setFitWidth(1100);

        Group gridGroup = createGrid();

        if (ChooseCharacterController.selectedCharacter.equals("Bob")) {
            characterBackwardStanding = new ImageView(new Image(getClass().getResourceAsStream("/image/Bob_BackwardStanding.png")));
        } else if (ChooseCharacterController.selectedCharacter.equals("Alice")) {
            characterBackwardStanding = new ImageView(new Image(getClass().getResourceAsStream("/image/Alice_BackwardStanding.png")));
        }
        characterBackwardStanding.setX(950);
        characterBackwardStanding.setY(480);
        characterBackwardStanding.setFitHeight(80);
        characterBackwardStanding.setFitWidth(40);

        platformRedImg = createDraggablePlatform("/image/Platform_Intermediate_Red_1x5.png", 110, 620, 151.2, 30.4);
        platformOrangeImg = createDraggablePlatform("/image/Platform_Intermediate_Orange_1x4.png", 275.95, 620, 120, 30.4);
        platformYellowImg = createDraggablePlatform("/image/Platform_Intermediate_Yellow_1x4.png", 410.7, 620, 120, 30.4);
        platformGreenImg = createDraggablePlatform("/image/Platform_Intermediate_Green_1x3.png", 545.45, 620, 89.6, 30.4);
        platformBlueImg = createDraggablePlatform("/image/Platform_Intermediate_Blue_1x5.png", 649.8, 620, 151.2, 30.4);

        platformBlackImg = new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Intermediate_Black_1x5.png")));
        platformBlackImg.setX(384);
        platformBlackImg.setY(433);
        platformBlackImg.setFitWidth(151.2);
        platformBlackImg.setFitHeight(31.2);

        treasureBox = new ImageView(new Image(getClass().getResourceAsStream("/image/TreasureBox_Intermediate_Unopen.png")));
        treasureBox.setFitWidth(48);
        treasureBox.setFitHeight(40);
        treasureBox.setX(450);
        treasureBox.setY(428 - treasureBox.getFitHeight());

        star = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        star.setX(585);
        star.setY(180);
        star.setFitWidth(48);
        star.setFitHeight(48);

        Button doneButton = new Button();
        ImageView doneImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Done.png")));
        doneImage.setFitHeight(50);
        doneImage.setFitWidth(130);
        doneButton.setGraphic(doneImage);
        doneButton.setLayoutX(815.75);
        doneButton.setLayoutY(605);
        doneButton.setBackground(null);

        Image hintBg = new Image(getClass().getResourceAsStream("/image/SequenceHint_Intermediate.png"));
        intermediateHint = new Hint(hintBg, predefinedColourSequence);

        Group root = new Group(
                blackBackground,
                background,
                characterBackwardStanding,
                platformRedImg,
                platformOrangeImg,
                platformYellowImg,
                platformGreenImg,
                platformBlueImg,
                platformBlackImg,
                treasureBox,
                star,
                doneButton,
                gridGroup,
                intermediateHint.hintGroup
        );

        Scene scene = new Scene(root, 1100, 700);

        scene.setOnMouseClicked(e -> {
            intermediateHint.hintGroup.setVisible(false);
            doneButton.setOnMouseClicked(event -> {
                if (platformRedImg.getY() != 620 && platformOrangeImg.getY() != 620 && platformYellowImg.getY() != 620 && platformGreenImg.getY() != 620 && platformBlueImg.getY() != 620) {
                    Intermediate_Level1.redX = (platformRedImg.getX() - 110) / 0.8;
                    Intermediate_Level1.orangeX = (platformOrangeImg.getX() - 110) / 0.8;
                    Intermediate_Level1.yellowX = (platformYellowImg.getX() - 110) / 0.8;
                    Intermediate_Level1.greenX = (platformGreenImg.getX() - 110) / 0.8;
                    Intermediate_Level1.blueX = (platformBlueImg.getX() - 110) / 0.8;
                    Intermediate_Level1.blackX = (platformBlackImg.getX() - 110) / 0.8;
                    Intermediate_Level1.redY = (platformRedImg.getY() - 20) / 0.8;
                    Intermediate_Level1.orangeY = (platformOrangeImg.getY() - 20) / 0.8;
                    Intermediate_Level1.yellowY = (platformYellowImg.getY() - 20) / 0.8;
                    Intermediate_Level1.greenY = (platformGreenImg.getY() - 20) / 0.8;
                    Intermediate_Level1.blueY = (platformBlueImg.getY() - 20) / 0.8;
                    Intermediate_Level1.blackY = (platformBlackImg.getY() - 20) / 0.8;
                    try {
                        Intermediate_Level1 intermediateLevel1 = new Intermediate_Level1();
                        intermediateLevel1.start(stage);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        });

        stage.setTitle("Horizon Hop");
        stage.setScene(scene);
        stage.show();
    }

    private Group createGrid() {
        Group gridGroup = new Group();
        for (double x = GRID_X; x <= GRID_X + GRID_WIDTH; x += GRID_CELL_WIDTH + GRID_LINE_THICKNESS) {
            Line verticalLine = new Line(x, GRID_Y, x, GRID_Y + GRID_HEIGHT);
            verticalLine.setStroke(Color.WHITE);
            verticalLine.setStrokeWidth(GRID_LINE_THICKNESS);
            gridGroup.getChildren().add(verticalLine);
        }
        for (double y = GRID_Y; y <= GRID_Y + GRID_HEIGHT; y += GRID_CELL_HEIGHT + GRID_LINE_THICKNESS) {
            Line horizontalLine = new Line(GRID_X, y, GRID_X + GRID_WIDTH, y);
            horizontalLine.setStroke(Color.WHITE);
            horizontalLine.setStrokeWidth(GRID_LINE_THICKNESS);
            gridGroup.getChildren().add(horizontalLine);
        }
        return gridGroup;
    }

    private ImageView createDraggablePlatform(String imagePath, double x, double y, double width, double height) {
        ImageView platform = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        platform.setX(x);
        platform.setY(y);
        platform.setFitWidth(width);
        platform.setFitHeight(height);

        platform.setOnMousePressed(e -> {
            draggedPlatform = platform;
            offsetX = e.getSceneX() - platform.getX();
            offsetY = e.getSceneY() - platform.getY();
            originalX = platform.getX();
            originalY = platform.getY();
            platform.toFront();
        });

        platform.setOnMouseDragged(e -> {
            if (draggedPlatform != null) {
                double newX = e.getSceneX() - offsetX;
                double newY = e.getSceneY() - offsetY;

                // Constrain the platform within the grid area
                if (newX >= GRID_X && newX + platform.getFitWidth() <= GRID_X + GRID_WIDTH) {
                    platform.setX(newX);
                }
                if (newY >= GRID_Y && newY + platform.getFitHeight() <= GRID_Y + GRID_HEIGHT) {
                    platform.setY(newY);
                }
                platform.toFront(); // Ensure the platform is always on top while dragging
            }
        });

        platform.setOnMouseReleased(e -> {
            if (draggedPlatform != null) {
                if (isCollision(draggedPlatform)) {
                    draggedPlatform.setX(originalX);
                    draggedPlatform.setY(originalY);
                } else {
                    snapToGrid(draggedPlatform);
                }
                draggedPlatform = null;
            }
        });

        return platform;
    }

    private void snapToGrid(ImageView platform) {
        double x = platform.getX();
        double y = platform.getY();

        // Calculate the nearest grid position
        double newX = GRID_X + Math.round((x - GRID_X) / (GRID_CELL_WIDTH + GRID_LINE_THICKNESS)) * (GRID_CELL_WIDTH + GRID_LINE_THICKNESS);
        double newY = GRID_Y + Math.round((y - GRID_Y) / (GRID_CELL_HEIGHT + GRID_LINE_THICKNESS)) * (GRID_CELL_HEIGHT + GRID_LINE_THICKNESS);

        platform.setX(newX);
        platform.setY(newY);
    }

    private boolean isCollision(ImageView platform) {
        // Check for collision with other platforms, the character, and the treasure box
        for (ImageView other : new ImageView[]{characterBackwardStanding, star, treasureBox, platformBlackImg,
                platformBlueImg, platformGreenImg, platformYellowImg, platformOrangeImg, platformRedImg}) {
            if (other != platform && platform.getBoundsInParent().intersects(other.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getShuffledColourSequence() {
        List<String> colourSequence = new ArrayList<>();
        colourSequence.add("Red");
        colourSequence.add("Orange");
        colourSequence.add("Yellow");
        colourSequence.add("Green");
        colourSequence.add("Blue");
        Collections.shuffle(colourSequence);
        return colourSequence;
    }
}
