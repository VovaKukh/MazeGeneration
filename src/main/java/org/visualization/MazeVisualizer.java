package org.visualization;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.models.Cell;
import org.models.IMazeGenerator;

import java.util.List;

public class MazeVisualizer extends Canvas {
    private final IMazeGenerator mazeGenerator;
    private static final int CELL_SIZE = 20;
    GraphicsContext gc = getGraphicsContext2D();

    public MazeVisualizer(IMazeGenerator mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
        this.setWidth(mazeGenerator.getWidth() * CELL_SIZE);
        this.setHeight(mazeGenerator.getHeight() * CELL_SIZE);
        drawMaze();
    }

    public void drawMaze() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        Cell[][] cells = mazeGenerator.getCells();
        for (Cell[] cell : cells) {
            for (Cell value : cell) {
                drawCell(gc, value);
            }
        }
    }

    public void drawSolutionPath(List<Cell> solutionPath) {
        for (Cell cell : solutionPath) {
            cell.setCurrent(true);
            drawCell(gc, cell);
        }

        Cell previousCell = null;

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3);

        for (Cell cell : solutionPath) {
            if (previousCell != null) {
                double previousCenterX = (previousCell.getX() * CELL_SIZE) + (CELL_SIZE / 2.0);
                double previousCenterY = (previousCell.getY() * CELL_SIZE) + (CELL_SIZE / 2.0);

                double currentCenterX = (cell.getX() * CELL_SIZE) + (CELL_SIZE / 2.0);
                double currentCenterY = (cell.getY() * CELL_SIZE) + (CELL_SIZE / 2.0);

                gc.strokeLine(previousCenterX, previousCenterY, currentCenterX, currentCenterY);
            }
            previousCell = cell;
        }
    }

    private void drawCell(GraphicsContext gc, Cell cell) {
        // Coordinates for the cell's top-left corner
        int xCoord = cell.getX() * CELL_SIZE;
        int yCoord = cell.getY() * CELL_SIZE;

        // If the cell has been visited, fill it with purple
        if (cell.isVisited()) {
            gc.setFill(Color.MEDIUMPURPLE);
            gc.fillRect(xCoord, yCoord, CELL_SIZE, CELL_SIZE);
        }

        // If the cell has been visited, fill it with purple
        if (cell.isCurrent()) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(xCoord, yCoord, CELL_SIZE, CELL_SIZE);
        }

        // Set the stroke color for the cell walls
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Draw the top wall
        if (cell.hasTopWall()) {
            gc.strokeLine(xCoord, yCoord, xCoord + CELL_SIZE, yCoord);
        }
        // Draw the right wall
        if (cell.hasRightWall()) {
            gc.strokeLine(xCoord + CELL_SIZE, yCoord, xCoord + CELL_SIZE, yCoord + CELL_SIZE);
        }
        // Draw the bottom wall
        if (cell.hasBottomWall()) {
            gc.strokeLine(xCoord, yCoord + CELL_SIZE, xCoord + CELL_SIZE, yCoord + CELL_SIZE);
        }
        // Draw the left wall
        if (cell.hasLeftWall()) {
            gc.strokeLine(xCoord, yCoord, xCoord, yCoord + CELL_SIZE);
        }
    }
}

