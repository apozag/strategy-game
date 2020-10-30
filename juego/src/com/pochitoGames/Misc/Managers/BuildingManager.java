/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Vector2i;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class BuildingManager {
    
    private static BuildingManager instance;
    
    private static Map<Integer, BuildingInfo> blueprints = new HashMap<Integer, BuildingInfo>();
        
    private List<BuildingInfo> buildings;
    
    private BuildingManager(){
        //              id                   id       pos               pos entrada         ancho y alto    altura
        blueprints.put(100, new BuildingInfo(100, new Vector2i(0, 0), new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Ayuntamiento
        blueprints.put(101, new BuildingInfo(101, new Vector2i(0, 0), new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Almacén
        blueprints.put(102, new BuildingInfo(102, new Vector2i(0, 0), new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Lo que sea
        
        buildings = new ArrayList<>();
    }
    
    public static BuildingManager getInstance(){
        if(instance == null){
            instance = new BuildingManager();
        }
        return instance;
    }
    
    public void build(int id, Vector2i cell){                  
        
        BuildingInfo b = new BuildingInfo(blueprints.get(id));   
        b.cell = cell;                        

        for(int i = 0; i < b.size.col ; i++){
            for(int j = 0; j < b.size.row; j++){
                if(MapInfo.getInstance().getTileId(new Vector2i(b.cell.col + i, b.cell.row + j)) >= 100)
                    return;
            }
        }
        
        for(int i = 0; i < b.size.col; i++){
            for(int j = 0; j < b.size.row; j++){
                MapInfo.getInstance().setTileId(b.cell.col + i, b.cell.row + j, id);
            }
        }
        buildings.add(b);
        float yAnchor = 1 - (float)b.size.row /(2*b.height + b.size.col + b.size.row);
        switch(id){
            case 100:
                ECS.getInstance().createEntity(null, 
                    new Position(IsometricTransformations.isoToCartesian(cell)),
                    new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_1.png", new Vector2D(0, yAnchor), true),
                    new Building(100, 50, 10, TypeBuilding.CIVIL));
                break;
            case 101:
                ECS.getInstance().createEntity(null, 
                    new Position(IsometricTransformations.isoToCartesian(cell)),
                    new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_2.png", new Vector2D(0, yAnchor), true),
                    new Building(50, 30, 10, TypeBuilding.CIVIL));
                break;
            case 102:
                ECS.getInstance().createEntity(null, 
                    new Position(IsometricTransformations.isoToCartesian(cell)),
                    new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_3.png", new Vector2D(0, yAnchor), true),
                    new Building(30, 20, 20, TypeBuilding.CIVIL));
                break;
            default:
                System.out.println("Building does not exist!");
        }
    }
    
    public Vector2i getNearestBuilding(Vector2i cell, int id){
        Vector2i bCell = new Vector2i(99999, 99999);
        for(BuildingInfo b : buildings){
            if(b.id == id && cell.distance(Vector2i.add(b.entry, b.cell)) < cell.distance(bCell)){
                bCell = Vector2i.add(b.entry, b.cell);
            }
        }
        if(bCell.col >= 99999 || bCell.row >= 99999)
            return null;
        return bCell;
    }
}

class BuildingInfo{
    public int id;
    public Vector2i cell;
    public Vector2i entry;
    public Vector2i size;
    public int height;
    
    public BuildingInfo(int id, Vector2i cell, Vector2i entry, Vector2i size, int height){
        this.id = id;
        this.cell = cell;
        this.entry = entry;
        this.size = size;
        this.height = height;
    }
    
    public BuildingInfo(BuildingInfo b){
        this.id = b.id;
        this.cell = b.cell;
        this.entry = b.entry;
        this.size = b.size;
        this.height = b.height;
    }
}
