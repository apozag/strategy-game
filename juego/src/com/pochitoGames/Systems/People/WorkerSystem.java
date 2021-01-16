/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.Visibility;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.WorkerState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;
import java.util.List;

/**
 * @author PochitoMan
 */
public class WorkerSystem extends System {

    public WorkerSystem() {
        include(Worker.class, Position.class, PathFinding.class, Human.class);
        exclude(Builder.class);
    }

    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            PathFinding pf = e.get(PathFinding.class);
            Worker worker = e.get(Worker.class);
            WorkerState state = worker.getState();
            switch (state) {
                case WAIT:
                    //Estamos parados hasta que nos requieran (Los edificios nos llamen)
                    break;
                case SEARCH_RESOURCE:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        //Cojo el edificio (El componente Warehouse solo)
                        Warehouse wh = worker.getTargetBuilding().getEntity().get(Warehouse.class);
                        // Si no queda recurso, paso del tema y le aviso al builder
                        if(!wh.hasResource(worker.getResourceNeeded())){
                            worker.getTargetMate().hasWorker = false;
                            worker.setState(WorkerState.WAIT);
                            break;
                        }

                        //Cojo al compañero al que le tengo que llevar el recurso
                        Builder mate = worker.getTargetMate();
                        //Cojo su pathfinding para saber su casilla
                        PathFinding mpf = mate.getEntity().get(PathFinding.class);
                        //Le digo a MI pathfinding que vaya a la casilla del compa
                        pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), MapInfo.getInstance().getCloseCell(mpf.getCurrent(), true, false), e.getId(), false));
                        //Si hay camino, me pongo en estado CARRY_RESOURCE y voy
                        if (pf.getSteps() != null) {                            
                            wh.takeContent(worker.getResourceNeeded(), 1);
                            worker.setCarrying(worker.getResourceNeeded());
                            if (pf.getSteps().size() > 0) {
                                pf.getSteps().remove(pf.getSteps().size() - 1);
                            }
                            if (pf.getSteps().size() > 0) {
                                pf.setTargetCell(pf.getSteps().get(pf.getSteps().size() - 1));
                            }
                            worker.setState(WorkerState.CARRY_RESOURCE);
                        }
                        // Si no hay camino, paso del tema y le aviso al builder
                        else{
                            mate.hasWorker = false;
                            worker.setState(WorkerState.WAIT);
                        }
                    }
                    break;
                case CARRY_RESOURCE:
                    //Si he llegado al compañero
                    if (pf.getTargetCell() == null) {
                        //Cojo al compa
                        Builder mate = worker.getTargetMate();
                        //Cojo el edificio que está construyendo mi compa (getTargetBuilding)
                        //Y le meto (putResources) una unidad del recurso que necesita (getResourcesNeeded)
                        mate.getTargetBuilding().putResources(worker.getCarrying(), 1);
                        mate.hasWorker = false;
                        //Me pongo en WAIT
                        worker.setState(WorkerState.WAIT);
                    }
                    break;

                case TAKING_RESOURCE_FROM_BUILDING:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        
                        // Cojo el edificio al que tengo que llevar el recurso
                        Building newTargetBuilding = BuildingManager.getInstance().getNearestWarehousePut(pf.getCurrent(), worker.getResourceNeeded(), worker.getTargetBuilding().getTypeBuilding(), null);

                        //Si hay edificio de ese tipo
                        if (newTargetBuilding != null) {
                            pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), newTargetBuilding.getEntryCell(), e.getId(), false));
                            // SI hay camino, le quito el recurso al src y me pongo en marcha
                            if (pf.getSteps() != null) {
                                Warehouse wh = worker.getTargetBuilding().getEntity().get(Warehouse.class);
                                wh.takeContent(worker.getResourceNeeded(), 1);
                                pf.getSteps().remove(pf.getSteps().size() - 1);
                                pf.setTargetCell(pf.getSteps().get(pf.getSteps().size() - 1));
                                worker.setTargetBuilding(newTargetBuilding);
                                worker.setCarrying(worker.getResourceNeeded());
                                worker.setState(WorkerState.CARRY_RESOURCE_TO_WAREHOUSE);
                            }
                            // Si no hay camino, paso del tema. Se lo indico al srcWh tambien.
                            else{
                                worker.getSrcWarehouse().hasWorker = false;
                                worker.setState(WorkerState.WAIT);
                            }
                        }
                        // Si no lo he encontrado, paso del tema para no quedarme pillado
                        // Se lo indico al srcWh
                        else{
                            worker.getSrcWarehouse().hasWorker = false;
                            worker.setState(WorkerState.WAIT);
                        }
                    }
                    break;
                case CARRY_RESOURCE_TO_WAREHOUSE:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        //Cojo el edificio (El componente Warehouse solo)
                        Warehouse wh = worker.getTargetBuilding().getEntity().get(Warehouse.class);
                        //Le pongo una unidad del recurso
                        wh.putContent(worker.getCarrying(), 1);
                        //Ya he terminado por ahora
                        worker.setState(WorkerState.WAIT);
                        if(worker.getSrcWarehouse() != null){
                            worker.getSrcWarehouse().hasWorker = false;
                            worker.setSrcWarehouse(null);
                        }
                    }
            }
        }
    }
}
