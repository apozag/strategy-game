/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Canteen;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Other.ResourceType;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author PochitoMan
 */
public class CanteenSystem extends System{
    
    public CanteenSystem(){
        include(Canteen.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Canteen canteen = e.get(Canteen.class);
            if(canteen.getPeople().isEmpty())
                continue;
            Warehouse wh = e.get(Warehouse.class);
            canteen.setCapacity(wh.getContent(ResourceType.MEAT));
            Iterator<Map.Entry<Human,Long>> iter = canteen.getPeople().entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<Human, Long> entry = iter.next();
                Human human = entry.getKey();
                long time = entry.getValue();
                if(human == null) iter.remove();
                else if(java.lang.System.currentTimeMillis()-time >= canteen.getWaitTimeMillis())
                {
                    if(!wh.takeContent(ResourceType.MEAT, 1)){
                        java.lang.System.out.println("No hay carne");
                    }
                    iter.remove();
                }
            }
        }
    }
    
}
