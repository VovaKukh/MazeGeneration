package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.visualization.MazeVisualizer;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        MazeGenerator mazeGenerator = new MazeGenerator(36, 36);
        mazeGenerator.generateMaze(0,0);

        MazeVisualizer mazeVisualizer = new MazeVisualizer(mazeGenerator);

        StackPane root = new StackPane(mazeVisualizer);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Maze Visualization");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
