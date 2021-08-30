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
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.BuildingState;
import com.pochitoGames.Misc.States.WorkerState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;

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
                {
                    Building b = BuildingManager.getInstance().getFirstPlanned();
                    if(b != null){
                        pf.setSteps(PathFindingSystem.aStar(pf.getCurrent(), b.getEntryCell(), e.getId(), true));
                        if(pf.getSteps() != null){
                            b.builder = builder;
                            builder.setTargetBuilding(b);
                            pf.setTargetCell(b.getEntryCell());
                            pf.start();
                            builder.setState(BuilderState.BUILD);
                            b.setState(BuildingState.BUILDING);
                        }
                    }
                    break;         
                }
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
                                Worker mate = PeopleManager.getInstance().getNearestWorker(human.getTypeHuman(), pf.getCurrent(), building.getEntryCell());
                                if(mate != null){                                   
                                        builder.hasWorker = true;
                                        //Le pongo al compañero toda la info que necesita
                                        mate.setResourceNeeded(needed);
                                        mate.setTargetBuilding(building);
                                        mate.setTargetMate(builder);
                                        //Le pongo target al pathfinding del compa(Para que se ponga en marcha)
                                        PathFinding mpf = mate.getEntity().get(PathFinding.class);
                                        mpf.setTargetCell(building.getEntryCell());
                                        mpf.start();
                                        //Pongo al compa en estado SEARCH_RESOURCE
                                        mate.setState(WorkerState.SEARCH_RESOURCE);
                                        builder.worker = mate;
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
