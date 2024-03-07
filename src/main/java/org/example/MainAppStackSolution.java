package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.helpers.AStarSolver;
import org.models.Cell;
import org.visualization.MazeVisualizer;

import java.util.List;

public class MainAppStackSolution extends Application {

    @Override
    public void start(Stage stage) {
        MazeGeneratorStack mazeGenerator = new MazeGeneratorStack(36, 36);
        Cell[][] cells = mazeGenerator.generateMaze(0,0);

        // Search for solution
        AStarSolver aStarSolver = new AStarSolver(cells);
        List<Cell> cellSolutionList = aStarSolver.findPath();

        MazeVisualizer mazeVisualizer = new MazeVisualizer(mazeGenerator);
        mazeVisualizer.drawSolutionPath(cellSolutionList);

        // Wrap the MazeVisualizer in a StackPane (or any other layout)
        StackPane root = new StackPane(mazeVisualizer);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Maze Stack Solution Visualization using A*");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
