package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.LumberJack;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.States.LumberJackState;

import static com.pochitoGames.Misc.States.LumberJackState.CARRYING;
import static com.pochitoGames.Misc.States.LumberJackState.CHOPPING;

public class LumberJackSystem extends System {

    public LumberJackSystem(){
        include(Tree.class,Position.class, PathFinding.class, Human.class);
        exclude(Builder.class);
    }


    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            PathFinding pf = e.get(PathFinding.class);
            LumberJack lumberJack = e.get(LumberJack.class);
            LumberJackState lumberJackState = lumberJack.getLumberJackState();
            switch (lumberJackState) {
                case WAITING:
                    //Estamos parados hasta que nos requieran
                    break;

                case WALKING:
                    if (pf.getTargetCell() == null) {

                    }

                    break;

                case CARRYING:

                    break;

                case CHOPPING:

                    break;

                case PLANTING:

                    break;

            }
        }

    }
}
