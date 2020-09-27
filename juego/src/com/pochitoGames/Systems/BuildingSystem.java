package com.pochitoGames.Systems;

import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Human;
import com.pochitoGames.Components.Sprite;
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
