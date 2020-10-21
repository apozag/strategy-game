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
                case WAITING:
                    if(EventManager.getInstance().isMousePressed()){
                        Vector2D target = Camera.getInstance().toWorldCoords(EventManager.getInstance().getMousePos());
                        Vector2i cell = IsometricTransformations.cartesianToIso(target);
                        pf.setTargetCell(cell);
                        constructor.setState(ConstructorState.WALKING);
                    }
                    break;
                case WALKING:
                    /*
                    Vector2D pos = p.getLocalPos();
                    Vector2D dir = Vector2D.sub(constructor.getTarget(), pos);
                    if(dir.magnitude() < 10){
                        //sprite.setCurrentAnimationIndex(1);
                        constructor.setState(ConstructorState.BUILDING);
                    }
                    p.setLocalPos(Vector2D.add(Vector2D.mult(Vector2D.normalized(dir), (float)(constructor.getSpeed() * dt)), pos));
                    */
                    if(pf.getTargetCell() == null)
                        constructor.setState(ConstructorState.BUILDING);
                    break;
                case BUILDING:
                    //Construye algo
                    constructor.setState(ConstructorState.WAITING);
                    break;                    
            }
            
        }
    }
}
