/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Visual;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Components.Visual.Text;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Renderer;
/**
 *
 * @author PochitoMan
 */
public class TextSystem extends System{

    
    public TextSystem(){
        include(Text.class, Position.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Text text = e.get(Text.class);
            Renderer.getInstance().renderText(text);
        }
    }
    
}
