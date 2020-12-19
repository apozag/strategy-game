/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;

import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.PeopleGenerator;
import com.pochitoGames.Components.UI.UIButton;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.Vector2i;

/**
 *
 * @author PochitoMan
 */

public class PeopleGeneratorSystem extends System{

    public PeopleGeneratorSystem(){
        include(UIButton.class, PeopleGenerator.class, MouseListener.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            MouseListener ml = e.get(MouseListener.class);
            PeopleGenerator pg = e.get(PeopleGenerator.class);
            if(ml.down && ml.firstTick){
                PeopleManager.getInstance().createCharacter(pg.getType(), pg.getRole(), new Vector2i(0, 0));
            }
        }
    }
    
}
