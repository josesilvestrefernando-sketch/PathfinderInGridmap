package com.josil.pathfinderingridmap;


import pathfinderingridmap.Chicken;
import pathfinderingridmap.Food;
import pathfinderingridmap.GridMap;
import pathfinderingridmap.MovementAnimation;
import pathfinderingridmap.Pathfinder;
import processing.core.PApplet;

public class Main extends PApplet {


    int CELL_SIZE, totalwidth, tolaheight, GRID_SIZE_W, GRID_SIZE_H;
    GridMap gridMap;
    Pathfinder pathfinder;
    Chicken chicken;
    Food food;
    MovementAnimation movementAnimation;

    public void settings() {

        fullScreen();
    }


    public void setup() {
        CELL_SIZE = 50;
        totalwidth = width / CELL_SIZE;
        tolaheight = height / CELL_SIZE;
        GRID_SIZE_W = totalwidth;
        GRID_SIZE_H = (int) (tolaheight);
      
        resetGame();
    }

    public void draw() {
        background(30);
        gridMap.drawGrid();
        movementAnimation.update();
        movementAnimation.drawChicken();
    }

    void resetGame() {
        gridMap = new GridMap(this, GRID_SIZE_W, GRID_SIZE_H, CELL_SIZE);
        gridMap.placeInitialEntities();

        chicken = new Chicken(gridMap);
        food = new Food(gridMap);

        food.placeAt((int) random(GRID_SIZE_W - 1), (int) random(GRID_SIZE_H - 1));

        pathfinder = new Pathfinder(this, gridMap);
        pathfinder.computePath();
        movementAnimation = new MovementAnimation(this, chicken, pathfinder, food);
    }


    public void mousePressed() {
        boolean placed = food.relocate(mouseX, mouseY);
        if (placed) {
            pathfinder.computePath();
            movementAnimation.resetStepIndex();
        }
    }

}


