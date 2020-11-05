/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Misc.Other.ResourceType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class ResourcesManager {
    
    private Map<Integer, Map<ResourceType, Integer>> resources = new HashMap<>();
    
    private static ResourcesManager instance;
    
    private ResourcesManager(){
        resources.put(0, new HashMap<ResourceType, Integer>(){
            {
                put(ResourceType.GOLD, 0);
                put(ResourceType.WOOD, 0);
                put(ResourceType.STONE, 0);
            }
        });
    }
    
    
    public static ResourcesManager getInstance(){
        if(instance == null)
            instance = new ResourcesManager();
        return instance;
    }
    
    public int getResource(int playerId, ResourceType type){
        return resources.get(playerId).get(type);
    }
    
    public void setResource(int playerId, ResourceType type, int amount){
        resources.get(playerId).put(type, amount);
    }
    
    public void addResource(int playerId, ResourceType type, int amount){
        resources.get(playerId).put(type, resources.get(playerId).get(type) + amount);
    }
    
    public void subResource(int playerId, ResourceType type, int amount){
        resources.get(playerId).put(type, resources.get(playerId).get(type) - amount);
    }
}