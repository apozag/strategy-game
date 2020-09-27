/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;

import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Components.TileMap;
import com.pochitoGames.Components.TileSelector;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.MapInfo;
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
                Vector2D selected = ts.getSelected();
                Vector2D pos = TileMapSystem.indexToCartesian((int)selected.x, (int)selected.y, map);
                /*
                ECS.getInstance().createEntity(null, 
                        new Position(pos),
                        new Sprite("src\\com\\pochitoGames\\Resources\\TileMaps\\Casa.png", new Vector2D(0,3.0f/4.0f))

                );
                */
                //Sprites\\building.png
            }
        }
    }
    
}
