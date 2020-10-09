/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc;

/**
 *
 * @author PochitoMan
 */
public class BuildingManager {
    private static BuildingManager instance;
    
    private Map<Integer, BuildingInfo> buildings;
    
    private BuildingManager(){
        
    }
    
    public static BuildingManager getInstance(){
        if(instance == null){
            instance = new BuildingManager();
        }
        return instance;
    }
    
    public static void build(int id, int col, int row){
        BuildingInfo info = buildings.get(id);
        
        MapInfo.getInstance().setTileId(col, row, id);
        MapInfo.getInstance().setTileId(col+1, row, id);
        MapInfo.getInstance().setTileId(col, row+1, id);
        MapInfo.getInstance().setTileId(col+1, row+1, id);
    }
}
