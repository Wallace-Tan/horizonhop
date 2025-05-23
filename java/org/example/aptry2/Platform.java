package org.example.aptry2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Platform {
    String colour; // Colour of the platform
    ImageView platformImage; // ImageView representing the platform

    // Constructor to initialize platform and add it to the platform list
    Platform(String colour, Image platformImage, ArrayList<Platform> platformList) {
        this.colour = colour;
        this.platformImage = new ImageView(platformImage);
        platformList.add(this);
    }

    // Method to set the position of the platform
    void setPlatformPosition(double xPosition, double yPosition) {
        platformImage.setX(xPosition);
        platformImage.setY(yPosition);
    }

    // Method to set the size of the platform
    void setSize(double width, double height) {
        platformImage.setFitWidth(width);
        platformImage.setFitHeight(height);
    }

    // Method to get the coordinates of the platform
    double[] getCoordinates() {
        double xStart = platformImage.getX();
        double xEnd = xStart + platformImage.getFitWidth();
        double yStart = platformImage.getY();
        double yEnd = yStart + platformImage.getFitHeight();
        return new double[]{xStart, xEnd, yStart, yEnd};
    }

    // Method to check if a character interacts with a treasure box
    public boolean checkTreasureBox(Character character, TreasureBox treasureBox) {
        if (character.getCoordinates()[1] >= treasureBox.imageView.getX() &&
                character.getCoordinates()[0] <= treasureBox.imageView.getX() + treasureBox.imageView.getFitWidth() &&
                character.getCoordinates()[3] >= treasureBox.imageView.getY() &&
                character.getCoordinates()[2] <= treasureBox.imageView.getY() + treasureBox.imageView.getFitHeight()) {
            treasureBox.imageView.setImage(treasureBox.openImg); // Set the treasure box image to open state
            return true;
        }
        return false;
    }

    // Method to change the platform colour
    public void changePlatformColour(Image curColourPlatform, String curColour, Image newColourPlatform, String newColour) {
        if (this.colour.equals(curColour)) {
            this.colour = newColour;
            this.platformImage.setImage(newColourPlatform); // Change to new colour
        } else if (this.colour.equals(newColour)) {
            this.colour = curColour;
            this.platformImage.setImage(curColourPlatform); // Revert to current colour
        }
    }
}