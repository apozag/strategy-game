/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Managers.ResourcesManager;
import com.pochitoGames.Misc.Other.ResourceType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author PochitoMan
 */
public class Warehouse extends Component{
    
    Map<ResourceType, Integer> content;
    Map<ResourceType, Integer> capacity;  
    Set<ResourceType> transferable;
    
    boolean addsToGlobal = true;
    
    public boolean hasWorker = false;
    
    
    public Warehouse(boolean addsToGlobal, Map<ResourceType, Integer> content, Map<ResourceType, Integer> capacity, ResourceType... transferable){
        this.content = content;
        this.capacity = capacity;
        this.addsToGlobal = addsToGlobal;
        for(Map.Entry<ResourceType, Integer>entry : content.entrySet()){
           ResourcesManager.getInstance().addResource(0, entry.getKey(), entry.getValue());
        }
        this.transferable = new HashSet<>();
        for(ResourceType type : transferable){
            this.transferable.add(type);
        }
    }
    
    public Warehouse(boolean addsToGlobal, Map<ResourceType, Integer> content, Map<ResourceType, Integer> capacity, Set<ResourceType>transferable){
        this.content = content;
        this.capacity = capacity;
        this.addsToGlobal = addsToGlobal;
        for(Map.Entry<ResourceType, Integer>entry : content.entrySet()){
           ResourcesManager.getInstance().addResource(0, entry.getKey(), entry.getValue());
        }
        this.transferable = transferable;
       
    }
    
    public Warehouse(Warehouse warehouse){
        this.content = new HashMap<>(warehouse.content);
        this.capacity = new HashMap<>(warehouse.capacity);
        this.addsToGlobal = warehouse.addsToGlobal;
        this.transferable = warehouse.transferable;
    }
    
    public boolean canHave(ResourceType type){
        return content.containsKey(type);
    }
    
    public boolean isFull(ResourceType type){
        return content.containsKey(type) && content.get(type) >= capacity.get(type);
    }
    
    public boolean hasResource(ResourceType type){
        return canHave(type) && content.get(type) > 0;
    }
    
    public boolean putContent(ResourceType type, int amount){
        if(!isFull(type)){
            content.put(type, content.get(type) + amount);
            if(addsToGlobal)
                ResourcesManager.getInstance().addResource(0, type, amount);
            return true;
        }
        return false;
    }
    
    public boolean takeContent(ResourceType type, int amount){
        if(hasResource(type) && content.get(type) >= amount){
            content.put(type, content.get(type) - amount);
            if(addsToGlobal)
                ResourcesManager.getInstance().subResource(0, type, amount);
            return true;
        }
        return false;
    }
    
    public int getContent(ResourceType type){
        if(canHave(type))
            return content.get(type);
        return 0;
    }
    
    public Set<ResourceType> getResourceTypes(){
        return content.keySet();
    }
    
    public Set<ResourceType> getTransferableTypes(){
        return transferable;
    }

    public boolean doesAddToGlobal(){
        return addsToGlobal;
    }

            
}
