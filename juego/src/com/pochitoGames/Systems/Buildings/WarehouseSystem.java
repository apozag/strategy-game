/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.WorkerState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;

/**
 *
 * @author PochitoMan
 */
public class WarehouseSystem extends System {

    public WarehouseSystem() {
        include(Warehouse.class, Building.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            Building building = e.get(Building.class);
            if (building.isFinished()) {
                Warehouse wh = e.get(Warehouse.class);
                for (ResourceType type : wh.getTransferableTypes()) {
                    if (wh.getContent(type) > 0 && !wh.hasWorker) {
                        Worker worker = PeopleManager.getInstance().getNearestWorker(building.getOwnerType(), building.getCell());
                        if (worker != null) {
                            PathFinding pf = worker.getEntity().get(PathFinding.class);
                            Building targetBuilding = BuildingManager.getInstance().getNearestWarehousePut(building.getEntryCell(), type, building.getTypeBuilding(), null);
                            if (targetBuilding != null) {
                                pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), building.getEntryCell(), worker.getEntity().getId(), false));
                                if (pf.getSteps() != null
                                        && PathFindingSystem.aStarFloor(building.getEntryCell(), targetBuilding.getEntryCell(), worker.getEntity().getId(), false) != null) {
                                    wh.hasWorker = true;
                                    pf.setTargetCell(building.getEntryCell());
                                    worker.setState(WorkerState.TAKING_RESOURCE_FROM_BUILDING);
                                    worker.setTargetBuilding(building);
                                    worker.setResourceNeeded(type);
                                    worker.setSrcWarehouse(wh);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
