/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.UI.PanelRect;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.UIManager;
/**
 *
 * @author PochitoMan
 */
public class PanelRectSystem extends System{
    
    public PanelRectSystem(){
        include(Position.class, PanelRect.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            PanelRect rect = e.get(PanelRect.class);
            if(!rect.initialized){
                for(Entity child : e.getChildren()){
                    PanelRect childRect = child.get(PanelRect.class);
                    if(childRect != null){
                        if(!rect.getChildren().contains(childRect))
                            rect.addChild(childRect);
                    }
                }
                rect.initialized = true;
                if(rect.name != null && !rect.name.equals("")){
                    UIManager.getInstance().addPanel(rect.name, e);
                }
                e.deactivate();
            }
        }
    }
    
}
