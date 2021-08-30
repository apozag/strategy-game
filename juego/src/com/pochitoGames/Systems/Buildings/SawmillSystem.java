/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Sawmill;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.WorkerState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;
/**
 *
 * @author PochitoMan
 */
public class SawmillSystem extends System{

    public SawmillSystem(){
        include(Building.class, Sawmill.class, Warehouse.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Building b = e.get(Building.class);
            if(b.isFinished()){
                Sawmill s = e.get(Sawmill.class);
                Warehouse wh = e.get(Warehouse.class);
                if(wh.getContent(ResourceType.RAW_WOOD) >= 1){
                    if(java.lang.System.currentTimeMillis() - s.getLastTime() > s.getFrequency()){
                        wh.putContent(ResourceType.WOOD, 1);
                        wh.takeContent(ResourceType.RAW_WOOD, 1);
                        s.setLastTime(java.lang.System.currentTimeMillis());
                    }
                }
                else{
                    s.setLastTime(java.lang.System.currentTimeMillis());
                }
            }
        }
    }
    
}
