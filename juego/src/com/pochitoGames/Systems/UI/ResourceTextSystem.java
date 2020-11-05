/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;


import com.pochitoGames.Engine.System;
import com.pochitoGames.Components.UI.ResourceText;
import com.pochitoGames.Components.Visual.Text;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Misc.Managers.ResourcesManager;

/**
 *
 * @author PochitoMan
 */
public class ResourceTextSystem extends System{

    public ResourceTextSystem(){
        include(Text.class, ResourceText.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            ResourceText rt = e.get(ResourceText.class);
            Text text = e.get(Text.class);
            String s = null;
            switch(rt.getType()){
                case WOOD:
                    s = "Wood: ";
                    break;
                case STONE:
                    s = "Stone: ";
                    break;
                case GOLD:
                    s = "Gold: ";
                    break;                                                
            }
            text.setText(s + ResourcesManager.getInstance().getResource(0, rt.getType()));
        }
    }
    
}
