package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.*;
import com.pochitoGames.Engine.System;

public class BuildingSystem extends System {


    boolean start = false;

    public BuildingSystem(){
        include(Human.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {

        for(Entity e : getEntities()){

        }
    }
}
