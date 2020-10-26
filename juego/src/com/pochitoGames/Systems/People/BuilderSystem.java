package com.pochitoGames.Systems.People;
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
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.States.ConstructorState;


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
            Builder constructor = e.get(Builder.class);
            ConstructorState state = constructor.getState();
            Sprite sprite = e.get(Sprite.class);
            Position p = e.get(Position.class);
            switch(state){
                case GO_A:
                    if(pf.getTargetCell() == null && EventManager.getInstance().isKeyDown('e')){
                        Vector2i near = BuildingManager.getInstance().getNearestBuilding(pf.getCurrent(), 101);
                        if(near != null){
                            pf.setTargetCell(near);
                            constructor.setState(ConstructorState.GO_B);
                        }
                    }
                    break;
                case GO_B:
                    if(pf.getTargetCell() == null && EventManager.getInstance().isKeyDown('e')){
                       Vector2i near = BuildingManager.getInstance().getNearestBuilding(pf.getCurrent(), 102);
                        if(near != null){
                            pf.setTargetCell(near);
                            constructor.setState(ConstructorState.GO_C);
                        }
                    }                    
                    break;
                case GO_C:
                    if(pf.getTargetCell() == null && EventManager.getInstance().isKeyDown('e')){
                        Vector2i near = BuildingManager.getInstance().getNearestBuilding(pf.getCurrent(), 100);
                        if(near != null){
                            pf.setTargetCell(near);
                            constructor.setState(ConstructorState.GO_A);
                        }
                    }
                    break;                    
            }
            
        }
    }
}
