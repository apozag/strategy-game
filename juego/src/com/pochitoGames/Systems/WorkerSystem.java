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
     
    } 
}
