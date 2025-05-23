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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Advanced_Level1_PlatformPlacement extends Application {
    private ImageView draggedPlatform = null; // Holds the platform currently being dragged
    private double offsetX; // X offset for dragging
    private double offsetY; // Y offset for dragging
    private double originalX; // Original X position before dragging
    private double originalY; // Original Y position before dragging
    private static final double GRID_X = 110; // X position of the grid
    private static final double GRID_Y = 20; // Y position of the grid
    private static final double GRID_WIDTH = 880; // Width of the grid
    private static final double GRID_HEIGHT = 560; // Height of the grid
    private static final double GRID_CELL_WIDTH = 29.33; // Width of each grid cell
    private static final double GRID_CELL_HEIGHT = 28.4; // Height of each grid cell
    private static final double GRID_LINE_THICKNESS = 1; // Thickness of grid lines
    private ImageView characterStanding; // Character image
    private ImageView star; // Star image
    private ImageView treasureBox; // Treasure box image
    private ImageView platformBlackImg; // Black platform image
    private ImageView platformBlueImg; // Blue platform image
    private ImageView platformGreenImg; // Green platform image
    private ImageView platformYellowImg; // Yellow platform image
    private ImageView platformOrangeImg; // Orange platform image
    private ImageView platformRedImg; // Red platform image
    public static final List<String> predefinedColourSequence = List.of("Yellow", "Green", "Red", "Blue", "Orange", "Black"); // Predefined color sequence for platforms

    @Override
    public void start(Stage stage) throws IOException {
        MediaPlayerSingleton.play("/platformPlacement.m4a"); // Play background music

        // Set up the background images
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/image/Bg_Advanced.png")));
        background.setX(GRID_X);
        background.setY(GRID_Y);
        background.setFitHeight(GRID_HEIGHT);
        background.setFitWidth(GRID_WIDTH);

        ImageView blackBackground = new ImageView(new Image(getClass().getResourceAsStream("/image/Bg_PlatformPlacement.png")));
        blackBackground.setX(0);
        blackBackground.setY(0);
        blackBackground.setFitHeight(700);
        blackBackground.setFitWidth(1100);

        Group gridGroup = createGrid(); // Create the grid for placing platforms

        // Set the character image based on selection
        if (ChooseCharacterController.selectedCharacter.equals("Bob")) {
            characterStanding = new ImageView(new Image(getClass().getResourceAsStream("/image/Bob_BackwardStanding.png")));
        } else if (ChooseCharacterController.selectedCharacter.equals("Alice")) {
            characterStanding = new ImageView(new Image(getClass().getResourceAsStream("/image/Alice_BackwardStanding.png")));
        }
        characterStanding.setX(950);
        characterStanding.setY(480);
        characterStanding.setFitHeight(80);
        characterStanding.setFitWidth(40);

        // Initialize platform images
        platformRedImg = initPlatform(new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Red_1x5.png"))), 110, 620, 151.2, 30.4);
        platformOrangeImg = initPlatform(new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Orange_1x4.png"))), 275.95, 620, 120, 30.4);
        platformYellowImg = initPlatform(new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Yellow_1x4.png"))), 410.7, 620, 120, 30.4);
        platformGreenImg = initPlatform(new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Green_1x3.png"))), 545.45, 620, 89.6, 30.4);
        platformBlueImg = initPlatform(new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Blue_1x5.png"))), 649.8, 620, 151.2, 30.4);
        platformBlackImg = new ImageView(new Image(getClass().getResourceAsStream("/image/Platform_Advanced_Black_1x5.png")));
        platformBlackImg.setX(384);
        platformBlackImg.setY(433);
        platformBlackImg.setFitWidth(151.2);
        platformBlackImg.setFitHeight(31.2);

        // Initialize the treasure box
        treasureBox = new ImageView(new Image(getClass().getResourceAsStream("/image/TreasureBox_Advanced_Unopen.png")));
        treasureBox.setFitWidth(48);
        treasureBox.setFitHeight(40);
        treasureBox.setX(450);
        treasureBox.setY(428 - treasureBox.getFitHeight());

        // Initialize the star
        star = new ImageView(new Image(getClass().getResourceAsStream("/image/Star.png")));
        star.setX(585);
        star.setY(180);
        star.setFitWidth(48);
        star.setFitHeight(48);

        // Set up the done button
        Button doneButton = new Button();
        ImageView doneImage = new ImageView(new Image(getClass().getResourceAsStream("/image/Button_Done.png")));
        doneImage.setFitHeight(50);
        doneImage.setFitWidth(130);
        doneButton.setGraphic(doneImage);
        doneButton.setLayoutX(815.75);
        doneButton.setLayoutY(605);
        doneButton.setBackground(null);

        // Set up the hint background
        ImageView hintBg = new ImageView(new Image(getClass().getResourceAsStream("/image/SequenceHint_Advanced.png")));
        hintBg.setX(150);
        hintBg.setY(165);
        hintBg.setFitWidth(800);
        hintBg.setFitHeight(370);

        // Create the root group and add all elements
        Group root = new Group(blackBackground, background, characterStanding, platformRedImg, platformOrangeImg, platformYellowImg, platformGreenImg, platformBlueImg, platformBlackImg, treasureBox, star, doneButton, gridGroup, hintBg);

        // Set up the scene and event handlers
        Scene scene = new Scene(root, 1100, 700);
        scene.setOnMouseClicked(e -> {
            try {
                platformRedImg = createDraggablePlatform(platformRedImg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                platformOrangeImg = createDraggablePlatform(platformOrangeImg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                platformYellowImg = createDraggablePlatform(platformYellowImg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                platformGreenImg = createDraggablePlatform(platformGreenImg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                platformBlueImg = createDraggablePlatform(platformBlueImg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            hintBg.setVisible(false); // Hide hint background when a platform is clicked
            doneButton.setOnMouseClicked(event -> {
                // Ensure all platforms are placed before proceeding
                if (platformRedImg.getY() != 620 && platformOrangeImg.getY() != 620 && platformYellowImg.getY() != 620 && platformGreenImg.getY() != 620 && platformBlueImg.getY() != 620) {
                    // Save platform positions
                    Advanced_Level1.redX = (platformRedImg.getX() - 110) / 0.8;
                    Advanced_Level1.orangeX = (platformOrangeImg.getX() - 110) / 0.8;
                    Advanced_Level1.yellowX = (platformYellowImg.getX() - 110) / 0.8;
                    Advanced_Level1.greenX = (platformGreenImg.getX() - 110) / 0.8;
                    Advanced_Level1.blueX = (platformBlueImg.getX() - 110) / 0.8;
                    Advanced_Level1.blackX = (platformBlackImg.getX() - 110) / 0.8;
                    Advanced_Level1.redY = (platformRedImg.getY() - 20) / 0.8;
                    Advanced_Level1.orangeY = (platformOrangeImg.getY() - 20) / 0.8;
                    Advanced_Level1.yellowY = (platformYellowImg.getY() - 20) / 0.8;
                    Advanced_Level1.greenY = (platformGreenImg.getY() - 20) / 0.8;
                    Advanced_Level1.blueY = (platformBlueImg.getY() - 20) / 0.8;
                    Advanced_Level1.blackY = (platformBlackImg.getY() - 20) / 0.8;
                    try {
                        // Start the advanced level with platform positions saved
                        Advanced_Level1 intermediateLevel1 = new Advanced_Level1();
                        intermediateLevel1.start(stage);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        });

        stage.setTitle("Try Run");
        stage.setScene(scene);
        stage.show();
    }

    // Method to create the grid lines
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

    // Method to initialize platform properties
    private ImageView initPlatform(ImageView platform, double x, double y, double width, double height) {
        platform.setX(x);
        platform.setY(y);
        platform.setFitWidth(width);
        platform.setFitHeight(height);
        return platform;
    }

    // Method to make platforms draggable
    private ImageView createDraggablePlatform(ImageView platform) throws IOException {
        platform.setOnMousePressed(e -> {
            draggedPlatform = platform;
            offsetX = e.getSceneX() - platform.getX();
            offsetY = e.getSceneY() - platform.getY();
            originalX = platform.getX();
            originalY = platform.getY();
            platform.toFront(); // Bring the platform to the front while dragging
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
                    // If there is a collision, reset to the original position
                    draggedPlatform.setX(originalX);
                    draggedPlatform.setY(originalY);
                } else {
                    snapToGrid(draggedPlatform); // Snap the platform to the nearest grid position
                }
                draggedPlatform = null;
            }
        });

        return platform;
    }

    // Method to snap a platform to the nearest grid position
    private void snapToGrid(ImageView platform) {
        double x = platform.getX();
        double y = platform.getY();

        // Calculate the nearest grid position
        double newX = GRID_X + Math.round((x - GRID_X) / (GRID_CELL_WIDTH + GRID_LINE_THICKNESS)) * (GRID_CELL_WIDTH + GRID_LINE_THICKNESS);
        double newY = GRID_Y + Math.round((y - GRID_Y) / (GRID_CELL_HEIGHT + GRID_LINE_THICKNESS)) * (GRID_CELL_HEIGHT + GRID_LINE_THICKNESS);

        platform.setX(newX);
        platform.setY(newY);
    }

    // Method to check for collision with other platforms, the character, or the treasure box
    private boolean isCollision(ImageView platform) {
        for (ImageView other : new ImageView[]{characterStanding, star, treasureBox, platformBlackImg, platformBlueImg, platformGreenImg, platformYellowImg, platformOrangeImg, platformRedImg}) {
            if (other != platform && platform.getBoundsInParent().intersects(other.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
