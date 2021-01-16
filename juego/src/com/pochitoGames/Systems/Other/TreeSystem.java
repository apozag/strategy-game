/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Other;

import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
/**
 *
 * @author PochitoMan
 */
public class TreeSystem extends System{

    public TreeSystem(){
        include(Tree.class, Sprite.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Tree tree = e.get(Tree.class);
            Sprite s = e.get(Sprite.class);
            
            tree.addAge(dt);
            if(tree.getAge() < Tree.ADULT_AGE/2)
                s.setCurrentAnimationIndex(0);
            else if(tree.getAge() < Tree.ADULT_AGE)
                s.setCurrentAnimationIndex(1);
            else
                s.setCurrentAnimationIndex(2);
        }
    }
    
}
