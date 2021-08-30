/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.GameLogic;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.CompoundTileMap;
import com.pochitoGames.Engine.Camera;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.InputManager;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Engine.Vector2i;
import com.pochitoGames.Engine.UIManager;
import com.pochitoGames.Systems.Visual.TileMapSystem;

/**
 *
 * @author PochitoMan
 */
public class TileSelectorSystem extends System{

    //BufferedImage selectorAux = ImageManager.getImage("src\\com\\pochitoGames\\Resources\\Sprites\\selector.png");
    
    public TileSelectorSystem(){
        include(Position.class, Sprite.class, TileSelector.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Position p = ((Position)(e.get(Position.class)));
            TileSelector ts = ((TileSelector)(e.get(TileSelector.class)));
                        
            Vector2D mousePos = Camera.getInstance().toWorldCoords(InputManager.getInstance().getMousePos());
            if(mousePos.x > 0 && mousePos.y > 0){
                
                Vector2i selected = IsometricTransformations.cartesianToIso(mousePos);

                Vector2D selectorPos = IsometricTransformations.isoToCartesian(selected);             
                                
                p.setLocalPos(selectorPos);
                
                ts.select(selected);                
            }
            
                
        }       
    }           
   
   
}