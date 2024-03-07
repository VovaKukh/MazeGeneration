module org.example.Maze {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.visualization;
    opens org.visualization to javafx.fxml;
    exports org.models;
    opens org.models to javafx.fxml;
    exports org.helpers;
    opens org.helpers to javafx.fxml;
}
