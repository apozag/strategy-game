/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Vector2i;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    private float speed = 2.5f;
    
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
        if(steps.isEmpty())
            targetCell = null;
        this.steps = steps;
    }
    
    public Vector2i pollNextStep(){
        if(steps.isEmpty())
            return null;
        return steps.remove(0);
    }
    
    public Vector2i peekNextStep(){
        if(steps.isEmpty())
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
    
    public Vector2i getCloseCell(){
        if(MapInfo.getInstance().getPeopleLayerCell(current)){
            Vector2i neighbors[] = {new Vector2i(1, 0), new Vector2i(-1, 0), 
                                    new Vector2i(0, 1), new Vector2i(0,-1), 
                                    new Vector2i(1, -1), new Vector2i(-1, 1), 
                                    new Vector2i(0,0),new Vector2i(1, 1) };
            
            int mapW = MapInfo.getInstance().getWidth();
            int mapH = MapInfo.getInstance().getHeight();
            
            for(int i = 0; i < 8; i++){
                Vector2i n = Vector2i.add(current, neighbors[i]);
                if(!(n.col < 0 || n.col >= mapW || n.row < 0 || n.row >= mapH) && MapInfo.getInstance().getTileWalkCost(n) >= 0){
                    return n;
                }
            }
        }
        return current;
    }
}

