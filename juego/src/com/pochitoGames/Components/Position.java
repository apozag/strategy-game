/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

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
    
    boolean dirty = false;
    
    public Position(Vector2D pos){
        this.localPos = pos;
    }
    
    public Position(Vector2D pos, boolean lock){
        this.localPos = pos;
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
        Entity parent = getEntity().getParent();
        if(parent != null && dirty){
            worldPos = Vector2D.add(localPos , ((Position)(parent.get(Position.class))).getWorldPos());
        }
        else{
            return new Vector2D(localPos);
        }
        dirty = false;
        return new Vector2D(worldPos);
    }
    
    private void setDirtyFlag(){
        dirty = true;
        for(Entity child : getEntity().getChildren()){
            ((Position)(child.get(Position.class))).setDirtyFlag();
        }
    }
    
    public boolean isLocked(){
        return lock;
    }
}
