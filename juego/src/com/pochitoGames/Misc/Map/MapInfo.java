/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Map;

import com.pochitoGames.Components.Visual.TileMap;

import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class MapInfo{
    static MapInfo instance;
    int[][] map;
    Map <Integer, Integer> walkCost;
    TileMap activeTileMap;
    
    private MapInfo(){
    }
    
    public static MapInfo getInstance(){
        if(instance == null)
            instance = new MapInfo();
        return instance;
    }
    
    public void setMap(int[][] map){
        this.map = map;
    }
    
    public void setActiveTileMap(TileMap map){
        activeTileMap = map;
    }
    
    public TileMap getActiveTileMap(){
        return activeTileMap;
    }
    
    public void setWalkCost(Map<Integer, Integer> walkCost){
        this.walkCost = walkCost;
    }
    
    public int[][] getMap(){
        return map;
    }
    
    public int getTileWalkCost(int x, int y){
        return walkCost.get(map[x][y]);
    }

    public int getTileId(int col, int row){ return map[col][row]; }
    public void setTileId(int x, int y, int id){
        map[x][y] = id;
    }
    
}
