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
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class Warehouse extends Component{
    
    Map<ResourceType, Integer> content;
    //Map<ResourceType, Integer> capacity;
    
    public Warehouse(Map<ResourceType, Integer> content){
        this.content = content;
        
        for(Map.Entry<ResourceType, Integer>entry : content.entrySet()){
           ResourcesManager.getInstance().addResource(0, entry.getKey(), entry.getValue());
        }
    }
    
    public boolean canHave(ResourceType type){
        return content.containsKey(type);
    }
    
    public boolean hasResource(ResourceType type){
        return canHave(type) && content.get(type) > 0;
    }
    
    public void putContent(ResourceType type, int amount){
        if(canHave(type)){
            content.put(type, content.get(type) + amount);
            ResourcesManager.getInstance().addResource(0, type, amount);
        }
    }
    
    public void takeContent(ResourceType type, int amount){
        if(hasResource(type) && content.get(type) >= amount){
            content.put(type, content.get(type) - amount);
             ResourcesManager.getInstance().subResource(0, type, amount);
        }
    }
    
    public int getContent(ResourceType type){
        if(canHave(type))
            return content.get(type);
        return 0;
    }



            
}
