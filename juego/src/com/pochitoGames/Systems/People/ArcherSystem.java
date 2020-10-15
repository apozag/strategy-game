package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.Visibility;
import com.pochitoGames.Components.People.Archer;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Misc.States.ArcherState;

import java.util.HashSet;

public class ArcherSystem extends System {

    public ArcherSystem(){
        include(Human.class, Position.class, Archer.class, Visibility.class);
        exclude();
    }

    @Override
    public void update(double dt) {

        for(Entity e : getEntities()){
            Archer archer = e.get(Archer.class);
            Human human = e.get(Human.class);
            Position p = e.get(Position.class);
            ArcherState state = archer.getState();
            Visibility v = e.get(Visibility.class);
            switch (state){
                case WAITING:
                    HashSet<Entity> onSight = v.getVisibility();
                    if(!onSight.isEmpty()){
                        archer.setTarget(onSight.iterator().next());
                        archer.setState(ArcherState.ATTACKING);
                    }
                    break;
                case ATTACKING:
                    Entity t= archer.getTarget();
                    Human h = t.get(Human.class);
                    h.reciveDamage(human.getAttack());
                    break;
            }
        }
    }
}
