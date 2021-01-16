/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Components.People.Miner;
import com.pochitoGames.Engine.Component;

/**
 * @author PochitoMan
 */
public class Quarry extends Component {
    static final float waitTime = 5.0f;

    double currentTime = 0.0f;

    Miner miner = null;
        
    private boolean hasWorker = false;

    public float getWaitTime() {
        return waitTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void addCurrentTime(double seconds) {
        currentTime += seconds;
    }
    
    public void resetCurrentTime(){
        currentTime = 0;
    }

    public Miner getMiner() {
        return miner;
    }

    public void setMiner(Miner miner) {
        this.miner = miner;
    }

    public boolean isHasWorker() {
        return hasWorker;
    }

    public void setHasWorker(boolean hasWorker) {
        this.hasWorker = hasWorker;
    }

}
