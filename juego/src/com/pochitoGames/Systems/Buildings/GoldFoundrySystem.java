/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.GoldFoundry;
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
public class GoldFoundrySystem extends System{

    public GoldFoundrySystem(){
        include(Building.class, GoldFoundry.class, Warehouse.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Building b = e.get(Building.class);
            if(b.isFinished()){
                GoldFoundry gf = e.get(GoldFoundry.class);
                Warehouse wh = e.get(Warehouse.class);                
                if(wh.getContent(ResourceType.RAW_GOLD) > 0){
                    if(java.lang.System.currentTimeMillis() - gf.getLastTime() > gf.getFrequency()){
                        gf.setLastTime(java.lang.System.currentTimeMillis());
                        wh.takeContent(ResourceType.RAW_GOLD, 1);
                        wh.putContent(ResourceType.GOLD, 1);
                    }
                }else{
                    gf.setLastTime(java.lang.System.currentTimeMillis());
                }
                /*
                if(wh.getContent(ResourceType.GOLD) > 3 && !wh.hasWorker){
                    Worker w = PeopleManager.getInstance().getNearestWorker(b.getOwnerType(), b.getEntryCell());
                    if(w != null){ 
                        PathFinding pf = w.getEntity().get(PathFinding.class);
                        pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), b.getEntryCell(), w.getEntity().getId(), false));
                        if(pf.getSteps() != null){
                            wh.hasWorker = true;
                            w.setTargetBuilding(b);
                            w.setResourceNeeded(ResourceType.GOLD);
                            w.setState(WorkerState.TAKING_RESOURCE_FROM_BUILDING);
                            w.setSrcWarehouse(wh);
                            pf.setTargetCell(b.getEntryCell());
                        }
                    }
                }
                */
            }
        }
    }
    
}
