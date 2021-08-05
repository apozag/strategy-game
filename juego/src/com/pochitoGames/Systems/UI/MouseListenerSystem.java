/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;

import com.pochitoGames.Engine.System;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.InputManager;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Systems.Visual.SpriteSystem;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author PochitoMan
 */
public class MouseListenerSystem extends System{
    
    private boolean started  = false;
    
    public MouseListenerSystem(){
        include(MouseListener.class, Sprite.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        
        if(!started){
            
            //started = true;
        }
        List<Entity> entities = getEntities();
        entities.sort(new SortByLayer());
        boolean pointBlock = false;
        Vector2D mousePos = InputManager.getInstance().getMousePos();
        for(Entity e : entities){
            MouseListener ml = e.get(MouseListener.class);
            Sprite s = e.get(Sprite.class);
            ml.releasedLeft = false;
            ml.releasedRight = false;
            if(SpriteSystem.isInsideSprite(s, mousePos)){
                // Click Izq
                if(!pointBlock && InputManager.getInstance().isMouseLeftPressed()){
                    if( ml.downLeft)
                        ml.firstTickLeft = false;
                    else{
                        ml.downLeft = true;
                        ml.firstTickLeft = InputManager.getInstance().isMouseLeftClicked();
                    }
                    // Bloquea el click para el resto
                    pointBlock = true;
                } 
                else if(ml.downLeft){
                    ml.downLeft = false;
                    ml.releasedLeft = true;
                }
                
                // Click Dch
                if(!pointBlock && InputManager.getInstance().isMouseRightPressed()){            
                    if(ml.downRight)
                        ml.firstTickRight = false;
                    else{
                        ml.downRight = true;
                        ml.firstTickRight = InputManager.getInstance().isMouseRightClicked();
                    }
                    // Bloquea el click para el resto
                    pointBlock = true;
                }
                else if(ml.downRight){
                    ml.downRight = false;
                    ml.releasedRight = true;
                }
                // MouseEnter
                ml.mouseOver = true;
            }
            else{
                ml.firstTickLeft = false;                
                ml.firstTickRight = false;                
                ml.mouseOver = false;
                if(ml.releasedLeft)
                        ml.releasedLeft = false;
                else{
                    ml.downLeft = false;
                    ml.releasedLeft = true;
                }
                if(ml.releasedRight)
                        ml.releasedRight = false;
                    else{
                        ml.downRight = false;
                        ml.releasedRight = true;
                    }
            }
        }
    }
}

class SortByLayer implements Comparator<Entity> {

    @Override
    public int compare(Entity o1, Entity o2) {
        MouseListener ml1 = o1.get(MouseListener.class), ml2 = o2.get(MouseListener.class);
        return ml2.layer - ml1.layer;
    }

    
}
