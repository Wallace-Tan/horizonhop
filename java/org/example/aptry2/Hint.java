package org.example.aptry2;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

public class Hint {
    ImageView background;
    Image redColour;
    Image orangeColour;
    Image yellowColour;
    Image greenColour;
    Image blueColour;
    Image unknownColour;
    Image arrowImg;
    Group hintGroup; // Group to hold all hint elements

    // Constructor for the Hint class
    public Hint(Image backgroundImage, List<String> colourSequence) {
        // Load images as resources from the classpath
        redColour = new Image(getClass().getResourceAsStream("/image/Hint_Red.png"));
        orangeColour = new Image(getClass().getResourceAsStream("/image/Hint_Orange.png"));
        yellowColour = new Image(getClass().getResourceAsStream("/image/Hint_Yellow.png"));
        greenColour = new Image(getClass().getResourceAsStream("/image/Hint_Green.png"));
        blueColour = new Image(getClass().getResourceAsStream("/image/Hint_Blue.png"));
        unknownColour = new Image(getClass().getResourceAsStream("/image/Hint_UnknownColour.png"));
        arrowImg = new Image(getClass().getResourceAsStream("/image/Hint_Arrow.png"));

        this.background = new ImageView(backgroundImage);
        this.background.setX(150);
        this.background.setY(175);
        this.background.setFitWidth(800);
        this.background.setFitHeight(350);

        hintGroup = new Group(this.background);
        double xPosition = 240;

        // Randomly select two indices to reveal the actual colors
        Random random = new Random();
        int idxNumber1 = random.nextInt(5);
        int idxNumber2;
        do {
            idxNumber2 = random.nextInt(5);
        } while (idxNumber2 == idxNumber1);

        int indexCount = 0;
        for (String colour : colourSequence) {
            ImageView colourImageView = createColourImageView(colour, indexCount, idxNumber1, idxNumber2);
            colourImageView.setX(xPosition);
            colourImageView.setY(290);
            hintGroup.getChildren().add(colourImageView);

            // Add arrow except after last color
            if (indexCount < 4) {
                ImageView arrow = new ImageView(arrowImg);
                arrow.setX(xPosition + 105);
                arrow.setY(325);
                arrow.setFitWidth(22);
                arrow.setFitHeight(30);
                hintGroup.getChildren().add(arrow);
            }

            xPosition += 130;
            indexCount++;
        }
    }

    private ImageView createColourImageView(String colour, int indexCount, int random1, int random2) {
        ImageView imageView = new ImageView();
        if (indexCount == random1 || indexCount == random2) {
            switch (colour) {
                case "Red" -> imageView.setImage(redColour);
                case "Orange" -> imageView.setImage(orangeColour);
                case "Yellow" -> imageView.setImage(yellowColour);
                case "Green" -> imageView.setImage(greenColour);
                case "Blue" -> imageView.setImage(blueColour);
                default -> imageView.setImage(unknownColour);
            }
        } else {
            imageView.setImage(unknownColour);
        }
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
    }
}
