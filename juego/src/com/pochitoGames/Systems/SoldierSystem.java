package com.pochitoGames.Systems;

import com.pochitoGames.Components.*;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.MouseEventData;
import com.pochitoGames.Engine.MouseEventType;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.SoldierState;

import java.util.HashMap;
import java.util.HashSet;

public class SoldierSystem extends System{



    public SoldierSystem(){
        include(Human.class, Position.class, Soldier.class, Visibility.class);
        exclude();
    }

    @Override
    public void update(double dt) {

        for(Entity e : getEntities()){
            Soldier soldier = e.get(Soldier.class);
            Human human = e.get(Human.class);
            Position p = e.get(Position.class);
            SoldierState state = soldier.getState();
            Visibility v = e.get(Visibility.class);
            switch (state){
                case WAITING:
                    HashSet<Entity> onSight = v.getVisibility();
                    if(!onSight.isEmpty()){
                        soldier.setTarget(((Position)(onSight.iterator().next().get(Position.class))).getWorldPos());
                        soldier.setState(SoldierState.ATTACKING);
                    }
                    break;
                case ATTACKING:

                    break;
            }
        }
    }
}
