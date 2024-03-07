package org.helpers;

import org.models.Cell;

import java.util.*;

public class AStarSolver {
    private final Cell[][] cells;
    private final int width;
    private final int height;
    private PriorityQueue<Cell> openSet;
    private HashSet<Cell> closedSet;
    private final Cell startCell;
    private final Cell endCell;

    public AStarSolver(Cell[][] cells) {
        this.cells = cells;
        this.width = cells.length;
        this.height = cells[0].length;
        this.startCell = cells[0][0];
        this.endCell = cells[width - 1][height - 1];
        init();
    }

    private void init() {
        Comparator<Cell> cellComparator = Comparator.comparingInt(c -> c.fScore);
        openSet = new PriorityQueue<>(cellComparator);
        closedSet = new HashSet<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = cells[x][y];
                cell.gScore = Integer.MAX_VALUE;
                cell.fScore = Integer.MAX_VALUE;
                cell.parent = null;
            }
        }

        // Setup start cell
        startCell.gScore = 0;
        startCell.fScore = heuristic(startCell, endCell);
        openSet.add(startCell);
    }

    public List<Cell> findPath() {
        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            if (current == endCell) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Cell neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor)) continue;

                int tentativeGScore = current.gScore + distBetween(current, neighbor);
                if (tentativeGScore >= neighbor.gScore) continue;

                neighbor.parent = current;
                neighbor.gScore = tentativeGScore;
                neighbor.fScore = tentativeGScore + heuristic(neighbor, endCell);

                if (!openSet.contains(neighbor)) openSet.add(neighbor);
            }
        }

        return Collections.emptyList();
    }

    private List<Cell> reconstructPath(Cell current) {
        List<Cell> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private int heuristic(Cell a, Cell b) {
        // Manhattan distance
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Down, Up, Right, Left
        for (int[] dir : directions) {
            int nx = cell.getX() + dir[0];
            int ny = cell.getY() + dir[1];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && canMove(cell, cells[nx][ny])) {
                neighbors.add(cells[nx][ny]);
            }
        }
        return neighbors;
    }

    private boolean canMove(Cell from, Cell to) {
        // Determine the direction of movement
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        // Moving to the right
        if (dx == 1) {
            return !from.hasRightWall() && !to.hasLeftWall();
        }
        // Moving to the left
        else if (dx == -1) {
            return !from.hasLeftWall() && !to.hasRightWall();
        }
        // Moving down
        else if (dy == 1) {
            return !from.hasBottomWall() && !to.hasTopWall();
        }
        // Moving up
        else if (dy == -1) {
            return !from.hasTopWall() && !to.hasBottomWall();
        }

        // Should not reach here for adjacent cells
        return false;
    }

    private int distBetween(Cell from, Cell to) {
        return 1; // Assuming each move has a constant cost, there are no diagonal cells in the app
    }
}
