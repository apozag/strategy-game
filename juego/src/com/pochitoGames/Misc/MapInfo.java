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
    Map <Integer, TileInfo> tileInfo;
    List<Vector2D> changed;
    
    private MapInfo(){
        changed = new LinkedList<>();
    }
    
    public static MapInfo getInstance(){
        if(instance == null)
            instance = new MapInfo();
        return instance;
    }
    
    public void setMap(int[][] map){
        this.map = map;
    }
    
    public int[][] getMap(){
        return map;
    }
    
    public TileInfo getTileInfo(int x, int y){
        return tileInfo.get(map[x][y]);
    }
    
    public void setTileId(int x, int y, int id){
        map[x][y] = id;
        changed.add(new Vector2D(x, y));
    }
    
}
