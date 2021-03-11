/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Other;

import com.pochitoGames.Engine.Vector2i;
import com.pochitoGames.Components.Buildings.Warehouse;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class BuildingInfo{
    public int id;
    public Vector2i entry;
    public Vector2i size;
    public int height;
    public String image;
    public Map<ResourceType, Integer> resourcesNeeded;
    public Warehouse warehouse;
    
    public BuildingInfo(int id, Vector2i entry, Vector2i size, int height, String image, Map<ResourceType, Integer> needed, Warehouse warehouse){
        this.id = id;
        this.entry = entry;
        this.size = size;
        this.height = height;
        this.image = image;
        this.resourcesNeeded = needed;
        this.warehouse = warehouse;
    }
    
    public BuildingInfo(BuildingInfo b){
        this.id = b.id;
        this.entry = b.entry;
        this.size = b.size;
        this.height = b.height;
        this.resourcesNeeded = b.resourcesNeeded;
        this.warehouse = b.warehouse;
    }
}
