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
                    if(pf.getTargetCell() == null){
                        Warehouse wh = c.getTargetBuilding().getEntity().get(Warehouse.class);
                        wh.takeContent(c.getResourceNeeded(), 1);
                        Builder mate = c.getTargetMate();
                        PathFinding mpf = mate.getEntity().get(PathFinding.class);                        
                        pf.setTargetCell(mpf.getCloseCell());
                        c.setState(BuilderState.CARRY_RESOURCE);
                    }
                    break;
                case CARRY_RESOURCE:
                    if(pf.getTargetCell() == null){
                        Builder mate = c.getTargetMate();
                        mate.getTargetBuilding().putResources(c.getResourceNeeded(), 1);
                        mate.setState(BuilderState.BUILD);                        
                        c.setState(BuilderState.WAIT);
                    }
                    break;
                case BUILD:
                    if(pf.getTargetCell() == null){
                        Building b = c.getTargetBuilding();      
                        ResourceType needed = b.getResourceNeeded();
                        c.setResourceNeeded(needed);
                        if(needed == null){
                            c.setTargetBuilding(null);
                            c.setState(BuilderState.WAIT);
                        }
                        else{
                            Builder mate = PeopleManager.getInstance().getNearestBuilder(pf.getCurrent());
                            if(mate != null){
                                PathFinding mpf = mate.getEntity().get(PathFinding.class);
                                Building wh = BuildingManager.getInstance().getNearestWarehouse(mpf.getCurrent(), needed);
                                if(wh != null){
                                    mate.setResourceNeeded(needed);
                                    mate.setTargetBuilding(wh);
                                    mate.setTargetMate(c);
                                    mpf.setTargetCell(wh.getEntryCell());
                                    mate.setState(BuilderState.SEARCH_RESOURCE);
                                    c.setState(BuilderState.ON_HOLD);
                                }
                            }
                        }
                    }
                        break;
                case ON_HOLD:                    
                    break;                        
            }

            
        }
    }
}
