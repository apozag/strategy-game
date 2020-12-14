/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Vector2D;
/**
 *
 * @author PochitoMan
 */
public class Position extends Component{
    private Vector2D localPos;
    private Vector2D worldPos;
    
    boolean lock = false;
    
    boolean dirty = true;
    
    public Position(Vector2D pos){
        this.localPos = pos;
        this.worldPos = pos;
    }
    
    public Position(Vector2D pos, boolean lock){
        this.localPos = pos;
        this.worldPos = pos;
        this.lock = lock;
    }
    
    public Vector2D getLocalPos(){
        return new Vector2D(localPos);
    }
    
    public void setLocalPos(Vector2D pos){
        this.localPos = pos;
        setDirtyFlag();
    }
    
    public Vector2D getWorldPos(){
        if(dirty){
            worldPos.x = localPos.x;
            worldPos.y = localPos.y;
            Entity parent = getEntity().getParent();
            if(parent != null){
                Position parentPos = parent.get(Position.class);
                worldPos.add(parentPos.getWorldPos());
            }
            dirty = false;
        }
        return new Vector2D(worldPos);
    }
    
    private void setDirtyFlag(){
        dirty = true;
        for(Entity child : getEntity().getChildren()){
            Position childPosition = child.get(Position.class);
            childPosition.setDirtyFlag();
        }
    }
    
    public boolean isLocked(){
        return lock;
    }
}
