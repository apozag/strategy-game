package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.LumberJack;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.TreeManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.LumberJackState;

public class LumberJackSystem extends System {

    private int choppedTrees;
    private int plantedTrees = 0;
    private boolean plant = false;


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
                    if (lj.getHut() != null && java.lang.System.currentTimeMillis() - lj.getLastTime() > lj.getWaitTime()) {
                        lj.setState(LumberJackState.SEARCHING_TREE);
                        java.lang.System.out.println("Cambio estado a Buscar Arbol");
                    }
                    break;

                case WALKING_HUT:
                    if (pf.getTargetCell() == null) {
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.WAITING);
                        java.lang.System.out.println("Cambio estado a esperar");

                    }
                    break;

                case WALKING_TREE:
                    if (pf.getTargetCell() == null) {
                        java.lang.System.out.println("He cortado tanto arboles :"+choppedTrees);
                        if (choppedTrees >= 4){
                            plant = true;
                            choppedTrees = 0;
                        }
                        if (plant) {
                            plantedTrees++;
                            if (plantedTrees > 4){
                                plant = false;
                                plantedTrees = 0;
                                break;
                            }
                            else {
                                lj.setState(LumberJackState.PLANTING);
                                java.lang.System.out.println("He plantado tanto arboles :"+plantedTrees);
                                java.lang.System.out.println("Cambio de estado a plantar");
                            }
                        } else {
                            lj.setLastTime(java.lang.System.currentTimeMillis());
                            lj.setState(LumberJackState.CHOPPING);
                            java.lang.System.out.println("Voy a talar un arbol");
                            choppedTrees += 1;
                        }

                    }

                    break;

                case CARRYING:
                    if (pf.getTargetCell() == null) {
                        Warehouse wh = lj.getHut().get(Warehouse.class);
                        wh.putContent(ResourceType.RAW_WOOD, 1);
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
                        java.lang.System.out.println("Vuelvo con el arbol talado");

                    }
                    break;

                case PLANTING:
                    Vector2i pantablePos = TreeManager.getInstance().getPlantableCell(pf.getCurrent());
                    if (pantablePos != null) {
                        pf.setTargetCell(pantablePos);
                        lj.setState(LumberJackState.WALKING_PLANTING);
                        java.lang.System.out.println("Estoy plantando");

                    }
                    break;
                case WALKING_PLANTING:
                    if (pf.getTargetCell() == null) {
                        TreeManager.getInstance().createTree(pf.getCurrent());
                        lj.setState(LumberJackState.WALKING_HUT);
                        java.lang.System.out.println("Voy a la cabaña");

                    }
                    break;

                case SEARCHING_TREE:
                    Tree tree = TreeManager.getInstance().getNearestTree(pf.getCurrent());
                    if (tree != null) {
                        pf.setTargetCell(MapInfo.getInstance().getCloseCell(tree.getCell()));
                        lj.setState(LumberJackState.WALKING_TREE);
                        java.lang.System.out.println("Voy al arbol");
                        lj.setTree(tree);
                    } else {
                        lj.setLastTime(java.lang.System.currentTimeMillis());
                        lj.setState(LumberJackState.WAITING);
                        java.lang.System.out.println("Me espero");

                    }
            }
        }

    }
}
