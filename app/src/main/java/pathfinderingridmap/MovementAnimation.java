package pathfinderingridmap;

import processing.core.PApplet;

import java.util.ArrayList;

public class MovementAnimation {
    PApplet pApplet;
    final Chicken chicken;
    final Pathfinder pathfinder;
    final Food food;
    int stepIndex = 0;
    float speed = 15f;

    public MovementAnimation(PApplet pApplet1, Chicken chicken, Pathfinder pathfinder, Food food) {
        pApplet=pApplet1;
        this.chicken = chicken;
        this.pathfinder = pathfinder;
        this.food = food;
    }

    public void update() {
        ArrayList<Cell> path = pathfinder.path;
        if (path.isEmpty() || stepIndex >= path.size()) {
            return;
        }
        Cell targetCell = path.get(stepIndex);
        float targetX = targetCell.x * chicken.gridMap.cellSize + chicken.gridMap.cellSize / 2f;
        float targetY = targetCell.y * chicken.gridMap.cellSize + chicken.gridMap.cellSize / 2f;
        float dx = targetX - chicken.x;
        float dy = targetY - chicken.y;
        float distance = pApplet.dist(chicken.x, chicken.y, targetX, targetY);
        if (distance < 0.01f) {
            chicken.snapToCell(targetCell);
            handleArrival(targetCell);
            stepIndex++;
            return;
        }
        //float delta = pApplet.frameRate > 0 ? 1.0f / pApplet.frameRate : 1.0f / 60f;
       // float step = speed * delta;
        float step = speed;
        float moveX = (dx / distance) * pApplet.min(step, distance);
        float moveY = (dy / distance) * pApplet.min(step, distance);
        chicken.moveBy(moveX, moveY);
    }

    void handleArrival(Cell arrived) {
        if (food.isAt(arrived)) {
            food.consume();
            pathfinder.path.clear();
            stepIndex = 0;
        }
    }

    public void drawChicken() {
        pApplet.stroke(255, 255, 0);
        pApplet.noFill();
        pApplet.strokeWeight(2);
        pApplet.beginShape();
        for (Cell c : pathfinder.path) {
            pApplet.vertex(c.x * chicken.gridMap.cellSize + chicken.gridMap.cellSize / 2f,
                    c.y * chicken.gridMap.cellSize + chicken.gridMap.cellSize / 2f);
        }
        pApplet.endShape();
        pApplet.strokeWeight(1);

        pApplet.fill(255, 205, 100);
        pApplet.stroke(140, 85, 0);
        pApplet.strokeWeight(2);
        pApplet.ellipse(chicken.x, chicken.y, chicken.gridMap.cellSize * 0.8f, chicken.gridMap.cellSize * 0.8f);
        pApplet.strokeWeight(1);
    }

    public void resetStepIndex() {
        stepIndex = 0;
        chicken.recenter();
    }
}

