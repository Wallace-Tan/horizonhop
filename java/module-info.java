module org.example.aptry2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires java.datatransfer;


    opens org.example.aptry2 to javafx.fxml;
    exports org.example.aptry2;
}