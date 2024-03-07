package org.models;

public class Cell {
    private int x;
    private int y;
    private boolean topWall = true;
    private boolean rightWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;
    private boolean visited = false;
    private boolean current = false;

    // For A*
    public int fScore;
    public int gScore;
    public Cell parent;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, boolean topWall, boolean rightWall, boolean bottomWall, boolean leftWall) {
        this.x = x;
        this.y = y;
        this.topWall = topWall;
        this.rightWall = rightWall;
        this.bottomWall = bottomWall;
        this.leftWall = leftWall;
    }

    public boolean hasTopWall() { return topWall; }
    public boolean hasRightWall() { return rightWall; }
    public boolean hasBottomWall() { return bottomWall; }
    public boolean hasLeftWall() { return leftWall; }

    public void removeTopWall() { this.topWall = false; }
    public void removeRightWall() { this.rightWall = false; }
    public void removeBottomWall() { this.bottomWall = false; }
    public void removeLeftWall() { this.leftWall = false; }

    public boolean isVisited() {
        return visited;
    }
    public boolean isCurrent() {
        return current;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
