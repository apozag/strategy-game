/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Engine.Vector2i;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */
public class PathFinding extends Component{
    Vector2i current;
    List<Vector2i> steps;
    Vector2D nextPos;
    private Vector2i targetCell;
    private boolean walking;
    private boolean busy;
    private float speed = 100.0f;
    private boolean start;
    
    public PathFinding(Vector2i cell){
        current = cell;
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
    
    public void setSteps(List<Vector2i> steps){
        if(steps == null || steps.isEmpty())
            targetCell = null;
        else
            targetCell = steps.get(steps.size()-1);
        this.steps = steps;
    }
    
    public Vector2i pollNextStep(){
        if(steps == null || steps.isEmpty())
            return null;
        return steps.remove(0);
    }
    
    public Vector2i peekNextStep(){
        if(steps == null || steps.isEmpty())
            return null;
        return steps.get(0);
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
    
    public List<Vector2i> getSteps(){return steps;}
    
    public float getSpeed(){return speed;}

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    public void start(){
        start = true;
    }
    
    public boolean started(){
        return start;
    }
    
    public void reset(){
        steps = null;
        targetCell = null;
        start = false;
    }

}

