/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;


import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.School;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.PanelActivator;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Engine.UIManager;

/**
 *
 * @author PochitoMan
 */
public class SchoolSystem extends System{
    
    
    private static School activeSchool;
    
    public SchoolSystem(){
        include(School.class, Building.class, MouseListener.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Building b = e.get(Building.class);
            School s = e.get(School.class);
            MouseListener ml = e.get(MouseListener.class);
            
            if(b.isFinished()){
                
                if(!s.isInitialized()){
                    ECS.getInstance().addComponent(e, new PanelActivator("SCHOOL"));
                    s.setInitialized();
                }
                
                if(!s.isQueueEmpty()){
                    if(java.lang.System.currentTimeMillis() - s.getLastTime() > s.getFrequency()){
                        s.setLastTime(java.lang.System.currentTimeMillis());
                        PeopleManager.getInstance().createCharacter(b.getOwnerType(), s.popQueue(), b.getEntryCell());
                    }
                }
                
                if(ml.firstTickLeft){
                    activeSchool = s;
                }                
            }
        }
    }
    
    public static void addToQueue(TypeRole role){
        activeSchool.addToQueue(role);
    }
    
}
