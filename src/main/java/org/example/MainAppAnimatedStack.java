package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.visualization.MazeVisualizer;

public class MainAppAnimatedStack extends Application {

    @Override
    public void start(Stage stage) {
        MazeGeneratorStack mazeGenerator = new MazeGeneratorStack(36, 36);
        MazeVisualizer mazeVisualizer = new MazeVisualizer(mazeGenerator);

        StackPane root = new StackPane(mazeVisualizer);
        Scene scene = new Scene(root);

        // Delay between steps in nanoseconds
        final long delayBetweenSteps = (long) (0.01 * 1_000_000_000);

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Only proceed if enough time has passed since the last update
                if ((now - lastUpdate) >= delayBetweenSteps) {
                    boolean generationComplete = mazeGenerator.generateNextRandomStep(0,0);
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
        stage.setTitle("Maze Visualization Animation");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
