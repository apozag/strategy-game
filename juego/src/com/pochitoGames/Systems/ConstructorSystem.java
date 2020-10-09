package com.pochitoGames.Systems;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Builder;
import com.pochitoGames.Components.Human;
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
        include(Position.class, Sprite.class, Human.class ,Builder.class);
        exclude();
    }
    
    @Override
    public void update(double dt){
        for(Entity e : getEntities()){
            Builder constructor = e.get(Builder.class);
            ConstructorState state = constructor.getState();
            Sprite sprite = e.get(Sprite.class);
            Position p = e.get(Position.class);
            switch(state){
                case WAITING:
                    if(EventManager.getInstance().isMousePressed()){
                        Vector2D target = Camera.getInstance().toWorldCoords(EventManager.getInstance().getMousePos());
                        /*
                        if(target.x < p.getWorldPos().x)
                            sprite.setCurrentAnimationIndex(3);
                        else
                            sprite.setCurrentAnimationIndex(2);
*/
                        constructor.setTarget(target);
                        constructor.setState(ConstructorState.WALKING);
                    }
                    break;
                case WALKING:
                    Vector2D pos = p.getLocalPos();
                    Vector2D dir = Vector2D.sub(constructor.getTarget(), pos);
                    if(dir.magnitude() < 10){
                        //sprite.setCurrentAnimationIndex(1);
                        constructor.setState(ConstructorState.BUILDING);
                    }
                    p.setLocalPos(Vector2D.add(Vector2D.mult(Vector2D.normalized(dir), (float)(constructor.getSpeed() * dt)), pos));
                    break;
                case BUILDING:
                    //Construye algo
                    constructor.setState(ConstructorState.WAITING);
                    break;                    
            }
            
        }
    }
}
