/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.People;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.Buildings.Refinery;
import com.pochitoGames.Components.Other.Stone;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.States.MinerState;

/**
 * @author PochitoMan
 */
public class Miner extends Component {

    MinerState state = MinerState.WAIT;
    Building quarry;
    //Refinery refinery;
    private Stone mine;
    
    private long waitTime = 1000;  // milliseconds
    private long lastTime = 99999; // milliseconds

    public MinerState getState() {
        return state;
    }

    public void setState(MinerState state) {
        this.state = state;
    }

    public void setQuarry(Building canteen) {
        this.quarry = canteen;
    }

    public Building getQuarry() {
        return quarry;
    }

    public Stone getMine() {
        return mine;
    }

    public void setMine(Stone mine) {
        this.mine = mine;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
}
