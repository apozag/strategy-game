/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Systems.Visual.TileMapSystem;

/**
 *
 * @author PochitoMan
 */
public class BuildingGeneratorSystem extends System{

    
    public BuildingGeneratorSystem(){
        include(TileSelector.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            if(EventManager.getInstance().isMousePressed()){
                TileSelector ts = (TileSelector)(e.get(TileSelector.class));
                TileMap map = ts.getMap();
                Vector2i selected = ts.getSelected();
                Vector2D pos = IsometricTransformations.isoToCartesian(selected);               
            }
        }
    }
    
}
