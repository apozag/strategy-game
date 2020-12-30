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
public class Refinery extends Component{
    //La ultima vez en milisegundos que genero piedra
    private double lastTime = 0;
    // Frecuyencia con la que genera piedra en milisegundos
    private double frequency = 2000;   
    
    private int lastStoneAmount = 0;
    
    public Refinery(long frequency){
        this.frequency = frequency;
    }

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public int getLastStoneAmount() {
        return lastStoneAmount;
    }

    public void setLastStoneAmount(int lastStoneAmount) {
        this.lastStoneAmount = lastStoneAmount;
    }
}
