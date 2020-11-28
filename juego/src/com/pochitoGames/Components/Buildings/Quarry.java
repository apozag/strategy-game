/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Components.People.Miner;
import com.pochitoGames.Engine.Component;

/**
 *
 * @author PochitoMan
 */
public class Quarry extends Component{
    static final float waitTime = 5.0f;
    
    float currentTime = 0.0f;
    
    int stone = 0;
    
    Miner miner = null;
    
    public float getWaitTime(){return waitTime;}
    
    public float getCurrentTime(){return currentTime;}
    
    public void addCurrentTime(float seconds){currentTime += seconds;}
    
    public int getStoneAmount(){return stone;}
    
    public void addStone(int amount){stone += amount;}
    public void takeStone(int amount){stone -= amount;}    
    
    public Miner getMiner(){return miner;}
    
    public void setMiner(Miner miner){this.miner = miner;}
    
}
