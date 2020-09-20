/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;

import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Components.Worker;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.MouseEventData;
import com.pochitoGames.Engine.MouseEventType;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import java.awt.event.MouseEvent;
/**
 *
 * @author PochitoMan
 */
public class WorkerSystem extends System {
    
    boolean start = false;
    
    public WorkerSystem(){
        include(Worker.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        
        for(Entity e : getEntities()){
            if(!start){
                for(MouseEventData data : EventManager.getInstance().getMouseEvents()){
                    if(data.getType() == MouseEventType.LEFT_CLICK && SpriteSystem.isInsideSprite((Sprite)(e.get(Sprite.class)), data.getPosition()))
                        start = !start;
                }
            }
            else{
                Position position = ((Position)e.get(Position.class));
                position.setLocalPos(Vector2D.add(position.getLocalPos(), new Vector2D(1.0f, 1.0f)));
            }
        }       
    } 
}
