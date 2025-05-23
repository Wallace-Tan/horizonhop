package org.example.aptry2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;

public class tryRun extends Application {

    private static final int WINDOW_WIDTH = 1100;
    private static final int WINDOW_HEIGHT = 700;
    private static final int EDITOR_WIDTH = 990; // 80% of window width
    private static final int EDITOR_HEIGHT = 700; // 80% of window height (700 * 0.8)
    private static final int GAME_PLAY_WIDTH = 1100;
    private static final int GAME_HEIGHT = 700;
    private static final int GRID_SIZE = 25;
    private static final int PLATFORM_WIDTH = 4 * GRID_SIZE;
    private static final int PLATFORM_HEIGHT = GRID_SIZE;
    private static final int CHARACTER_SIZE = 2 * GRID_SIZE;
    private static final double SCALE_FACTOR = 0.8;
    private static final int AVAILABLE_PLATFORMS_HEIGHT = 100;
    private static final int AVAILABLE_PLATFORMS_Y = EDITOR_HEIGHT;


    private Canvas canvas;
    private GraphicsContext gc;
    private Pane root;
    private Pane editorPane;
    private Image backgroundImage;
    private Scale editScale;

    private List<Platform> placedPlatforms = new ArrayList<>();
    private List<Platform> availablePlatforms = new ArrayList<>();
    private Character character;
    private Rectangle goal;
    private Platform immovablePlatform;

    private boolean isPlacingPhase = true;
    private AnimationTimer gameLoop;
    private Platform draggedPlatform = null;
    private Platform selectedPlatform = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        editorPane = new Pane();

