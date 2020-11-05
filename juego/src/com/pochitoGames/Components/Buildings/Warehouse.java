/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Other.ResourceType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class Warehouse extends Component{
    Map<ResourceType, Integer> content;
    
    public Warehouse(Map<ResourceType, Integer> content){
        this.content = content;
    }
    
    public boolean canHave(ResourceType type){
        return content.containsKey(type);
    }
    
    public boolean hasResource(ResourceType type){
        return canHave(type) && content.get(type) > 0;
    }
    
    public void putContent(ResourceType type, int amount){
        if(canHave(type))
            content.put(type, content.get(type) + amount);
    }
    
    public void takeContent(ResourceType type, int amount){
        if(hasResource(type) && content.get(type) >= amount)
            content.put(type, content.get(type) - amount);
    }
            
}
