/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Canteen;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
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
            Iterator<Map.Entry<Human,Long>> iter = canteen.getPeople().entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<Human, Long> entry = iter.next();
                Human human = entry.getKey();
                long time = entry.getValue();
                if(java.lang.System.currentTimeMillis()-time >= canteen.getWaitTimeMillis()){
                    java.lang.System.out.println("restored!");
                    human.restoreHunger();
                    canteen.getPeople().remove(human);
                }
            }
        }
    }
    
}