        canvas = new Canvas(EDITOR_WIDTH, EDITOR_HEIGHT + AVAILABLE_PLATFORMS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        editorPane.getChildren().add(canvas);

        // Center the editorPane in the root
        editorPane.setLayoutX((WINDOW_WIDTH - EDITOR_WIDTH) / 2);
        editorPane.setLayoutY((WINDOW_HEIGHT - EDITOR_HEIGHT) / 2);

        root.getChildren().add(editorPane);

        //Load background image
        backgroundImage = new Image(getClass().getResourceAsStream("/image/Bg_Intermediate.png"));

        createAvailablePlatforms();
        createGoal();
        createCharacter();
        createDoneButton();
        createResetButton();

        // Scaling for editing mode
        editScale = new Scale(SCALE_FACTOR, SCALE_FACTOR);
        editorPane.getTransforms().add(editScale);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        setupInputHandlers(scene);

        primaryStage.setTitle("Horizon Hop");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPlacingPhase) {
                    character.update(placedPlatforms);
                }
                update(primaryStage);
                render();
            }
        };
        gameLoop.start();
    }

    private double[] sceneToEditorCoords(double sceneX, double sceneY) {
        double editorX = (sceneX - editorPane.getLayoutX()) / SCALE_FACTOR;
        double editorY = (sceneY - editorPane.getLayoutY()) / SCALE_FACTOR;
        return new double[]{editorX, editorY};
    }

    private void createAvailablePlatforms() {
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};
        int platformSpacing = (EDITOR_WIDTH - (colors.length * PLATFORM_WIDTH)) / (colors.length + 1);

        for (int i = 0; i < colors.length; i++) {
            double x = (i + 1) * platformSpacing + i * PLATFORM_WIDTH;
            double y = AVAILABLE_PLATFORMS_Y + (AVAILABLE_PLATFORMS_HEIGHT - PLATFORM_HEIGHT) / 2;
            Platform platform = new Platform(x, y, colors[i]);
            availablePlatforms.add(platform);
        }
    }

    private void createGoal() {
        goal = new Rectangle(2 * GRID_SIZE, 2 * GRID_SIZE);
        goal.setFill(Color.GOLD);
        goal.setX(EDITOR_WIDTH / 2 - GRID_SIZE);
        goal.setY(2 * GRID_SIZE);
        editorPane.getChildren().add(goal);

        // Create an immovable platform directly below the goal
        immovablePlatform = new Platform(goal.getX(), goal.getY() + goal.getHeight(), Color.BROWN);
        immovablePlatform.setImmovable(true);
        placedPlatforms.add(immovablePlatform);
    }

    private void createCharacter() {
        character = new Character(EDITOR_WIDTH / 2 - CHARACTER_SIZE / 2, EDITOR_HEIGHT - CHARACTER_SIZE);
    }

    private void createDoneButton() {
        Button doneButton = new Button("DONE");
        doneButton.setLayoutX(EDITOR_WIDTH - 70);
        doneButton.setLayoutY(EDITOR_HEIGHT - 40);
        doneButton.setOnAction(e -> switchToGameMode());
        editorPane.getChildren().add(doneButton);
    }

    private void createResetButton() {
        Button resetButton = new Button("Reset");
        resetButton.setLayoutX(EDITOR_WIDTH - 140);
        resetButton.setLayoutY(EDITOR_HEIGHT - 40);
        resetButton.setOnAction(e -> resetGame((Stage) root.getScene().getWindow()));
        editorPane.getChildren().add(resetButton);
    }
    private void scaleGameElements() {
        double scaleX = (double) GAME_PLAY_WIDTH / EDITOR_WIDTH;
        double scaleY = (double) GAME_HEIGHT / EDITOR_HEIGHT;

        // Scale goal
        goal.setX(goal.getX() * scaleX);
        goal.setY(goal.getY() * scaleY);
        goal.setWidth(goal.getWidth() * scaleX);
        goal.setHeight(goal.getHeight() * scaleY);

        // Scale platforms
        for (Platform platform : placedPlatforms) {
            platform.x *= scaleX;
            platform.y *= scaleY;
        }

        // Scale character
        character.x *= scaleX;
        character.y *= scaleY;
    }

    private void switchToGameMode() {
        isPlacingPhase = false;

        // Create a new Pane for the game mode
        Pane gameRoot = new Pane();

        // Create a new Canvas for the game mode
        Canvas gameCanvas = new Canvas(GAME_PLAY_WIDTH, GAME_HEIGHT);
        gc = gameCanvas.getGraphicsContext2D();
        gameRoot.getChildren().add(gameCanvas);

        scaleGameElements();

        // Add the goal and platforms to the new Pane
        gameRoot.getChildren().add(goal);
        for (Platform platform : placedPlatforms) {
            Rectangle platformRect = new Rectangle(platform.x, platform.y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
            platformRect.setFill(platform.color);
            gameRoot.getChildren().add(platformRect);
        }

        // Reset character position
        character = new Character(GAME_PLAY_WIDTH / 2 - CHARACTER_SIZE / 2, GAME_HEIGHT - CHARACTER_SIZE - 50);
        // Add Retry button
        Button retryButton = new Button("Retry");
        retryButton.setLayoutX(WINDOW_WIDTH - 70);
        retryButton.setLayoutY(10);
        retryButton.setOnAction(e -> returnToEditorMode((Stage) gameRoot.getScene().getWindow()));
        gameRoot.getChildren().add(retryButton);

        availablePlatforms.clear(); // Clear the available platforms

        Scene gameScene = new Scene(gameRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(gameScene);
        setupInputHandlers(gameScene);

        // Add this line to ensure the scene can receive key events
        gameScene.getRoot().requestFocus();

        // Update the root and canvas references
        root = gameRoot;
        canvas = gameCanvas;
    }

    private void setupInputHandlers(Scene scene) {
        scene.setOnMousePressed(e -> {
            if (isPlacingPhase) {
                double[] editorCoords = sceneToEditorCoords(e.getSceneX(), e.getSceneY());
                double editorX = editorCoords[0];
                double editorY = editorCoords[1];

                for (Platform platform : availablePlatforms) {
                    if (platform.contains(editorX, editorY)) {
                        draggedPlatform = platform;
                        break;
                    }
                }
                if (draggedPlatform == null) {
                    for (Platform platform : placedPlatforms) {
                        if (!platform.isImmovable() && platform.contains(editorX, editorY)) {
                            draggedPlatform = platform;
                            selectedPlatform = platform;
                            placedPlatforms.remove(platform);
                            break;
                        }
                    }
                }
            }
        });

        scene.setOnMouseDragged(e -> {
            if (isPlacingPhase && draggedPlatform != null) {
                double[] editorCoords = sceneToEditorCoords(e.getSceneX(), e.getSceneY());
                double editorX = editorCoords[0];
                double editorY = editorCoords[1];

                // Constrain the dragged platform to the editor area
                editorY = Math.min(Math.max(editorY, 0), EDITOR_HEIGHT - PLATFORM_HEIGHT);

                draggedPlatform.setX(editorX - PLATFORM_WIDTH / 2);
                draggedPlatform.setY(editorY - PLATFORM_HEIGHT / 2);
            }
        });

        scene.setOnMouseReleased(e -> {
            if (isPlacingPhase && draggedPlatform != null) {
                double[] editorCoords = sceneToEditorCoords(e.getSceneX(), e.getSceneY());
                double editorX = editorCoords[0];
                double editorY = editorCoords[1];

                draggedPlatform.setX(editorX - PLATFORM_WIDTH / 2);
                draggedPlatform.setY(editorY - PLATFORM_HEIGHT / 2);

                if (!isOverlapping(draggedPlatform)) {
                    draggedPlatform.snapToGrid();
                    placedPlatforms.add(draggedPlatform);
                    availablePlatforms.remove(draggedPlatform);
                    selectedPlatform = null;
                } else {
                    draggedPlatform.resetPosition();
                    if (selectedPlatform != null) {
                        placedPlatforms.add(selectedPlatform);
                        selectedPlatform = null;
                    }
                }
                draggedPlatform = null;
            }
        });
    }

    private boolean isOverlapping(Platform platform) {
        for (Platform placed : placedPlatforms) {
            if (placed.intersects(platform)) {
                return true;
            }
        }
        return false;
    }

    private void returnToEditorMode(Stage primaryStage) {
        resetGame(primaryStage);
    }

    private void update(Stage primaryStage) {
        if (!isPlacingPhase) {
            character.update(placedPlatforms);

            // Keep character within game boundaries
            character.x = Math.max(0, Math.min(character.x, GAME_PLAY_WIDTH - CHARACTER_SIZE));
            character.y = Math.max(0, Math.min(character.y, GAME_HEIGHT - CHARACTER_SIZE));

            if (character.intersects(goal)) {
                gameLoop.stop();
            }
        }
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (isPlacingPhase) {
            // Draw background image for editing mode
            gc.drawImage(backgroundImage, 0, 0, EDITOR_WIDTH, EDITOR_HEIGHT);

            // Draw available platforms area
            gc.setFill(Color.LIGHTGRAY);
            gc.fillRect(0, EDITOR_HEIGHT, EDITOR_WIDTH, AVAILABLE_PLATFORMS_HEIGHT);

            // Draw grid in editor mode
            gc.setStroke(Color.GRAY);
            for (int i = 0; i <= EDITOR_WIDTH; i += GRID_SIZE) {
                gc.strokeLine(i, 0, i, EDITOR_HEIGHT);
            }
            for (int i = 0; i <= EDITOR_HEIGHT; i += GRID_SIZE) {
                gc.strokeLine(0, i, EDITOR_WIDTH, i);
            }

            // Draw placed platforms
            for (Platform platform : placedPlatforms) {
                platform.draw(gc);
            }

            // Draw available platforms
            for (Platform platform : availablePlatforms) {
                platform.draw(gc);
            }

            // Draw goal
            gc.setFill(Color.GOLD);
            gc.fillRect(goal.getX(), goal.getY(), goal.getWidth(), goal.getHeight());

            // Draw dragged platform
            if (draggedPlatform != null) {
                draggedPlatform.draw(gc);
            }
        } else {
            // Game mode rendering
            // Draw background image for game mode
            gc.drawImage(backgroundImage, 0, 0, GAME_PLAY_WIDTH, GAME_HEIGHT);

            // Draw placed platforms
            for (Platform platform : placedPlatforms) {
                platform.draw(gc);
            }

            // Draw goal
            gc.setFill(Color.GOLD);
            gc.fillRect(goal.getX(), goal.getY(), goal.getWidth(), goal.getHeight());

            // Draw character
            character.draw(gc);
        }
    }

    private void resetGame(Stage primaryStage) {
        root = new Pane();
        editorPane = new Pane();

        canvas = new Canvas(EDITOR_WIDTH, EDITOR_HEIGHT + AVAILABLE_PLATFORMS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        editorPane.getChildren().add(canvas);

        // Center the editorPane in the root
        editorPane.setLayoutX((WINDOW_WIDTH - EDITOR_WIDTH) / 2);
        editorPane.setLayoutY((WINDOW_HEIGHT - EDITOR_HEIGHT) / 2);

        root.getChildren().add(editorPane);

        placedPlatforms.clear();
        availablePlatforms.clear();
        createAvailablePlatforms();
        createGoal();
        createCharacter();
        createDoneButton();
        createResetButton();

        // Apply scaling to editorPane
        editScale = new Scale(SCALE_FACTOR, SCALE_FACTOR);
        editorPane.getTransforms().add(editScale);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        setupInputHandlers(scene);

        primaryStage.setScene(scene);

        isPlacingPhase = true;
        gameLoop.start();
    }

    private class Platform {
        double x, y;
        double initialX, initialY;
        Color color;
        boolean immovable;

        Platform(double x, double y, Color color) {
            this.x = x;
            this.y = y;
            this.initialX = x;
            this.initialY = y;
            this.color = color;
            this.immovable = false;
        }

        void setImmovable(boolean immovable) {
            this.immovable = immovable;
        }

        boolean isImmovable() {
            return immovable;
        }

        void setX(double x) {
            this.x = x;
        }

        void setY(double y) {
            this.y = y;
        }

        void resetPosition() {
            this.x = initialX;
            this.y = initialY;
        }

        void snapToGrid() {
            x = Math.round(x / GRID_SIZE) * GRID_SIZE;
            y = Math.round(y / GRID_SIZE) * GRID_SIZE;
        }

        void draw(GraphicsContext gc) {
            gc.setFill(color);
            gc.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        }

        boolean contains(double px, double py) {
            return px >= x && px <= x + PLATFORM_WIDTH && py >= y && py <= y + PLATFORM_HEIGHT;
        }

        boolean intersects(Platform p) {
            return p.x < x + PLATFORM_WIDTH && p.x + PLATFORM_WIDTH > x &&
                    p.y < y + PLATFORM_HEIGHT && p.y + PLATFORM_HEIGHT > y;
        }

        boolean intersects(Character c) {
            return c.x < x + PLATFORM_WIDTH && c.x + CHARACTER_SIZE > x &&
                    c.y < y + PLATFORM_HEIGHT && c.y + CHARACTER_SIZE > y;
        }
    }

    private class Character {
        double x, y, velocityY;
        boolean jumping, movingLeft, movingRight;

        Character(double x, double y) {
            this.x = x;
            this.y = y;
            this.velocityY = 0;
            this.jumping = false;
        }

        void update(List<Platform> platforms) {
            System.out.println("Updating character: x=" + x + ", y=" + y);
            if (movingLeft) x -= 5;
            if (movingRight) x += 5;

            // Keep character within bounds
            x = Math.max(0, Math.min(x, GAME_PLAY_WIDTH - CHARACTER_SIZE));

            velocityY += 0.8; // Gravity
            y += velocityY;

            for (Platform platform : platforms) {
                if (platform.intersects(this) && velocityY > 0) {
                    y = platform.y - CHARACTER_SIZE;
                    velocityY = 0;
                    jumping = false;
                    break;
                }
            }

            if (y >= GAME_HEIGHT - CHARACTER_SIZE) {
                y = GAME_HEIGHT - CHARACTER_SIZE;
                velocityY = 0;
                jumping = false;
            }

            // Keep character within bounds
            y = Math.max(0, Math.min(y, GAME_HEIGHT - CHARACTER_SIZE));
        }

        void draw(GraphicsContext gc) {
            gc.setFill(Color.PURPLE);
            gc.fillRect(x, y, CHARACTER_SIZE, CHARACTER_SIZE);
        }

        boolean intersects(Rectangle r) {
            return x < r.getX() + r.getWidth() && x + CHARACTER_SIZE > r.getX() &&
                    y < r.getY() + r.getHeight() && y + CHARACTER_SIZE > r.getY();
        }
    }
}