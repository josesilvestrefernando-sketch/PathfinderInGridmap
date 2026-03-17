package pathfinderingridmap;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class GridMap {
    PApplet pApplet;
    final int cols;
    final int rows;
    final int cellSize;
    Cell[][] cells;
    Cell startCell;
    Cell foodCell;
    int obstacleCount = 60;

    public GridMap(PApplet pApplet1, int cols, int rows, int cellSize) {
        pApplet = pApplet1;
        this.cols = cols;
        this.rows = rows;
        this.cellSize = cellSize;
        cells = new Cell[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y] = new Cell(pApplet,x, y, cellSize);
            }
        }
    }

    public void placeInitialEntities() {
        startCell = cells[2][2];
      startCell.isStart = true;
        foodCell = cells[cols - 3][rows - 3];
        foodCell.isFood = true;
        placeObstacles(obstacleCount);
    }

    void placeObstacles(int count) {
        Random rand = new Random();
        int placed = 0;
        while (placed < count) {
            int x = rand.nextInt(cols);
            int y = rand.nextInt(rows);
            Cell c = cells[x][y];
            if (!c.isStart && !c.isFood && !c.isObstacle) {
                c.isObstacle = true;
                placed++;
            }
        }
    }

    public void drawGrid() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y].drawCell();
            }
        }
    }

    public void reset() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y].resetState();
            }
        }
    }

    public void tryPlaceFood(int px, int py) {
        int gridX = px / cellSize;
        int gridY = py / cellSize;
        if (gridX >= 0 && gridX < cols && gridY >= 0 && gridY < rows) {
            Cell target = cells[gridX][gridY];
            if (!target.isObstacle && !target.isStart) {

                foodCell = target;
                foodCell.isFood = true;
            }
        }
    }

    ArrayList<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int nx = cell.x + dx[i];
            int ny = cell.y + dy[i];
            if (nx >= 0 && nx < cols && ny >= 0 && ny < rows) {
                neighbors.add(cells[nx][ny]);
            }
        }
        return neighbors;
    }

    Cell getCell(int x, int y) {
        return cells[x][y];
    }
}

