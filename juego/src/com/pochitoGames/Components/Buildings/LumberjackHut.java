/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Components.People.LumberJack;
import com.pochitoGames.Engine.Component;

/**
 *
 * @author PochitoMan
 */
public class LumberjackHut extends Component{
    private LumberJack lumberjack;
    private int lastWood = 0;
    private boolean hasWorker = false;
    
    public LumberjackHut(){
        
    }

    public LumberJack getLumberjack() {
        return lumberjack;
    }

    public void setLumberjack(LumberJack lumberjack) {
        this.lumberjack = lumberjack;
    }

    public int getLastWood() {
        return lastWood;
    }

    public void setLastWood(int lastWood) {
        this.lastWood = lastWood;
    }

    public boolean isHasWorker() {
        return hasWorker;
    }

    public void setHasWorker(boolean hasWorker) {
        this.hasWorker = hasWorker;
    }
    
    
}
