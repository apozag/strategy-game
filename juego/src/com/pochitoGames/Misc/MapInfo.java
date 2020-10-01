/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc;

import com.pochitoGames.Engine.Vector2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class MapInfo{
    static MapInfo instance;
    int[][] map;
    Map <Integer, Integer> walkCost;
    
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
    
    public void setWalkCost(Map<Integer, Integer> walkCost){
        this.walkCost = walkCost;
    }
    
    public int[][] getMap(){
        return map;
    }
    
    public int getTileWalkCost(int x, int y){
        return walkCost.get(map[x][y]);
    }
    
    public void setTileId(int x, int y, int id){
        map[x][y] = id;
    }
    
}
