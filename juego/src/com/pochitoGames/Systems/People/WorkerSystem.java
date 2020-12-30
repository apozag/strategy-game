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
import com.pochitoGames.Misc.Other.ResourceType;
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
            Worker c = e.get(Worker.class);
            WorkerState state = c.getState();
            switch (state) {
                case WAIT:
                    //Estamos parados hasta que nos requieran (Los edificios nos llamen)
                    break;
                case SEARCH_RESOURCE:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        if(c.getResourceNeeded() != null){
                            //Cojo el edificio (El componente Warehouse solo)
                            Warehouse wh = c.getTargetBuilding().getEntity().get(Warehouse.class);
                            //Le quito una unidad del recurso
                            wh.takeContent(c.getResourceNeeded(), 1);
                            c.setCarrying(c.getResourceNeeded());
                        }
                        c.setResourceNeeded(null);
                        
                        //Cojo al compañero al que le tengo que llevar el recurso
                        Builder mate = c.getTargetMate();
                        //Cojo su pathfinding para saber su casilla
                        PathFinding mpf = mate.getEntity().get(PathFinding.class);
                        //Le digo a MI pathfinding que vaya a la casilla del compa
                        pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), mpf.getCurrent(), e.getId(), false));  
                        //Me pongo en estado CARRY_RESOURCE
                        if(pf.getSteps() != null){
                            pf.getSteps().remove(pf.getSteps().size() - 1);
                            if(pf.getSteps().size() > 0)
                                pf.setTargetCell(pf.getSteps().get(pf.getSteps().size() - 1));
                            c.setState(WorkerState.CARRY_RESOURCE);
                        }
                        //Ahora empezará a andar solito hacia el compañero
                    }
                    break;
                case CARRY_RESOURCE:
                    //Si he llegado al compañero
                    if (pf.getTargetCell() == null) {
                        //Cojo al compa
                        Builder mate = c.getTargetMate();
                        //Cojo el edificio que está construyendo mi compa (getTargetBuilding)
                        //Y le meto (putResources) una unidad del recurso que necesita (getResourcesNeeded)
                        mate.getTargetBuilding().putResources(c.getCarrying(), 1);
                        //Pongo al compa de vuelta al estado BUILDING (estaba en ON_HOLD)
                        mate.setState(BuilderState.BUILD);
                        //Me pongo en WAIT
                        c.setState(WorkerState.WAIT);
                    }
                    break;

                case TAKING_RESOURCE_FROM_BUILDING:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        if(c.getResourceNeeded() != null){
                            Warehouse wh = c.getTargetBuilding().getEntity().get(Warehouse.class);
                            wh.takeContent(c.getResourceNeeded(), 1);
                            c.setCarrying(c.getResourceNeeded());
                        }
                        // Vemos a que edificio ir a guardarlo dependiendo del recurso
                        //Building newTargetBuilding = null;
                        /*
                        switch(c.getCarrying()){
                            case RAW_STONE:
                                newTargetBuilding = BuildingManager.getInstance().getNearestBuilding(pf.getCurrent(), TypeBuilding.REFINERY);
                                break;
                            default: 
                                newTargetBuilding = BuildingManager.getInstance().getNearestBuilding(pf.getCurrent(), TypeBuilding.CASTLE);
                                break;
                        }
                        */
                        Building newTargetBuilding = BuildingManager.getInstance().getNearestWarehousePut(pf.getCurrent(), c.getCarrying(), TypeBuilding.QUARRY, null);
                        c.setResourceNeeded(null);
                        
                        //Me pongo el pathfinding a ese edificio si lo he encontrado
                        if(newTargetBuilding != null){                            
                            pf.setSteps(PathFindingSystem.aStarFloor(pf.getCurrent(), newTargetBuilding.getEntryCell(), e.getId(), false));
                            if(pf.getSteps() != null){
                                pf.getSteps().remove(pf.getSteps().size() - 1);
                                pf.setTargetCell(pf.getSteps().get(pf.getSteps().size() - 1));
                                c.setTargetBuilding(newTargetBuilding);
                                c.setState(WorkerState.CARRY_RESOURCE_TO_WAREHOUSE);
                            }
                        }
                    }
                    break;
                case CARRY_RESOURCE_TO_WAREHOUSE:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        //Cojo el edificio (El componente Warehouse solo)
                        Warehouse wh = c.getTargetBuilding().getEntity().get(Warehouse.class);
                        //Le pongo una unidad del recurso
                        wh.putContent(c.getCarrying(), 1);
                        //Ya he terminado por ahora
                        c.setState(WorkerState.WAIT);
                    }
            }
        }
    }
}
