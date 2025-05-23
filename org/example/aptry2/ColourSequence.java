package org.example.aptry2;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class ColourSequence {
    ImageView background;

    // Color hint images
    Image redColour;
    Image orangeColour;
    Image yellowColour;
    Image greenColour;
    Image blueColour;
    Image arrowImg;

    public Group hintGroup;

    public ColourSequence(Image background, List<String> colourSequence) throws FileNotFoundException {
        // Load images
        redColour = loadImage("/image/Hint_Red.png");
        orangeColour = loadImage("/image/Hint_Orange.png");
        yellowColour = loadImage("/image/Hint_Yellow.png");
        greenColour = loadImage("/image/Hint_Green.png");
        blueColour = loadImage("/image/Hint_Blue.png");
        arrowImg = loadImage("/image/Hint_Arrow.png");

        // Set background
        this.background = new ImageView(background);
        this.background.setX(150);
        this.background.setY(175);
        this.background.setFitWidth(800);
        this.background.setFitHeight(350);

        hintGroup = new Group(this.background);

        double xPosition = 240;
        Random random = new Random();
        int idxNumber1 = random.nextInt(5);
        int idxNumber2;
        do {
            idxNumber2 = random.nextInt(5);
        } while (idxNumber2 == idxNumber1);

        int indexCount = 0;
        for (String colour : colourSequence) {
            ImageView colourImageView = createColourImageView(colour);
            colourImageView.setX(xPosition);
            colourImageView.setY(290);
            hintGroup.getChildren().add(colourImageView);

            if (indexCount < colourSequence.size() - 1) {
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

    private ImageView createColourImageView(String colour) {
        ImageView imageView = new ImageView();
        switch (colour) {
            case "Red" -> imageView.setImage(redColour);
            case "Orange" -> imageView.setImage(orangeColour);
            case "Yellow" -> imageView.setImage(yellowColour);
            case "Green" -> imageView.setImage(greenColour);
            case "Blue" -> imageView.setImage(blueColour);
        }
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
    }

    private Image loadImage(String path) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        return new Image(is);
    }
}
