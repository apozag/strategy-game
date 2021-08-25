/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author PochitoMan
 */
public class School extends Component{
    
    Queue<TypeRole> queue;
    
    private double lastTime = 0;
    private double frequency = 5000;
    
    private boolean initialized = false;
    
    public School(){ queue = new PriorityQueue<>();}
    
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    
    public void addToQueue(TypeRole type){
        queue.add(type);
    }
    
    public TypeRole popQueue(){
        return queue.poll();
    }

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }

    public double getFrequency() {
        return frequency;
    }

    /**
     * @return the initialized
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * @param initialized the initialized to set
     */
    public void setInitialized() {
        this.initialized = true;
    }
}
