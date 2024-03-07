package org.models;

import org.models.Cell;

public interface IMazeGenerator {
    int getWidth();
    int getHeight();
    Cell[][] getCells();
}
