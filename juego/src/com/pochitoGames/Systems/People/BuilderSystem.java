package com.pochitoGames.Systems.People;
import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Camera;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.WorkerState;


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
        include(Human.class ,Builder.class, PathFinding.class);
        exclude();
    }
    
    @Override
    public void update(double dt){
        for(Entity e : getEntities()){
            PathFinding pf = e.get(PathFinding.class);
            Builder c = e.get(Builder.class);
            BuilderState state = c.getState();
            switch(state){

                case WAIT:
                    //Estamos parados hasta que nos requieran (Los edificios nos llamen)
                    break;                
                case BUILD:
                    //Si he llegado al edificio a construir
                    if(pf.getTargetCell() == null){
                        //Cojo ese edificio
                        Building b = c.getTargetBuilding();      
                        //Le pregunto qué recurso necesita
                        ResourceType needed = b.getResourceNeeded();
                        //Me lo pongo como recurso que necesito (en verdad eso no sirve para nada)
                        c.setResourceNeeded(needed);
                        
                        //Si no necesita ningún recurso, he acabado
                        if(needed == null){
                            c.setTargetBuilding(null);
                            c.setState(BuilderState.WAIT);
                        }
                        //Si SÍ necesita...
                        else{
                            //Busco a un compañero cercano
                            Human h = e.get(Human.class);
                            Worker mate = PeopleManager.getInstance().getNearestWorker(h.getTypeHuman(), pf.getCurrent());
                            //Si hay alguno disponible
                            if(mate != null){
                                //Cojo su pathFinding para saber su casilla
                                PathFinding mpf = mate.getEntity().get(PathFinding.class);
                                //Busco un almacén cercano a mi compañero 
                                //Que además tenga el recurso que quiero
                                Building building = BuildingManager.getInstance().getNearestWarehouse(mpf.getCurrent(), needed, c.getTargetBuilding());
                                
                                //Si hay alguno disponible
                                if(building != null){
                                    //Le pongo al compañero toda la info que necesita
                                    mate.setResourceNeeded(needed);
                                    mate.setTargetBuilding(building);
                                    mate.setTargetMate(c);
                                    //Le pongo target al pathfinding del compa(Para que se ponga en marcha)
                                    mpf.setTargetCell(building.getEntryCell());
                                    //Pongo al compa en estado SEARCH_RESOURCE
                                    mate.setState(WorkerState.SEARCH_RESOURCE);
                                    //Y yo me pongo en ON_HOLD para que nadie me moleste
                                    c.setState(BuilderState.ON_HOLD);
                                }
                            }
                        }
                    }
                        break;
                case ON_HOLD:
                    //NO hago nada hasta que mi compi me cambie de estado
                    break;
                case REPAIR:
                    Building b = c.getTargetBuilding();
                    b.setLife(b.getLife()+1);
                    c.setState(BuilderState.WAIT);
                    break;
            }
        }
    }
}
