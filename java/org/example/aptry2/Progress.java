package org.example.aptry2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Progress {
    ImageView current, red, orange, yellow, green, blue, background, title;
    Image redDone, redCur, redCurBig, orangeDone, orangeCur, orangeCurBig,
            yellowDone, yellowCur, yellowCurBig, greenDone, greenCur, greenCurBig,
            blueDone, blueCur, blueCurBig, treasureBoxUnopen, fail;
    ProgressComponent redComp, orangeComp, yellowComp, greenComp, blueComp;

    List<String> colourSequence;
    static ArrayList<ProgressComponent> progressComponentArrayList = new ArrayList<>();
    int platformCount = 0;
    String currentColour;
    String previousColour;
    String nextColour;
    boolean isFail = false;

    public Progress(Image background, List<String> colourSequence) throws FileNotFoundException {
        this.background = new ImageView(background);
        this.background.setX(800);
        this.background.setY(10);
        this.background.setFitHeight(180);
        this.background.setFitWidth(290);
        this.colourSequence = colourSequence;

        title = new ImageView(loadImage("/image/Progress_NextTitle.png"));
        title.setX(815);
        title.setY(65);
        title.setFitWidth(150);
        title.setFitHeight(50);

        redDone = loadImage("/image/Progress_Red_Done.png");
        redCur = loadImage("/image/Progress_Red_Current.png");
        redCurBig = loadImage("/image/Progress_Red_Current_Big.png");
        redComp = new ProgressComponent(redDone, redCur, redCurBig, "Red");
        redComp.dynamicPosition(colourSequence);
        red = redComp.imageView;

        orangeDone = loadImage("/image/Progress_Orange_Done.png");
        orangeCur = loadImage("/image/Progress_Orange_Current.png");
        orangeCurBig = loadImage("/image/Progress_Orange_Current_Big.png");
        orangeComp = new ProgressComponent(orangeDone, orangeCur, orangeCurBig, "Orange");
        orangeComp.dynamicPosition(colourSequence);
        orange = orangeComp.imageView;

        yellowDone = loadImage("/image/Progress_Yellow_Done.png");
        yellowCur = loadImage("/image/Progress_Yellow_Current.png");
        yellowCurBig = loadImage("/image/Progress_Yellow_Current_Big.png");
        yellowComp = new ProgressComponent(yellowDone, yellowCur, yellowCurBig, "Yellow");
        yellowComp.dynamicPosition(colourSequence);
        yellow = yellowComp.imageView;

        greenDone = loadImage("/image/Progress_Green_Done.png");
        greenCur = loadImage("/image/Progress_Green_Current.png");
        greenCurBig = loadImage("/image/Progress_Green_Current_Big.png");
        greenComp = new ProgressComponent(greenDone, greenCur, greenCurBig, "Green");
        greenComp.dynamicPosition(colourSequence);
        green = greenComp.imageView;

        blueDone = loadImage("/image/Progress_Blue_Done.png");
        blueCur = loadImage("/image/Progress_Blue_Current.png");
        blueCurBig = loadImage("/image/Progress_Blue_Current_Big.png");
        blueComp = new ProgressComponent(blueDone, blueCur, blueCurBig, "Blue");
        blueComp.dynamicPosition(colourSequence);
        blue = blueComp.imageView;

        treasureBoxUnopen = loadImage("/image/TreasureBox_Beginner_Unopen.png");
        fail = loadImage("/image/Progress_Fail.png");

        current = new ImageView();
        current.setX(975);
        current.setY(30);
        current.setFitWidth(85);
        current.setFitHeight(85);
    }

    private Image loadImage(String path) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        return new Image(is);
    }

    public void fail() {
        current.setImage(fail);
        red.setImage(fail);
        orange.setImage(fail);
        yellow.setImage(fail);
        green.setImage(fail);
        blue.setImage(fail);
    }

    public void nextProgressCheck() {
        switch (nextColour) {
            case "Red" -> current.setImage(redCurBig);
            case "Orange" -> current.setImage(orangeCurBig);
            case "Yellow" -> current.setImage(yellowCurBig);
            case "Green" -> current.setImage(greenCurBig);
            case "Blue" -> current.setImage(blueCurBig);
        }
    }

    public void currentProgressCheck() {
        switch (currentColour) {
            case "Red" -> {
                redComp.isCur = true;
                redComp.progressUpdate();
            }
            case "Orange" -> {
                orangeComp.isCur = true;
                orangeComp.progressUpdate();
            }
            case "Yellow" -> {
                yellowComp.isCur = true;
                yellowComp.progressUpdate();
            }
            case "Green" -> {
                greenComp.isCur = true;
                greenComp.progressUpdate();
            }
            case "Blue" -> {
                blueComp.isCur = true;
                blueComp.progressUpdate();
            }
        }
    }
}
