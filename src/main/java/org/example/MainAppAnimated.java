package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import org.visualization.MazeVisualizer;

public class MainAppAnimated extends Application {

    @Override
    public void start(Stage stage) {
        MazeGenerator mazeGenerator = new MazeGenerator(36, 36);
        MazeVisualizer mazeVisualizer = new MazeVisualizer(mazeGenerator);

        StackPane root = new StackPane(mazeVisualizer);
        Scene scene = new Scene(root);

        // Delay between steps in nanoseconds
        final long delayBetweenSteps = (long) (0.4 * 1_000_000_000);

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Only proceed if enough time has passed since the last update
                if ((now - lastUpdate) >= delayBetweenSteps) {
                    boolean generationComplete = mazeGenerator.generateNextRandomStep();
                    mazeVisualizer.drawMaze();

                    lastUpdate = now;

                    if (generationComplete) {
                        stop();
                    }
                }
            }
        };

        animationTimer.start();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
