/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.People;

import com.pochitoGames.Engine.System;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
/**
 *
 * @author PochitoMan
 */
public class HumanSystem extends System{

    private double hungerTime = 100;
    private float hungerAmount = 1;
    private float hurtAmount = 0.1f;
    
    public HumanSystem(){
        include(Human.class);
        exclude();
    }
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Human human = e.get(Human.class);
            if(!human.isAlive()){
                ECS.getInstance().removeEntity(e);
            }
            else if(human.getHunger() <= 0){
                human.reciveDamage(hurtAmount);
            }
            else{
                long currentTime = java.lang.System.currentTimeMillis();
                if(currentTime - human.getLastHungerTime() >= hungerTime){
                    human.subtractHunger(hungerAmount);
                    human.setLastHungerTime(currentTime);
                }
            }
        }
    }
    
}
