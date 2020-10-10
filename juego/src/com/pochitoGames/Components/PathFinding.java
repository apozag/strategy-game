/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Vector2i;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author PochitoMan
 */
public class PathFinding extends Component{
    Vector2i current;
    Queue<Vector2i> steps;
    Vector2D nextPos;
    private Vector2i targetCell;
    private boolean walking;
    
    public PathFinding(int col, int row){
        current = new Vector2i(col, row);
        steps = new LinkedList();
    }
    public void setCurrent(Vector2i cell){
        this.current = cell;
    }

    public Vector2i getCurrent(){
        return current;
    }
    
    public void addStep(int col, int row){
        steps.add(new Vector2i(col, row));
    }
    
    public void setSteps(Queue<Vector2i> steps){
        this.steps = steps;
    }
    
    public Vector2i pollNextStep(){
        return steps.poll();
    }
    
    public Vector2i peekNextStep(){
        return steps.peek();
    }
    
    public void setNextPos(Vector2D pos){
        nextPos = pos;
    }
    
    public Vector2D getNextPos(){
        return nextPos;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public Vector2i getTargetCell() { return targetCell; }

    public void setTargetCell(Vector2i targetCell) { this.targetCell = targetCell; }
}

