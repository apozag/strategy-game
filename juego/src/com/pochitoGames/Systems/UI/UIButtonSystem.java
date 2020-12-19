/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.UI.UIButton;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
/**
 *
 * @author PochitoMan
 */
public class UIButtonSystem extends System{

    
    public UIButtonSystem(){
        include(Position.class, Sprite.class, UIButton.class, MouseListener.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        
            for(Entity e : getEntities()){
                UIButton b = e.get(UIButton.class);
                Sprite s = e.get(Sprite.class);
                /*
                if(EventManager.getInstance().isMousePressed()){
                    if(SpriteSystem.isInsideSprite(s, mousePos)){              
                        if(b.down)
                            b.firstTick = false;
                        else{
                            b.down = true;
                            b.firstTick = true;
                            s.setCurrentAnimationIndex(1); 
                        }
                    }
                    else
                        b.down = false;
                }
                else{
                    s.setCurrentAnimationIndex(0);
                    b.down = false;
                } */
                MouseListener ml = e.get(MouseListener.class);
                if(ml.down){
                    s.setCurrentAnimationIndex(1); 
                }
                else{
                    s.setCurrentAnimationIndex(0);
                }
        }         
        
    }
    
}
