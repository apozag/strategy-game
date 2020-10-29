/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Map;

import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Misc.Other.Vector2i;

import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class MapInfo{
    static MapInfo instance;
    int[][] map;
    boolean[][] peopleLayer;
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
        peopleLayer = new boolean[map.length][map[0].length];
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
    
    public int getTileWalkCost(Vector2i cell){
        if(cell.col >= map.length || cell.row >= map[0].length){
            java.lang.System.out.println("IOOBE");
        }
        return peopleLayer[cell.col][cell.row]? -1 : walkCost.get(map[cell.col][cell.row]);
    }

    public int getTileId(Vector2i cell){ 
        if(cell.col >= map.length || cell.row >= map[0].length){
            java.lang.System.out.println("IOOBE");
        }
        return map[cell.col][cell.row]; 
    }
    public void setTileId(int x, int y, int id){
        map[x][y] = id;
    }
    
    public boolean getPeopleLayerCell(Vector2i cell){ return peopleLayer[cell.col][cell.row]; } 
    
    public void updatePeopleLayerCell(Vector2i oldCell, Vector2i newCell){
        peopleLayer[oldCell.col][oldCell.row] = false;
        peopleLayer[newCell.col][newCell.row] = true;
    }
    
}
