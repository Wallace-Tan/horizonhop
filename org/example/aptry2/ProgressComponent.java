package org.example.aptry2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ProgressComponent {
    ImageView imageView;
    Image doneImg;
    Image currentImg;
    boolean isCur;
    String colour;
    double initX = 815;

    ProgressComponent(Image done, Image current, Image currentBig, String colour) {
        this.doneImg = done;
        this.currentImg = current;
        this.imageView = new ImageView();  // Initialize imageView
        this.imageView.setImage(currentBig);
        this.imageView.setFitWidth(45);
        this.imageView.setFitHeight(45);
        this.colour = colour;
        Progress.progressComponentArrayList.add(this);
    }

    public void setPosition(double xPosition, double yPosition) {
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
    }

    public void progressUpdate() {
        if (this.isCur) {
            this.imageView.setImage(currentImg);
        }
    }

    public void dynamicPosition(List<String> colourSequence){
        for (String definedColour: colourSequence){
            if (definedColour.equals(this.colour)){
                this.setPosition(initX + (55* colourSequence.indexOf(definedColour)),125);
            }
        }
    }
}
