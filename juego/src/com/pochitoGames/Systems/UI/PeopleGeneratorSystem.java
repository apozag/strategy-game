/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;

import com.pochitoGames.Components.GameLogic.PeopleGenerator;
import com.pochitoGames.Components.Visual.UIButton;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.PeopleManager;

/**
 *
 * @author PochitoMan
 */

public class PeopleGeneratorSystem extends System{

    public PeopleGeneratorSystem(){
        include(UIButton.class, PeopleGenerator.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            UIButton b = e.get(UIButton.class);
            PeopleGenerator pg = e.get(PeopleGenerator.class);
            if(b.down && b.firstTick){
                PeopleManager.getInstance().createCharacter(pg.getId());
            }
        }
    }
    
}
