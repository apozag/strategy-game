package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.Other.Backpack;
import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.LumberJack;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.TreeManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.LumberJackState;

import java.util.List;

public class LumberJackSystem extends System {

    private int choppedTrees;
    private int plantedTrees;
    private boolean plant = false;
    private List<Tree> treesList = TreeManager.getInstance().getTrees();

    public LumberJackSystem() {
        include(Position.class, PathFinding.class, Human.class, LumberJack.class, Backpack.class);
        exclude(Builder.class);
    }


    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            PathFinding pf = e.get(PathFinding.class);
            LumberJack lj = e.get(LumberJack.class);
            LumberJackState lumberJackState = lj.getState();
            Backpack backpack = e.get(Backpack.class);
            if(lj.getResourceSprite() == null){
                lj.setResourceSprite(e.getChildren().get(0).get(Sprite.class));
            }
            
            switch (lumberJackState) {
                case WAITING:
                    // Cada x segundos vamos a por un arbol
                    if(lj.getHut() != null ){
                        if (java.lang.System.currentTimeMillis() - lj.getLastTime() > lj.getWaitTime()) {
                            lj.setState(LumberJackState.SEARCHING_TREE);
                        }

                        if (choppedTrees >= 4) {
                            plant = true;
                            choppedTrees = 0;
                        }
                        if (plant) {
                            plantedTrees++;
                            if (plantedTrees > 7) {
                                plant = false;
                                plantedTrees = 0;
                                break;
                            } else {
                                lj.setState(LumberJackState.WALKING_PLANTING);
                            }
                        }
                    }

                    break;

                case WALKING_HUT:
                    if (pf.getTargetCell() == null) {
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.WAITING);

                    }
                    break;

                case WALKING_TREE:
                    if (pf.getTargetCell() == null) {
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.CHOPPING);
                        choppedTrees += 1;
                    }

                    break;

                case CARRYING:
                    if (pf.getTargetCell() == null) {
                        Warehouse wh = lj.getHut().get(Warehouse.class);
                        wh.putContent(ResourceType.RAW_WOOD, 1);
                        backpack.setCarrying(null);
                        lj.setState(LumberJackState.WAITING);
                    }
                    break;

                case CHOPPING:
                    if (java.lang.System.currentTimeMillis() - lj.getLastTime() > lj.getWaitTime()) {
                        TreeManager.getInstance().removeTree(lj.getTree());
                        lj.setTree(null);
                        Building b = lj.getHut().get(Building.class);
                        pf.setTargetCell(b.getEntryCell());
                        lj.setState(LumberJackState.CARRYING);
                        backpack.setCarrying(ResourceType.RAW_WOOD);
                    }
                    break;

                case WALKING_PLANTING:
                    Vector2i pantablePos = TreeManager.getInstance().getPlantableCell(lj.lastPlantableCell);
                    if (pantablePos != null) {
                        pf.setTargetCell(pantablePos);
                        lj.setState(LumberJackState.PLANTING);

                    }
                    break;
                case PLANTING:
                    if (pf.getTargetCell() == null) {
                        TreeManager.getInstance().createTree(pf.getCurrent());
                        Building b = lj.getHut().get(Building.class);
                        pf.setTargetCell(b.getEntryCell());
                        lj.setState(LumberJackState.WALKING_HUT);

                    }
                    break;

                case SEARCHING_TREE:
                    Tree tree = TreeManager.getInstance().getNearestTree(pf.getCurrent());
                    if (tree != null) {
                        lj.lastPlantableCell = new Vector2i(tree.getCell());

                        pf.setTargetCell(MapInfo.getInstance().getCloseCell(tree.getCell(), false, false));
                        lj.setState(LumberJackState.WALKING_TREE);
                        lj.setTree(tree);
                        tree.setTaken();;
                    } else {
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.WAITING);
                    }
            }
        }

    }
}
