/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.Vector2i;

/**
 *
 * @author PochitoMan
 */
public class TileSelector extends Component{
    TileMap tileMap;
    
    Vector2i selected;
    
    public TileSelector(TileMap tm){
        this.tileMap = tm;
    }
    
    public TileMap getMap(){
        return tileMap;
    }
    
    public Vector2i getSelected(){
        return selected;
    }
    
    public void select(Vector2i cell){
        selected = cell;
    }
}
