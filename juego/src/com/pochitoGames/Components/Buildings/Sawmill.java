/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;

/**
 *
 * @author PochitoMan
 */
public class Sawmill extends Component{
    //La ultima vez en milisegundos que genero piedra
    private double lastTime = 0;
    // Frecuyencia con la que genera piedra en milisegundos
    private double frequency = 2000;   
    
    private int lastWoodAmount = 0;
    
    private boolean hasWorker = false;

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }
    
    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getLastWoodAmount() {
        return lastWoodAmount;
    }

    public void setLastWoodAmount(int lastWoodAmount) {
        this.lastWoodAmount = lastWoodAmount;
    }

    public boolean isHasWorker() {
        return hasWorker;
    }

    public void setHasWorker(boolean hasWorker) {
        this.hasWorker = hasWorker;
    }
}
