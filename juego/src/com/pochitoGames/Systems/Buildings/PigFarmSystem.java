package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.PigFarm;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Other.ResourceType;

public class PigFarmSystem extends System {

    public PigFarmSystem() {
        include(PigFarm.class, Warehouse.class, Building.class);
        exclude();
    }
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Building building = e.get(Building.class);
            Warehouse wh = e.get(Warehouse.class);
            PigFarm pig = e.get(PigFarm.class);

           if(building.isFinished()){
               if(java.lang.System.currentTimeMillis() - pig.getLastTimeCreated() >= pig.getWaitTimeMillis()){
                   pig.setLastTimeCreated(java.lang.System.currentTimeMillis());
                   java.lang.System.out.println("CREAMOS UNA CARNEEEEEEEEEEE");
                   wh.putContent(ResourceType.MEAT,1);
               }
           }else{
               pig.setLastTimeCreated(java.lang.System.currentTimeMillis());
            }
        }
    }
}
