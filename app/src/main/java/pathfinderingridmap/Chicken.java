package pathfinderingridmap;

public class Chicken {
    final GridMap gridMap;
    Cell currentCell;
    float x;
    float y;

    public Chicken(GridMap gridMap) {
        this.gridMap = gridMap;
        this.currentCell = null;
        snapToCell(gridMap.startCell);
    }

    void snapToCell(Cell cell) {
        if (cell == null) return;
        if (currentCell != null) {
            currentCell.isStart = false;
        }
        currentCell = cell;
        currentCell.isStart = true;
        gridMap.startCell = cell;
        centerOnCell(cell);
    }

    void centerOnCell(Cell cell) {
        x = cell.x * gridMap.cellSize + gridMap.cellSize / 2f;
        y = cell.y * gridMap.cellSize + gridMap.cellSize / 2f;
    }

    void moveBy(float dx, float dy) {
        x += dx;
        y += dy;
    }

    void recenter() {
        if (currentCell != null) {
            centerOnCell(currentCell);
        }
    }
}

