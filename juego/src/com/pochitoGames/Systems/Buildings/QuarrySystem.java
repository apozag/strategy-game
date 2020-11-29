/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.People.Miner;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.States.MinerState;
/**
 *
 * @author PochitoMan
 */
public class QuarrySystem extends System{
    public QuarrySystem(){
        include(Quarry.class, Building.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e: getEntities()){
            Quarry quarry = e.get(Quarry.class);
            Building building = e.get(Building.class);
            if(building.isFinished() && quarry.getMiner() == null){
                Miner miner = PeopleManager.getInstance().getNearestMiner(building.getOwnerType(), building.getCell());
                if(miner != null){
                    PathFinding pf = miner.getEntity().get(PathFinding.class);
                    miner.setState(MinerState.WALKING_CANTEEN);
                    miner.setQuarry(quarry);
                    pf.setTargetCell(building.getEntryCell());
                    quarry.setMiner(miner);
                    //TODO : Generar piedra, y que un worker vaya a bucarla
                    //TODO: : Miner se mueva y cuando vuelva cree piedra
                }
            }
        }
    }
}
