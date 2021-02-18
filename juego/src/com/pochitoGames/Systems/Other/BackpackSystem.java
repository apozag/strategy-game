/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Other;

import com.pochitoGames.Components.Other.Backpack;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
/**
 *
 * @author PochitoMan
 */
public class BackpackSystem extends System{

    public BackpackSystem(){
        include(Backpack.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Backpack b = e.get(Backpack.class);
            
            if(b.getResourceSprite() == null){
                b.setResourceSprite(e.getChildren().get(0).get(Sprite.class));
            }
        }
    }
    
}
