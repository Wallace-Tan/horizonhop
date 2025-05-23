package org.example.aptry2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChooseCharacter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/chooseCharacter.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Horizon Hop");
        primaryStage.setScene(scene);
        primaryStage.show();
        MediaPlayerSingleton.getInstance("/backgroundMusic.m4a");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
