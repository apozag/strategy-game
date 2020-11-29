/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Miner;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.States.MinerState;
import com.pochitoGames.Misc.States.SoldierState;

import java.util.HashSet;

/**
 * @author PochitoMan
 */
public class MinerSystem extends System {

    public MinerSystem() {
        include(Miner.class, PathFinding.class);
        exclude(Builder.class, Worker.class);
    }

    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            Miner miner = e.get(Miner.class);
            PathFinding pf = e.get(PathFinding.class);
            MinerState minerState = miner.getState();
            switch (minerState) {
                case WAIT:
                    break;
                case WALKING_CANTEEN:
                    if (pf.getTargetCell() == null) miner.setState(MinerState.WAIT);
                    break;
                case CANTERING:

                    break;
            }
        }
    }
}
