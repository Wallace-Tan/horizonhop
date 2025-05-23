package org.example.aptry2;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerSingleton {
    private static MediaPlayer mediaPlayer; // Singleton MediaPlayer instance

    // Method to get the instance of MediaPlayer
    public static MediaPlayer getInstance(String fileName) {
        if (mediaPlayer == null) { // If no instance exists, create one and play the media
            play(fileName);
        }
        return mediaPlayer; // Return the MediaPlayer instance
    }

    // Method to play media file
    public static void play(String fileName) {
        try {
            // Stop and dispose existing media player if it exists
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }
            // Get the path of the media file
            String path = String.valueOf(MediaPlayerSingleton.class.getResource(fileName));
            Media media = new Media(path); // Create a Media instance
            mediaPlayer = new MediaPlayer(media); // Create a new MediaPlayer instance
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set media to loop indefinitely
            mediaPlayer.play(); // Play the media
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an exception
            // Show an alert dialog with the error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to play media: " + e.getMessage());
            alert.showAndWait();
        }
    }

    // Method to stop the media player
    public static void stop() {
        if (mediaPlayer != null) { // If the MediaPlayer instance exists
            mediaPlayer.stop(); // Stop the media
        }
    }
}