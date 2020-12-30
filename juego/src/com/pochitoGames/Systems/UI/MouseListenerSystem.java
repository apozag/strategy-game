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
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.MouseEventData;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Systems.Visual.SpriteSystem;
import java.util.Comparator;
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
            getEntities().sort(new SortByLayer());
        }
        
        for(Entity e : getEntities()){
                MouseListener ml = e.get(MouseListener.class);
                Sprite s = e.get(Sprite.class);
                if(EventManager.getInstance().isMouseLeftPressed()){            
                    Vector2D mousePos = EventManager.getInstance().getMousePos();
                    if(SpriteSystem.isInsideSprite(s, mousePos)){
                        if(ml.downLeft)
                                ml.firstTickLeft = false;
                        else{
                            ml.downLeft = true;
                            ml.firstTickLeft = true;
                        }
                        // Bloquea el click para el resto
                        break;
                    }
                }
                else{
                    if(ml.releasedLeft)
                        ml.releasedLeft = false;
                    else{
                        ml.downLeft = false;
                        ml.releasedLeft = true;
                    }
                }
                
                if(EventManager.getInstance().isMouseRightPressed()){            
                    Vector2D mousePos = EventManager.getInstance().getMousePos();
                    if(SpriteSystem.isInsideSprite(s, mousePos)){
                        if(ml.downRight)
                                ml.firstTickRight = false;
                        else{
                            ml.downRight = true;
                            ml.firstTickRight = true;
                        }
                        // Bloquea el click para el resto
                        break;
                    }
                }
                else{
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
