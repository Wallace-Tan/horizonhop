package org.example.aptry2;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Star {
    ImageView starImage; // Image view for the star
    boolean collected = false; // Flag to check if the star has been collected

    // Constructor to initialize the star with an image and animation
    public Star(Image starImg) {
        this.starImage = new ImageView(starImg); // Create an ImageView for the star
        initAnimation(); // Initialize animation for the star
    }

    // Set the position of the star on the screen
    public void setPosition(double xPosition, double yPosition) {
        starImage.setX(xPosition); // Set X position
        starImage.setY(yPosition); // Set Y position
    }

    // Set the size of the star image
    public void setSize(double width, double height) {
        starImage.setFitWidth(width); // Set width
        starImage.setFitHeight(height); // Set height
    }

    // Check if the character has collected the star
    public boolean isCollected(double[] characterCoordinates) {
        // Check if the character's coordinates overlap with the star's position
        boolean isTouching = characterCoordinates[1] >= this.starImage.getX() &&
                characterCoordinates[0] <= this.starImage.getX() + this.starImage.getFitWidth() &&
                characterCoordinates[3] >= this.starImage.getY() &&
                characterCoordinates[2] <= this.starImage.getY() + this.starImage.getFitHeight();

        if (isTouching) {
            starImage.setVisible(false); // Hide the star image
            collected = true; // Set collected flag to true
            Beginner_Level1.starCount += 1; // Increment star count in the level
        }
        return collected; // Return the collected status
    }

    // Initialize the animation for the star
    private void initAnimation() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), starImage); // Create a transition for vertical movement
        transition.setFromY(starImage.getY() - 10); // Start position
        transition.setToY(starImage.getY() + 10); // End position
        transition.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        transition.setAutoReverse(true); // Reverse direction at the end
        transition.play(); // Start the animation
    }
}