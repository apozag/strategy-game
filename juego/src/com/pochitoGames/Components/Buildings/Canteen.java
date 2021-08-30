/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.Component;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class Canteen extends Component{
    private int capacity = 10;
    private long waitTimeMillis = 5000;
    private Map<Human, Long> people;
    
    public Canteen(){people = new HashMap<>();}        
    
    public boolean addHuman(Human human){
        if(people.size() >= getCapacity())
            return false;        
        people.put(human, java.lang.System.currentTimeMillis());
        human.restoreHunger();
        return true;
    }
    
    public Map<Human, Long> getPeople(){return people;}
    public void removeHuman(Human human){people.remove(human);}
    public long getWaitTimeMillis(){return waitTimeMillis;}
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
