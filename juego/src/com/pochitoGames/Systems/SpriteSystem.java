/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;

import com.pochitoGames.Misc.Time;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Engine.Camera;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Renderer;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Animation;
/**
 *
 * @author PochitoMan
 */
public class SpriteSystem extends System{  
    
    public SpriteSystem(){
        include(Sprite.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Sprite s = e.get(Sprite.class);
            Position p = e.get(Position.class);
            
            if(s.getCurrentAnimationIndex() >= 0){
                Animation anim = s.getCurrentAnimation();
                Vector2D srcSize = anim.getSize();
                Vector2D srcPos = new Vector2D();
                srcPos.y = anim.getYoffset();
                srcPos.x = srcSize.x * (int)(Time.getTicks() / anim.getSpeed() % anim.getFrames());
                s.setSrcSize(srcSize);
                s.setSrcPos(srcPos);
            }
            if(s.isDepthUpdated())
                s.setDepth(p.getWorldPos().y);

            Renderer.getInstance().renderSprite(s);
        }
    }
    
    public static boolean isInsideSprite(Sprite s, Vector2D p){
        Vector2D sPos = Camera.getInstance().toCameraCoords(((Position)(s.getEntity().get(Position.class))).getWorldPos());
        Vector2D sSize = s.getSrcSize();
        return p.x > sPos.x && 
               p.x < sPos.x + sSize.x && 
               p.y > sPos.y && 
               p.y < sPos.y + sSize.y;
    }
}
