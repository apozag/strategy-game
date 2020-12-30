/* * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Refinery;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Time;
/**
 *
 * @author PochitoMan
 */
public class RefinerySystem extends System{

    public RefinerySystem(){
        include(Building.class, Refinery.class, Warehouse.class);
        exclude();   
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Building b = e.get(Building.class);
            if(b.isFinished()){                    
                Warehouse wh = e.get(Warehouse.class);                
                Refinery refinery = e.get(Refinery.class);    
                if(refinery.getLastStoneAmount() > 0){
                    if(java.lang.System.currentTimeMillis() - refinery.getLastTime() > refinery.getFrequency()){
                        refinery.setLastTime(java.lang.System.currentTimeMillis());
                        wh.takeContent(ResourceType.RAW_STONE, 1);
                        wh.putContent(ResourceType.STONE, 1);
                    }
                }else{
                    refinery.setLastTime(java.lang.System.currentTimeMillis());
                }
                refinery.setLastStoneAmount(wh.getContent(ResourceType.RAW_STONE));
            }
        }
    }            
}
