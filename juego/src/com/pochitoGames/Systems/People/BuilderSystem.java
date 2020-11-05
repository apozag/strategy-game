package com.pochitoGames.Systems.People;
import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
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
        include(Position.class, Sprite.class, Human.class ,Builder.class, PathFinding.class);
        exclude();
    }
    
    @Override
    public void update(double dt){
        for(Entity e : getEntities()){
            PathFinding pf = e.get(PathFinding.class);
            Builder c = e.get(Builder.class);
            BuilderState state = c.getState();
            Sprite sprite = e.get(Sprite.class);
            Position p = e.get(Position.class);
            switch(state){
                case WAIT:
                    //Estamos parados hasta que nos requieran (Los edificios nos llaman)
                    break;                
                case SEARCH_RESOURCE:
                    //Si hemos llegado hasta el edificio
                    if(pf.getTargetCell() == null){
                        //Cojo el edificio (El componente Warehouse solo)
                        Warehouse wh = c.getTargetBuilding().getEntity().get(Warehouse.class);
                        //Le quito una unidad del recurso
                        wh.takeContent(c.getResourceNeeded(), 1);
                        //Cojo al compañero al que le tengo que llevar el recurso
                        Builder mate = c.getTargetMate();
                        //Cojo su pathfinding para saber su casilla
                        PathFinding mpf = mate.getEntity().get(PathFinding.class);
                        //Le digo a MI pathfinding que vaya a la casilla del compa
                        pf.setTargetCell(mpf.getCloseCell());
                        //Me congo en estado CARRY_RESOURCE
                        c.setState(BuilderState.CARRY_RESOURCE);
                        //Ahora empezará a andar solito hacia el compañero
                    }
                    break;
                case CARRY_RESOURCE:
                    //Si he llegado al compañero
                    if(pf.getTargetCell() == null){
                        //Cojo al compa
                        Builder mate = c.getTargetMate();
                        //Cojo el edificio que está constrruyendo mi compa (getTargetBuilding)
                        //Y le meto (putResources) una unidad del recurso que necesita (getResourcesNeeded)
                        mate.getTargetBuilding().putResources(c.getResourceNeeded(), 1);
                        //Pongo al compa de vuelta al estado BUILDING (estaba en ON_HOLD)
                        mate.setState(BuilderState.BUILD);                        
                        //Me pongo en WAIT
                        c.setState(BuilderState.WAIT);                        
                    }
                    break;
                case BUILD:
                    //Si he llegado al edificio a construir
                    if(pf.getTargetCell() == null){
                        //Cojo ese edificio
                        Building b = c.getTargetBuilding();      
                        //Le pregunto qué recurso necesita
                        ResourceType needed = b.getResourceNeeded();
                        //Me lo pongo como recurso que necesito
                        c.setResourceNeeded(needed);
                        
                        //Si no necesita ningún recurso, he acabado
                        if(needed == null){
                            c.setTargetBuilding(null);
                            c.setState(BuilderState.WAIT);
                        }
                        //Si SÍ necesita...
                        else{
                            //Busco a un compañero cercano
                            Builder mate = PeopleManager.getInstance().getNearestBuilder(pf.getCurrent());
                            //Si hay alguno disponible
                            if(mate != null){
                                //Cojo su pathFinding para saber su casilla
                                PathFinding mpf = mate.getEntity().get(PathFinding.class);
                                //Busco un almacén cercano a mi compañero 
                                //Que además tenga el recurso que quiero
                                Building wh = BuildingManager.getInstance().getNearestWarehouse(mpf.getCurrent(), needed);
                                
                                //Si hay alguno disponible
                                if(wh != null){
                                    //Le pongo al compañero toda la info que necesita
                                    mate.setResourceNeeded(needed);
                                    mate.setTargetBuilding(wh);
                                    mate.setTargetMate(c);
                                    //Le pongo target al pathfinding del compa(Para que se ponga en marcha)
                                    mpf.setTargetCell(wh.getEntryCell());
                                    //Pongo al compa en estado SEARCH_RESOURCE
                                    mate.setState(BuilderState.SEARCH_RESOURCE);
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
            }

            
        }
    }
}
