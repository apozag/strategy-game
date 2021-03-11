package com.pochitoGames.Systems.People;
import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.Other.Thinking;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.WorkerState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PochitoMan
 */

public class BuilderSystem extends System{
    public BuilderSystem(){
        include(Human.class ,Builder.class, PathFinding.class, Thinking.class);
        exclude();
    }
    
    @Override
    public void update(double dt){
        for(Entity e : getEntities()){
            PathFinding pf = e.get(PathFinding.class);
            Builder builder = e.get(Builder.class);
            BuilderState state = builder.getState();
            Human human = e.get(Human.class);
            Thinking thinking = e.get(Thinking.class);
            
            switch(state){

                case WAIT:
                    //Estamos parados hasta que nos requieran (Los edificios nos llamen)
                    break;                
                case BUILD:
                    //Si he llegado al edificio a construir
                    if(pf.getTargetCell() == null){
                        //Cojo ese edificio
                        Building b = builder.getTargetBuilding();      
                        //Le pregunto qué recurso necesita
                        ResourceType needed = b.getResourceNeeded();
                        //Me lo pongo como recurso que necesito (en verdad eso no sirve para nada)
                        thinking.setNeeded(needed);
                        
                        //Si no necesita ningún recurso, he acabado
                        if(needed == null){
                            builder.setTargetBuilding(null);
                            builder.hasWorker = false;
                            builder.setState(BuilderState.WAIT);
                        }
                        //Si SÍ necesita...
                        else if(!builder.hasWorker){
                            // Busco warehouse
                            Building building = BuildingManager.getInstance().getNearestWarehouseGet(pf.getCurrent(), needed, null, builder.getTargetBuilding());
                            if(building != null){
                                // Busco Worker cerca
                                Worker mate = PeopleManager.getInstance().getNearestWorker(human.getTypeHuman(), building.getEntryCell());
                                if(mate != null){
                                    PathFinding mpf = mate.getEntity().get(PathFinding.class);
                                    mpf.setSteps(PathFindingSystem.aStarFloor(mpf.getCurrent(), building.getEntryCell(), mate.getEntity().getId(), false));
                                    // Veo si hay camino
                                    if(mpf.getSteps() != null && 
                                            PathFindingSystem.aStarFloor(building.getEntryCell(), MapInfo.getInstance().getCloseCell(pf.getCurrent(), true, false), mate.getEntity().getId(), false) != null){
                                        builder.hasWorker = true;
                                        //Le pongo al compañero toda la info que necesita
                                        mate.setResourceNeeded(needed);
                                        mate.setTargetBuilding(building);
                                        mate.setTargetMate(builder);
                                        //Le pongo target al pathfinding del compa(Para que se ponga en marcha)
                                        mpf.setTargetCell(building.getEntryCell());
                                        //Pongo al compa en estado SEARCH_RESOURCE
                                        mate.setState(WorkerState.SEARCH_RESOURCE);
                                        //Y yo me pongo en ON_HOLD para que nadie me moleste
                                    }   
                                    else{
                                        mpf.setSteps(null);
                                    }
                                }
                            }
                        }
                    }
                        break;
                case ON_HOLD:
                    break;
                case REPAIR:
                    Building b = builder.getTargetBuilding();
                    b.setLife(b.getLife()+1);
                    builder.setState(BuilderState.WAIT);
                    break;
            }
        }
    }
}
