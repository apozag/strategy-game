/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.LumberjackHut;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.People.LumberJack;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.LumberJackState;
import com.pochitoGames.Misc.States.WorkerState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;

/**
 *
 * @author PochitoMan
 */
public class LumberjackHutSystem extends System{

    public LumberjackHutSystem(){
        include(LumberjackHut.class, Building.class, Warehouse.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Building b = e.get(Building.class);                
            LumberjackHut ljh = e.get(LumberjackHut.class);            
            if(b.isFinished()){
                if(ljh.getLumberjack() == null){
                    LumberJack lj = PeopleManager.getInstance().getNearestLumberjack(b.getOwnerType(), b.getCell());
                    if(lj != null && lj.getHut() == null){
                        ljh.setLumberjack(lj);
                        PathFinding pf = lj.getEntity().get(PathFinding.class);
                        pf.setSteps(PathFindingSystem.aStar(pf.getCurrent(), b.getEntryCell(), lj.getEntity().getId(),false));
                        if(pf.getSteps() != null){
                            lj.setHut(e);
                            pf.setTargetCell(b.getEntryCell());
                            lj.setState(LumberJackState.WALKING_HUT);
                        }
                    }
                }
                else{
                    /*
                    Warehouse wh = e.get(Warehouse.class);
                    Building building = e.get(Building.class);
                    
                    if(wh.getContent(ResourceType.RAW_WOOD) >= 1 && !wh.hasWorker){
                        Worker worker = PeopleManager.getInstance().getNearestWorker(building.getOwnerType(), building.getCell());
                        if(worker != null){
                            PathFinding pf = worker.getEntity().get(PathFinding.class);
                            pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), building.getEntryCell(), worker.getEntity().getId(), false));
                            if(pf.getSteps() != null){
                                wh.hasWorker = true;
                                pf.setTargetCell(building.getEntryCell());
                                worker.setState(WorkerState.TAKING_RESOURCE_FROM_BUILDING);
                                worker.setTargetBuilding(building);
                                worker.setResourceNeeded(ResourceType.RAW_WOOD);
                                worker.setSrcWarehouse(wh);
                            }
                    }
                    */
                }
            }
        }
    }
    
}

