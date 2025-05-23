package org.example.aptry2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TreasureBox {
    ImageView imageView;
    Image openImg;

    TreasureBox(Image unopenImg, Image openImg){
        this.imageView = new ImageView(unopenImg);
        this.openImg = openImg;
    }

    public void setSize(double width, double height){
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
    }

    public void setPosition(double xPosition, double yPosition){
        this.imageView.setX(xPosition);
        this.imageView.setY(yPosition);
    }
}
