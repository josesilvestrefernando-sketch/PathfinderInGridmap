package pathfinderingridmap;

public class Food {
    final GridMap gridMap;
    Cell currentCell;
    boolean available = false;

    public Food(GridMap gridMap) {
        this.gridMap = gridMap;
        placeAt(gridMap.foodCell);
    }

    void placeAt(Cell cell) {
        if (cell == null) return;
        cell.isFood = true;
        currentCell = cell;
        gridMap.foodCell = cell;
        available = true;
    }

    public boolean relocate(int px, int py) {
        int gridX = px / gridMap.cellSize;
        int gridY = py / gridMap.cellSize;
        if (gridX < 0 || gridX >= gridMap.cols || gridY < 0 || gridY >= gridMap.rows) {
            return false;
        }
        Cell target = gridMap.cells[gridX][gridY];
        if (target.isObstacle || target.isStart) {
            return false;
        }
        if (currentCell != null) {
            currentCell.isFood = false;
        }
        placeAt(target);
        return true;
    }
   public void placeAt(int x1, int y1) {
       gridMap.foodCell.isFood=false;
       placeAt(gridMap.cells[x1][y1]);
    }
    boolean isAt(Cell cell) {
        return available && cell == currentCell;
    }

    void consume() {
        if (currentCell != null) {
            currentCell.isFood = false;
            currentCell = null;
        }
        gridMap.foodCell = null;
        available = false;
    }
}

