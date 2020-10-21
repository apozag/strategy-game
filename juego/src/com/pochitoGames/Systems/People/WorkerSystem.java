/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.Visibility;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Engine.System;

/**
 *
 * @author PochitoMan
 */
public class WorkerSystem extends System {
    
    boolean start = false;
    
    public WorkerSystem(){
        include(Worker.class, Position.class,  PathFinding.class, Visibility.class, Human.class);
        exclude();
    }

    @Override
    public void update(double dt) {
     
    } 
}
