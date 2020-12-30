package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.LumberJack;
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
        include(Tree.class, Position.class, PathFinding.class, Human.class);
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
                    lumberJack.setLumberJackState(LumberJackState.CHOPPING);
                    break;

                case CARRYING:

                    break;

                case CHOPPING:

                    break;

                case PLANTING:
                    Vector2i pantablePos = TreeManager.getInstance().getPlantableCell(pf.getCurrent());
                    pf.setTargetCell(pantablePos);
                    if (pf.getTargetCell() == null) {
                    TreeManager.getInstance().createTree(pantablePos);

                }
                   //f (pf.getTargetCell() == null) {
                   //   //Cojo al compa
                   //   Builder mate = c.getTargetMate();
                   //   //Cojo el edificio que está construyendo mi compa (getTargetBuilding)
                   //   //Y le meto (putResources) una unidad del recurso que necesita (getResourcesNeeded)
                   //   mate.getTargetBuilding().putResources(c.getCarrying(), 1);
                   //   //Pongo al compa de vuelta al estado BUILDING (estaba en ON_HOLD)
                   //   mate.setState(BuilderState.BUILD);
                   //   //Me pongo en WAIT
                   //   c.setState(WorkerState.WAIT);

                    break;
                case SEARCH_TREE:
                    Tree tree = TreeManager.getInstance().getNearestTree();
                    if (tree != null) {
                        pf.setTargetCell(tree.getCell());
                        lumberJack.setLumberJackState(LumberJackState.WALKING);
                        lumberJack.setTree(tree);
                    }
            }
        }

    }
}
