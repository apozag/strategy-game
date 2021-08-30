/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.Other.Backpack;
import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Stone;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Miner;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.StoneManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.States.MinerState;
import com.pochitoGames.Misc.States.SoldierState;

import java.util.HashSet;

/**
 * @author PochitoMan
 */
public class MinerSystem extends System {

    public MinerSystem() {
        include(Miner.class, PathFinding.class, Backpack.class);
        exclude(Builder.class, Worker.class);
    }

    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            Miner miner = e.get(Miner.class);
            PathFinding pf = e.get(PathFinding.class);
            MinerState minerState = miner.getState();
            Backpack backpack = e.get(Backpack.class);
            
            switch (minerState) {
                case WAIT:
                    // Cada 'waitTime' segundos buscamos piedra
                    if(miner.getQuarry() != null && java.lang.System.currentTimeMillis() - miner.getLastTime() > miner.getWaitTime()){
                        miner.setState(MinerState.SEARCH_MINE);
                    }
                    break;
                case SEARCH_MINE:
                    // Si encontramos piedra, vamos a por ella
                    Stone s = StoneManager.getInstance().getNearestStone(pf.getCurrent());
                    if(s == null){
                        // Si no, volvemos a wait;
                        miner.setLastTime(java.lang.System.currentTimeMillis());
                        miner.setState(MinerState.WAIT);
                    }
                    else{
                        s.taken = true;
                        miner.setMine(s);
                        miner.setState(MinerState.WALKING_MINE);
                        pf.setTargetCell(MapInfo.getInstance().getCloseCell(s.cell, false, false));
                        pf.start();
                    }
                    break;
                case WALKING_MINE:                          
                    // SI hemos llegado, a minar
                    if(pf.getTargetCell() == null){
                        miner.setLastTime(java.lang.System.currentTimeMillis());
                        miner.setState(MinerState.MINING);
                    }
                    break;
                case MINING:
                    // Si terminamos de minar, eliminamos la piedra y vamos al quary
                    if(java.lang.System.currentTimeMillis() - miner.getLastTime() > miner.getWaitTime()){
                        StoneManager.getInstance().removeStone(miner.getMine());
                        pf.setTargetCell(miner.getQuarry().getEntryCell());    
                        pf.start();
                        miner.setState(MinerState.WALKING_QUARRY);
                        backpack.setCarrying(miner.getMine().type);
                    }
                    break;
                case WALKING_QUARRY:
                    if(pf.getTargetCell() == null){
                        Warehouse wh = miner.getQuarry().getEntity().get(Warehouse.class);
                        wh.putContent(miner.getMine().type, 1);
                        miner.setLastTime(java.lang.System.currentTimeMillis());
                        miner.setState(MinerState.WAIT);
                        backpack.setCarrying(null);
                    }
                    break;
                case WALKING:
                    if(pf.getTargetCell() == null){
                        miner.setLastTime(java.lang.System.currentTimeMillis());
                        miner.setState(MinerState.WAIT);
                    }
                    break;
            }
        }
    }
}
