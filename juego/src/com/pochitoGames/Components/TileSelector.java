/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;

/**
 *
 * @author PochitoMan
 */
public class TileSelector extends Component{
    TileMap tileMap;
    
    int column, row;
    
    public TileSelector(TileMap tm){
        this.tileMap = tm;
    }
    
    public TileMap getMap(){
        return tileMap;
    }
    
    public Vector2D getSelected(){
        return new Vector2D(column, row);
    }
    
    public void select(int column, int row){
        this.column = column;
        this.row = row;
    }
}
