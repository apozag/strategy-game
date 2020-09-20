/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import com.pochitoGames.Systems.SpriteSystem;
/**
 *
 * @author PochitoMan
 */

//Esto no se usas de momento
public class Scene {
    
    private ECS ecs;
    
    public Scene(){
        ecs = new ECS();
        
    }
    
    public void init(){

    }
    
    public void update(double dt){
        ecs.update(dt);
    }
    
    public void addSystems(System... systems){
        ecs.addSystems(systems);
    }
    
    public void addEntity(Component... components){
    }
    
}
