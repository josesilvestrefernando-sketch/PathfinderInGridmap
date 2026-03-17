package pathfinderingridmap;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Pathfinder {
    PApplet pApplet;
    final GridMap gridMap;
    ArrayList<Cell> path = new ArrayList<>();

    public Pathfinder(PApplet pApplet1, GridMap gridMap) {
        pApplet=pApplet1;
        this.gridMap = gridMap;
    }

    /**
     * <p>
     *     <b>computePath</b> - 1. Reset pathfinding metadata: Before running the algorithm, every cell clears its
     *     previous A\* scores (`f`, `g`, `h`) and parent pointer. 2. Prepare search structures: The open set is a
     *     priority queue that always expands the node with the lowest `f = g + h`; the closed set keeps track of
     *     already evaluated nodes. 3. Initialize start node**: Set the start node’s `g` cost to 0, compute its
     *     heuristic to the goal, set its `f`, and push it into the open set. 4. Main loop: While there are nodes
     *     to explore, pop the best candidate (lowest `f`). If it’s the goal, reconstruct the path. 5. Neighbor
     *     evaluation: For each adjacent cell, skip if it’s an obstacle or already evaluated. Otherwise calculate a
     *     tentative `g` (current cost + 1). 6. Path relaxation: If this path to the neighbor is better than any
     *     previous one, update its `cameFrom`, `g`, `h`, `f`, and ensure it’s in the open set.
     * </p>
     */
    public void computePath() {
        // Reset all pathfinding data on each cell before starting the search
        for (int x = 0; x < gridMap.cols; x++) {
            for (int y = 0; y < gridMap.rows; y++) {
                gridMap.cells[x][y].resetPathfinding();
            }
        }

        // Priority queue (min-heap) where the cell with the lowest f value is expanded first
        PriorityQueue<Cell> openSet = new PriorityQueue<>();
        // Set of cells that have been fully evaluated
        HashSet<Cell> closedSet = new HashSet<>();
        Cell start = gridMap.startCell;
        Cell goal = gridMap.foodCell;

        // Initialize the start node scores
        start.g = 0;
        start.h = heuristic(start, goal);
        start.f = start.h;
        openSet.add(start);
        path.clear();

        // Continue until there are no more nodes to examine
        while (!openSet.isEmpty()) {
            // Get the node with the lowest estimated total cost f = g + h
            Cell current = openSet.poll();
            // If we reached the goal, build the path and return
            if (current == goal) {
                reconstructPath(current);
                return;
            }
            // Mark the current node as evaluated
            closedSet.add(current);
            // Check each neighbor of the current node
            for (Cell neighbor : gridMap.getNeighbors(current)) {
                // Skip neighbor if it's an obstacle or already evaluated
                if (neighbor.isObstacle || closedSet.contains(neighbor)) continue;
                // Tentative g score is the cost to reach neighbor through current
                float tentativeG = current.g + 1;
                // If this is a better path to neighbor, or neighbor hasn't been seen yet
                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    // Record how we arrived at this neighbor
                    neighbor.cameFrom = current;
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    // Make sure the neighbor is in the open set for future expansion
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

    }

    void reconstructPath(Cell end) {
        path.clear();
        Cell current = end;
        while (current != null) {
            path.add(0, current);
            current = current.cameFrom;
        }
    }

    float heuristic(Cell a, Cell b) {
        return pApplet.abs(a.x - b.x) + pApplet.abs(a.y - b.y);
    }
}

