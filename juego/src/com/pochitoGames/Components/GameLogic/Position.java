/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Vector2D;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 *
 * @author PochitoMan
 */
public class Position extends Component{
    private Vector2D localPos;
    private Vector2D worldPos;
    
    boolean lock = false;
    
    boolean dirty = true;
    
    public Position(Node node){
        Element element = (Element) node;
        
        String x = element.getElementsByTagName("x").item(0).getTextContent();
        String y = element.getElementsByTagName("y").item(0).getTextContent();
        localPos = new Vector2D(Float.parseFloat(x), Float.parseFloat(y));
        worldPos = new Vector2D(localPos);
        
        NodeList nodelist = element.getElementsByTagName("fixed");
        if(nodelist.getLength() > 0)
            lock = nodelist.item(0).getTextContent().equals("TRUE");
    }
    
    public Position(Vector2D pos){
        this.localPos = new Vector2D(pos);
        this.worldPos = new Vector2D(pos);
    }
    
    public Position(Vector2D pos, boolean lock){
        this.localPos = new Vector2D(pos);
        this.worldPos = new Vector2D(pos);
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
