/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Components.Visual.CompoundTileMap;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Engine.Vector2i;

/**
 *
 * @author PochitoMan
 */
public class TileSelector extends Component{
    CompoundTileMap tileMap;
    
    Vector2i selected;
    
    private boolean visible = true;
    
    public TileSelector(CompoundTileMap tm){
        this.tileMap = tm;
    }
    
    public CompoundTileMap getMap(){
        return tileMap;
    }
    
    public Vector2i getSelected(){
        return selected;
    }
    
    public void select(Vector2i cell){
        selected = cell;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
