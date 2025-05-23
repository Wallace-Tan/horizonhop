package org.example.aptry2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Character {
    // Character attributes
    public String name;
    public ImageView imageView;
    private Image characterStanding, characterWalkingLeft, characterWalkingRight,
            characterBackwardStanding, characterBackwardLeft, characterBackwardRight,
            characterJumping, characterJumpingBackward;
    private double velocityY = 0; // Vertical velocity
    private double velocityX = 0; // Horizontal velocity
    private final double gravity = 0.5; // Gravity constant
    private final double jumpStrength = 13; // Jump strength
    private final double acceleration = 0.2; // Horizontal acceleration
    private final double deceleration = 0.5; // Horizontal deceleration
    private final double maxSpeed = 2; // Maximum horizontal speed
    private int walkCount = 1; // Counter for walking animation
    private boolean isJumping = false; // Jumping state
    private boolean isMovingLeft = false; // Moving left state
    private boolean isMovingRight = false; // Moving right state
    private boolean isFacingLeft; // Facing left state
    private boolean isFacingRight; // Facing right state
    private boolean isLeftLeg = true; // Left leg animation state
    private boolean isRightLeg = false; // Right leg animation state
    private boolean isTopBound = false; // Top boundary state
    private double groundY = 560;  // Default ground level
    private List<String> playerJumpSequence = new ArrayList<>(); // Sequence of platforms the player has jumped on
    public static boolean levelComplete;
    public static boolean mediumlevel1Complete = false;
    public static boolean hardlevel1Complete = false;
    public static boolean level1Complete = false;
    public static boolean level2Complete = false;
    public static double positionX = 281;
    public static double positionY = 150;
    public static double positionX1 = 774;
    public static double positionY1 = 493;
    public static double positionX2 = 742;
    public static double positionY2 = 245;

    public Progress progress; // Progress tracker
    public boolean stop = true; // Stop state

    // Constructor
    public Character(String name, boolean facingLeft, boolean facingRight, Progress progress, List<String> predefinedColourSequence) {
        this.name = name;
        this.isFacingLeft = facingLeft;
        this.isFacingRight = facingRight;
        this.imageView = new ImageView(characterStanding);
        this.progress = progress;
        this.levelComplete = false;
        progress.currentColour = predefinedColourSequence.get(0);
    }

    // Set character appearance based on name
    // Set character appearance based on name
    public void setCharacterAppearance() throws FileNotFoundException {
        this.characterStanding = loadImage("/image/" + name + "_Standing.png");
        this.characterWalkingLeft = loadImage("/image/" + name + "_WalkingLeft.png");
        this.characterWalkingRight = loadImage("/image/" + name + "_WalkingRight.png");
        this.characterBackwardStanding = loadImage("/image/" + name + "_BackwardStanding.png");
        this.characterBackwardLeft = loadImage("/image/" + name + "_BackwardLeft.png");
        this.characterBackwardRight = loadImage("/image/" + name + "_BackwardRight.png");
        this.characterJumping = loadImage("/image/" + name + "_Jumping.png");
        this.characterJumpingBackward = loadImage("/image/" + name + "_JumpingBackward.png");

        // Set default image to standing
        this.imageView.setImage(characterStanding);
    }

    // Utility method for loading images safely
    private Image loadImage(String path) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        return new Image(is);
    }

    // Set character position
    public void setPosition(double xPosition, double yPosition) {
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
    }

    // Set character size
    public void setSize(double width, double height) {
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
        this.imageView.setPreserveRatio(true);
    }

    // Get character coordinates
    public double[] getCoordinates() {
        double xStart = this.imageView.getX();
        double xEnd = this.imageView.getX() + this.imageView.getFitWidth();
        double yStart = this.imageView.getY();
        double yEnd = this.imageView.getY() + this.imageView.getFitHeight();
        return new double[]{xStart, xEnd, yStart, yEnd};
    }

    // Check if character is out of horizontal bounds
    public boolean isBoundX(double xPosition) {
        boolean touchBound = false;
        if (xPosition > 1100 - this.imageView.getFitWidth() && isMovingRight) {
            touchBound = true;
        } else if (xPosition < 0 && isMovingLeft) {
            touchBound = true;
        }
        return touchBound;
    }

    // Check if character is out of vertical bounds
    public boolean isBoundY(double yPosition) {
        return (yPosition < 0 || yPosition > 700 - this.imageView.getFitHeight());
    }

    // Check if character is on a platform
    public boolean isOnPlatform(double[] characterCoordinates, double[] platformCoordinates) {
        return characterCoordinates[1] >= platformCoordinates[0] && characterCoordinates[0] <= platformCoordinates[1]
                && characterCoordinates[3] >= platformCoordinates[2] && characterCoordinates[3] <= platformCoordinates[3];
    }

    // Check if platform is in front of the character
    public boolean platformAtFront(double nextX, double nextY, double[] platformCoordinates) {
        return (nextX + this.imageView.getFitWidth() > platformCoordinates[0]) && (nextY < platformCoordinates[3])
                && (nextY + this.imageView.getFitHeight() > platformCoordinates[2]) && (nextX < platformCoordinates[0]);
    }

    // Check if platform is behind the character
    public boolean platformAtBack(double nextX, double nextY, double[] platformCoordinates) {
        return (nextX + this.imageView.getFitWidth() > platformCoordinates[1]) && (nextY < platformCoordinates[3])
                && (nextY + this.imageView.getFitHeight() > platformCoordinates[2]) && (nextX < platformCoordinates[1]);
    }

    // Check if platform is above the character
    public boolean platformAtTop(double nextX, double nextY, double[] platformCoordinates) {
        return (nextY < platformCoordinates[3]) && (nextX + this.imageView.getFitWidth() > platformCoordinates[0])
                && (nextX < platformCoordinates[1]) && (nextY > platformCoordinates[2]);
    }

    // Handle character movement based on key press
    public void characterMove(KeyEvent e) {
        if (!levelComplete) {
            switch (e.getCode()) {
                case UP -> {
                    if (!isJumping) {
                        groundY = this.imageView.getY();
                        velocityY = -jumpStrength;
                        isJumping = true;
                        if (isFacingRight) {
                            this.imageView.setImage(characterJumping);
                        } else if (isFacingLeft) {
                            this.imageView.setImage(characterJumpingBackward);
                        }
                    }
                }
                case LEFT -> {
                    isMovingLeft = true;
                    isMovingRight = false;
                    isFacingLeft = true;
                    isFacingRight = false;
                    animationChange();
                }
                case RIGHT -> {
                    isMovingRight = true;
                    isMovingLeft = false;
                    isFacingLeft = false;
                    isFacingRight = true;
                    animationChange();
                }
            }
        }
    }

    // Handle character stop based on key release
    public void characterStop(KeyEvent e) {
        if (!levelComplete) {
            switch (e.getCode()) {
                case UP -> {
                    if (isFacingLeft) {
                        this.imageView.setImage(characterBackwardStanding);
                    } else if (isFacingRight) {
                        this.imageView.setImage(characterStanding);
                    }
                }
                case LEFT -> {
                    isMovingLeft = false;
                    this.imageView.setImage(characterBackwardStanding);
                }
                case RIGHT -> {
                    isMovingRight = false;
                    this.imageView.setImage(characterStanding);
                }
            }
        }
    }

    // Change animation based on movement
    private void animationChange() {
        if (walkCount == 0) {
            isRightLeg = false;
            isLeftLeg = true;
        } else if (walkCount == 2) {
            isRightLeg = true;
            isLeftLeg = false;
        }
        if (isRightLeg) {
            walkCount -= 1;
            if (isMovingRight) {
                this.imageView.setImage(characterWalkingRight);
            } else if (isMovingLeft) {
                this.imageView.setImage(characterBackwardRight);
            }
        } else if (isLeftLeg) {
            walkCount += 1;
            if (isMovingRight) {
                this.imageView.setImage(characterWalkingLeft);
            } else if (isMovingLeft) {
                this.imageView.setImage(characterBackwardLeft);
            }
        }
    }

    // Handle character jumping and falling
    private void handleJumpingAndFalling() {
        double nextYPosition = this.imageView.getY() + velocityY + gravity;
        if (!isBoundY(nextYPosition)) {
            if (nextYPosition < groundY) {
                this.imageView.setY(nextYPosition);
                velocityY += gravity;
                this.imageView.setImage(isFacingRight ? characterJumping : characterJumpingBackward);
            } else {
                this.imageView.setY(groundY);
                isJumping = false;
                velocityY = 0;
                if (!isMovingLeft && !isMovingRight) {
                    this.imageView.setImage(isFacingRight ? characterStanding : characterBackwardStanding);
                }
            }
        } else {
            if (nextYPosition <= 0) {
                velocityY = 0;
                isTopBound = true;
            }
        }

        if (isTopBound) {
            velocityY += gravity;
            this.imageView.setY(this.imageView.getY() + velocityY);
            if (this.imageView.getY() >= groundY) {
                this.imageView.setY(groundY);
                isJumping = false;
                isTopBound = false;
            }
            this.imageView.setImage(isFacingLeft ? characterBackwardStanding : characterStanding);
        }
    }

    // Handle character horizontal movement
    private void handleHorizontalMovement() {
        if (isMovingLeft) {
            velocityX = Math.max(velocityX - acceleration, -maxSpeed);
        } else if (isMovingRight) {
            velocityX = Math.min(velocityX + acceleration, maxSpeed);
        } else {
            if (velocityX > 0) {
                velocityX = Math.max(velocityX - deceleration, 0);
            } else if (velocityX < 0) {
                velocityX = Math.min(velocityX + deceleration, 0);
            }
        }

        double nextXPosition = this.imageView.getX() + velocityX;
        if (!isBoundX(nextXPosition)) {
            this.imageView.setX(nextXPosition);
        }
    }

    // Check progress based on predefined color sequence
    private void progressCheck(List<String> predefinedColourSequence) {
        if (playerJumpSequence.size() <= predefinedColourSequence.size() && playerJumpSequence.equals(predefinedColourSequence.subList(0, playerJumpSequence.size()))) {
            progress.currentColour = predefinedColourSequence.subList(0, playerJumpSequence.size()).get(playerJumpSequence.size() - 1);
            if (progress.platformCount >= 1) {
                progress.previousColour = predefinedColourSequence.subList(0, playerJumpSequence.size()).get(playerJumpSequence.size() - 2);
            }
            progress.platformCount += 1;
        } else {
            progress.isFail = true;
        }
    }

    // Update character position
    public void updatePosition(List<String> predefinedColourSequence, ArrayList<Platform> platformList, TreasureBox treasureBox, int starCount) {
        handleJumpingAndFalling();

        boolean onAnyPlatform = false;
        boolean blockedFront = false;
        boolean blockedBackwardFront = false;

        double nextXPosition = this.imageView.getX() + velocityX;
        double nextYPosition = this.imageView.getY() + velocityY;

        for (Platform platform : platformList) {
            double[] platformCoordinates = platform.getCoordinates();
            if (playerJumpSequence.size() < predefinedColourSequence.size()) {
                progress.nextColour = predefinedColourSequence.get(playerJumpSequence.size());
            }

            if (isOnPlatform(this.getCoordinates(), platformCoordinates)) {
                groundY = platformCoordinates[2] - this.imageView.getFitHeight();
                onAnyPlatform = true;
                isJumping = false;

                if (playerJumpSequence.isEmpty() || !playerJumpSequence.get(playerJumpSequence.size() - 1).equals(platform.colour)) {
                    playerJumpSequence.add(platform.colour);
                    progressCheck(predefinedColourSequence);
                }
            }

            if (!isBoundX(nextXPosition)) {
                if (platformAtFront(nextXPosition, nextYPosition, platformCoordinates)) {
                    blockedFront = true;
                }
                if (platformAtBack(nextXPosition, nextYPosition, platformCoordinates)) {
                    blockedBackwardFront = true;
                }
                if (platformAtTop(nextXPosition, nextYPosition, platformCoordinates)) {
                    velocityY = 0;
                    onAnyPlatform = true;
                }
            }

            this.levelComplete = platform.checkTreasureBox(this, treasureBox);
        }

        if (!isBoundX(nextXPosition) && !levelComplete) {
            if (isMovingRight && !blockedFront) {
                handleHorizontalMovement();
            } else if (isMovingLeft && !blockedBackwardFront) {
                handleHorizontalMovement();
            } else {
                if (velocityX > 0) {
                    velocityX = Math.max(velocityX - deceleration, 0);
                } else if (velocityX < 0) {
                    velocityX = Math.min(velocityX + deceleration, 0);
                }
            }
        }
        if (!onAnyPlatform) {
            groundY = 560;  // Fall back to ground level
            if (!isJumping && this.imageView.getY() < groundY) {
                isJumping = true;
            }
        }
    }
}