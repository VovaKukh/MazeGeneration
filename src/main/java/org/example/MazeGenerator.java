package org.example;

import org.models.Cell;
import org.models.IMazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGenerator implements IMazeGenerator {
    private int currentX;
    private int currentY;

    private final int width, height;
    private final Cell[][] cells;
    private boolean generationComplete = false;
    private final Random rand = new Random();


    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        initCells();
    }

    private void initCells() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public void generateMaze(int startX, int startY) {
        this.currentX = startX;
        this.currentY = startY;

        // Create a path of 4 cells to the right
        for (int x = startX; x < startX + 4; x++) {
            // Check bounds and remove walls between cells
            if (x + 1 < width) {
                cells[x][startY].removeRightWall();
                cells[x + 1][startY].removeLeftWall();
                cells[x][startY].setVisited(true);
            }
        }
        cells[startX + 4][startY].setVisited(true);

        // One cell down
        int downX = startX + 4;
        if (startY + 1 < height) {
            cells[downX][startY].removeBottomWall();
            cells[downX][startY + 1].removeTopWall();
            cells[downX][startY + 1].setVisited(true);
        }

        // Create a path of 4 cells to the left from the down position
        for (int x = downX; x > downX - 4; x--) {
            // Check bounds and remove walls between cells
            if (x - 1 >= 0) {
                cells[x][startY + 1].removeLeftWall();
                cells[x - 1][startY + 1].removeRightWall();
                cells[x][startY + 1].setVisited(true);
            }
        }
    }

    public boolean generateNextRandomStep() {
        // Mark the current cell as visited
        cells[currentX][currentY].setVisited(true);

        // Array to hold potential moves
        int[][] moves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up
        List<int[]> possibleMoves = new ArrayList<>();

        // Check each direction
        for (int[] move : moves) {
            int newX = currentX + move[0];
            int newY = currentY + move[1];

            // Check bounds and if the new cell is not visited
            if (newX >= 0 && newX < width && newY >= 0 && newY < height && !cells[newX][newY].isVisited()) {
                possibleMoves.add(move);
            }
        }

        // If there are no unvisited cells around the current cell, the generation is complete
        if (possibleMoves.isEmpty()) {
            generationComplete = true;
        } else {
            // Randomly select a move
            int[] selectedMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));

            // Remove walls between the current cell and the selected move
            removeWalls(currentX, currentY, selectedMove[0], selectedMove[1]);

            // Move to the new cell
            currentX += selectedMove[0];
            currentY += selectedMove[1];

            // Mark the new cell as visited
            cells[currentX][currentY].setVisited(true);
        }

        return generationComplete;
    }

    private void removeWalls(int x, int y, int dx, int dy) {
        // Moving right
        if (dx == 1 && dy == 0) {
            cells[x][y].removeRightWall();
            cells[x + 1][y].removeLeftWall();
        }

        // Moving down
        else if (dx == 0 && dy == 1) {
            cells[x][y].removeBottomWall();
            cells[x][y + 1].removeTopWall();
        }

        // Moving left
        else if (dx == -1 && dy == 0) {
            cells[x][y].removeLeftWall();
            cells[x - 1][y].removeRightWall();
        }

        // Moving up
        else if (dx == 0 && dy == -1) {
            cells[x][y].removeTopWall();
            cells[x][y - 1].removeBottomWall();
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
