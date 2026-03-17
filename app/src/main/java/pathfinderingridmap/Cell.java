package pathfinderingridmap;

import processing.core.PApplet;

import java.util.ArrayList;

public class Cell implements Comparable<Cell> {
    PApplet pApplet;
    final int x;
    final int y;
    final int size;
    boolean isObstacle = false;
    boolean isStart = false;
    boolean isFood = false;

    /**
     * <p>
     *     <b>f</b> - Total estimated cost of a path through this cell (f = g + h). Is the sum of `g` and `h`, and
     *     the priority queue expands the cell with the lowest `f`, balancing exploration of the already-traveled path
     *     (`g`) with the remaining distance (`h`).
     * </p>
     */
    float f = 0;
    /**
     * <p>
     *     <b>g</b> - Cost from the start cell to this cell along the path taken so far. Grows as the algorithm
     *     explores farther from the start: each step adds a fixed cost (1 in this grid) so that it reflects distance
     *     traveled.
     * </p>
     */
    float g = 0;
    /**
     * <p>
     *     <b>h</b> - Heuristic estimate of the cost from this cell to the goal (food). Predicts how close the cell is
     *     to the goal, using the Manhattan distance (`abs(dx) + abs(dy)`),
     *     guiding the search toward the food.
     * </p>
     */
    float h = 0;
    Cell cameFrom;

    Cell(PApplet pApplet1, int x, int y, int size) {
        pApplet = pApplet1;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    void drawCell() {
        pApplet.stroke(70);
        if (isObstacle) {
            pApplet.fill(180);
        }  else if (isFood) {
            pApplet.fill(255, 180, 0);
        } else {
            pApplet.fill(40);
        }
        pApplet.rect(x * size, y * size, size, size);
    }

    void resetState() {
        isObstacle = false;
        isStart = false;
        isFood = false;
        resetPathfinding();
    }

    void resetPathfinding() {
        f = 0;
        g = Float.MAX_VALUE;
        h = 0;
        cameFrom = null;
    }

    @Override
    public int compareTo(Cell other) {
        return Float.compare(f, other.f);
    }
}


