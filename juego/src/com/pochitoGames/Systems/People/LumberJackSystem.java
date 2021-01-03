package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.LumberJack;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.TreeManager;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.LumberJackState;
import com.pochitoGames.Misc.States.WorkerState;

import static com.pochitoGames.Misc.States.LumberJackState.CARRYING;
import static com.pochitoGames.Misc.States.LumberJackState.CHOPPING;

public class LumberJackSystem extends System {

    public LumberJackSystem() {
        include(Position.class, PathFinding.class, Human.class, LumberJack.class);
        exclude(Builder.class);
    }


    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            PathFinding pf = e.get(PathFinding.class);
            LumberJack lj = e.get(LumberJack.class);
            LumberJackState lumberJackState = lj.getState();
            switch (lumberJackState) {
                case WAITING:
                    // Cada x segundos vamos a por un arbol
                    if(java.lang.System.currentTimeMillis() - lj.getLastTime() > lj.getWaitTime()){
                        lj.setState(LumberJackState.SEARCHING_TREE);
                    }
                    break;
                    
                case WALKING_HUT:
                    if(pf.getTargetCell() == null){
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.WAITING);
                    }
                    break;

                case WALKING_TREE:
                    if (pf.getTargetCell() == null) {
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.CHOPPING);    
                    }
                    break;

                case CARRYING:

                    break;

                case CHOPPING:
                    if(java.lang.System.currentTimeMillis() - lj.getLastTime() > lj.getWaitTime()){
                        ECS.getInstance().removeEntity(lj.getTree().getEntity());
                        lj.setState(LumberJackState.CARRYING);
                    }
                    break;

                case PLANTING:
                    Vector2i pantablePos = TreeManager.getInstance().getPlantableCell(pf.getCurrent());
                    if(pantablePos != null){
                        pf.setTargetCell(pantablePos);
                        lj.setState(LumberJackState.WALKING_PLANTING);
                    }                
                    break;
                case WALKING_PLANTING:
                    if(pf.getTargetCell() == null){
                        TreeManager.getInstance().createTree(pf.getCurrent());
                        lj.setState(LumberJackState.WALKING_HUT);
                    }
                    break;
                case SEARCHING_TREE:
                    Tree tree = TreeManager.getInstance().getNearestTree();
                    if (tree != null) {
                        pf.setTargetCell(tree.getCell());
                        lj.setState(LumberJackState.WALKING_TREE);
                        lj.setTree(tree);
                    }
                    else{
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.WAITING);
                    }
            }
        }

    }
}
