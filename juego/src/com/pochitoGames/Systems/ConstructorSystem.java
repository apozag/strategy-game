package com.pochitoGames.Systems;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Constructor;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Engine.Camera;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ConstructorState;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PochitoMan
 */
public class ConstructorSystem extends System{
    public ConstructorSystem(){
        include(Position.class, Sprite.class, Constructor.class);
        exclude();
    }
    
    @Override
    public void update(double dt){
        for(Entity e : getEntities()){
            Constructor constructor = e.get(Constructor.class);
            ConstructorState state = constructor.getState();
            Sprite sprite = e.get(Sprite.class);
            Position p = e.get(Position.class);
            switch(state){
                case WAITING:
                    if(EventManager.getInstance().isMousePressed()){
                        Vector2D target = Camera.getInstance().toWorldCoords(EventManager.getInstance().getMousePos());
                        target.x -= sprite.getSrcSize().x/2;
                        target.y -= sprite.getSrcSize().y;
                        constructor.setTarget(target);
                        sprite.setCurrentAnimationIndex(2);
                        constructor.setState(ConstructorState.WALKING);
                    }
                    break;
                case WALKING:
                    Vector2D pos = p.getLocalPos();
                    Vector2D dir = Vector2D.sub(constructor.getTarget(), pos);
                    if(dir.magnitude() < 10){
                        sprite.setCurrentAnimationIndex(1);
                        constructor.setState(ConstructorState.BUILDING);
                    }
                    p.setLocalPos(Vector2D.add(Vector2D.mult(Vector2D.normalized(dir), constructor.getSpeed()), pos));
                    break;
                case BUILDING:
                    //Construye algo
                    constructor.setState(ConstructorState.WAITING);
                    break;                    
            }
            
        }
    }
}
