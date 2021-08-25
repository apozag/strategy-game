/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;

import com.pochitoGames.Components.UI.BuildingInfoPanel;
import com.pochitoGames.Components.UI.PanelRect;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
/**
 *
 * @author PochitoMan
 */
public class BuildingInfoPanelSystem extends System{
    
    public BuildingInfoPanelSystem(){
        include(BuildingInfoPanel.class, PanelRect.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        /*
        for(Entity e : getEntities()){
            BuildingInfoPanel bp = e.get(BuildingInfoPanel.class);
            if(!bp.initialized){
                PanelRect rect = e.get(PanelRect.class);
                if(rect.initialized){
                    bp.initialized = true;
                    for(PanelRect child : rect.getChildren()){
                        child
                    }
                    
                }
            }
        }
*/
    }
    
}
