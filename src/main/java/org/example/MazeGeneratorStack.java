package org.example;

import org.models.Cell;
import org.models.IMazeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGeneratorStack implements IMazeGenerator {
    private final int width, height;
    private final Cell[][] cells;
    private final Stack<Cell> stack = new Stack<>();
    private final Stack<Cell> pathStack = new Stack<>();
    private final Random rand = new Random();

    public MazeGeneratorStack(int width, int height) {
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

    public Cell[][] generateMaze(int startX, int startY) {
        initCells();

        // Starting cell
        Cell startCell = cells[startX][startY];
        startCell.setVisited(true);
        stack.push(startCell);

        while (!stack.isEmpty()) {
            Cell currentCell = stack.peek();
            List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(currentCell);

            if (!unvisitedNeighbors.isEmpty()) {
                Cell nextCell = unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
                removeWallsBetween(currentCell, nextCell);
                nextCell.setVisited(true);
                stack.push(nextCell);
            } else {
                stack.pop();
            }
        }

        return cells;
    }

    private List<Cell> getUnvisitedNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up

        for (int[] dir : directions) {
            int newX = cell.getX() + dir[0];
            int newY = cell.getY() + dir[1];

            if (newX >= 0 && newX < width && newY >= 0 && newY < height && !cells[newX][newY].isVisited()) {
                neighbors.add(cells[newX][newY]);
            }
        }
        return neighbors;
    }

    private void removeWallsBetween(Cell currentCell, Cell nextCell) {
        int dx = nextCell.getX() - currentCell.getX();
        int dy = nextCell.getY() - currentCell.getY();

        if (dx == 1) {
            currentCell.removeRightWall();
            nextCell.removeLeftWall();
        } else if (dx == -1) {
            currentCell.removeLeftWall();
            nextCell.removeRightWall();
        } else if (dy == 1) {
            currentCell.removeBottomWall();
            nextCell.removeTopWall();
        } else if (dy == -1) {
            currentCell.removeTopWall();
            nextCell.removeBottomWall();
        }
    }

    public boolean generateNextRandomStep(int startX, int startY) {
        if (pathStack.isEmpty()) {
            cells[startX][startY].setVisited(true);
            cells[startX][startY].setCurrent(true);
            pathStack.push(cells[startX][startY]);
        }

        if (!pathStack.isEmpty()) {
            // Update the previous cell before moving to a new one
            Cell previousCell = pathStack.peek();
            previousCell.setCurrent(false);

            Cell currentCell = pathStack.peek();
            List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(currentCell);

            if (!unvisitedNeighbors.isEmpty()) {
                // Randomly select an unvisited neighbor
                Cell selectedNeighbor = unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));

                // Remove walls between current cell and selected neighbor
                removeWallsBetween(currentCell, selectedNeighbor);

                // Mark the selected cell as visited and push it to the stack
                selectedNeighbor.setCurrent(true);
                selectedNeighbor.setVisited(true);
                pathStack.push(selectedNeighbor);

                // Successfully moved and explored a new cell, not yet complete
                return false;
            } else {
                // No unvisited neighbors, backtrack
                pathStack.pop();
                if (!pathStack.isEmpty()) {
                    Cell backCell = pathStack.peek();
                    backCell.setCurrent(true);

                    // Successfully moved and explored a new cell, not yet complete
                    return false;
                }
            }
        }

        // Generation is complete
        return true;
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
