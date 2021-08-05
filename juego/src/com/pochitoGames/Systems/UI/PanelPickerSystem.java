/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;

import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.PanelActivator;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.UIManager;

/**
 *
 * @author PochitoMan
 */
public class PanelPickerSystem extends System{

    public PanelPickerSystem(){
        include(PanelActivator.class, MouseListener.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            PanelActivator pp = e.get(PanelActivator.class);
            MouseListener ml = e.get(MouseListener.class);
            
            if(ml.firstTickLeft){
                UIManager.getInstance().activatePanel(pp.getTag());
            }
        }
    }
    
}
