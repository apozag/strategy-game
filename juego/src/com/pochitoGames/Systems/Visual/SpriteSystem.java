/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Visual;

import com.pochitoGames.Misc.Other.Time;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Engine.Camera;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Renderer;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.Animation;
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
                srcPos.x = anim.getXoffset() + srcSize.x * (int)(Time.getTicks() / anim.getSpeed() % anim.getFrames());
                s.setSrcSize(srcSize);
                s.setSrcPos(srcPos);
            }
            if(s.isDepthUpdated())
                s.setDepth(p.getWorldPos().y);

            Renderer.getInstance().renderSprite(s);
        }
    }
    
    public static boolean isInsideSprite(Sprite s, Vector2D p){
        Position pos = s.getEntity().get(Position.class);
        Vector2D sPos = pos.getWorldPos();
        if(!pos.isLocked())
            sPos = Camera.getInstance().toCameraCoords(sPos);
        Vector2D sSize = s.getSrcSize();
        return p.x > sPos.x && 
               p.x < sPos.x + sSize.x && 
               p.y > sPos.y && 
               p.y < sPos.y + sSize.y;
    }
}
