/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Visual;

import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.SeeThrough;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
/**
 *
 * @author PochitoMan
 */
public class SeeThroughSystem extends System{

    public SeeThroughSystem(){
        include(SeeThrough.class, MouseListener.class, Sprite.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            MouseListener ml = e.get(MouseListener.class);
            Sprite s = e.get(Sprite.class);
            if(ml.mouseOver)
                s.setTransparency(0.5f);
            else
                s.setTransparency(1.0f);
        }
    }
    
}
