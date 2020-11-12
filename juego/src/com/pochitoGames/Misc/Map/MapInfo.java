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
            return -1;
        }

        int num = map[cell.col][cell.row];
        if(!walkCost.containsKey(num))
            java.lang.System.out.println("ojknojn");
        return walkCost.get(num);       
    }

    public int getTileId(Vector2i cell){ 
        if(cell.col >= map.length || cell.row >= map[0].length){
            return -1;
        }
        return map[cell.col][cell.row]; 
    }
    public void setTileId(int x, int y, int id){
        map[x][y] = id;
        if(id < 100){
            activeTileMap.setTile(id, x, y);
        }
    }
    
    public int getWidth(){
        return map.length;
    }
    public int getHeight(){
        return map[0].length;
    }
    
    public boolean getPeopleLayerCell(Vector2i cell){ return peopleLayer[cell.col][cell.row]; } 
    
    public void updatePeopleLayerCell(Vector2i oldCell, Vector2i newCell){
        peopleLayer[oldCell.col][oldCell.row] = false;
        peopleLayer[newCell.col][newCell.row] = true;
    }
    
   public Vector2i getCloseCell(Vector2i cell){
        if(getPeopleLayerCell(cell)){
            Vector2i neighbors[] = {new Vector2i(1, 0), new Vector2i(-1, 0), 
                                    new Vector2i(0, 1), new Vector2i(0,-1), 
                                    new Vector2i(1, -1), new Vector2i(-1, 1), 
                                    new Vector2i(1, 1)};           
            
            for(int i = 0; i < 8; i++){
                Vector2i n = Vector2i.add(cell, neighbors[i]);
                if(!(n.col < 0 || n.col >= getWidth() || n.row < 0 || n.row >= getHeight()) && getTileWalkCost(n) >= 0){
                    return n;
                }
            }
        }
        return cell;
    }
    
    
}
