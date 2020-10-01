/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Components.TileMap;
import com.pochitoGames.Components.TileSelector;
import com.pochitoGames.Engine.Camera;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.MapInfo;
/**
 *
 * @author PochitoMan
 */
public class TileSelectorSystem extends System{

    //BufferedImage selectorAux = ImageManager.getImage("src\\com\\pochitoGames\\Resources\\Sprites\\selector.png");
    
    public TileSelectorSystem(){
        include(Position.class, Sprite.class, TileSelector.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            Position p = ((Position)(e.get(Position.class)));
            TileSelector ts = ((TileSelector)(e.get(TileSelector.class)));
            TileMap tm = ts.getMap();
                        
            Vector2D mousePos = Camera.getInstance().toWorldCoords(EventManager.getInstance().getMousePos());
            if(mousePos.x > 0 && mousePos.y > 0){
                Vector2D offset = new Vector2D(mousePos.x%tm.getTileW(), mousePos.y%tm.getTileH());
                
                Vector2D translatedMouse = new Vector2D(mousePos.x - (MapInfo.getInstance().getMap().length) * tm.getTileW() / 2, mousePos.y);
                Vector2D selected = new Vector2D(0, 0);
                
                selected.x = ((translatedMouse.x * 2 / tm.getTileW()) + translatedMouse.y * 2 / tm.getTileH()) /2;
                selected.y = (translatedMouse.y * 2 / tm.getTileH() - (translatedMouse.x * 2 / tm.getTileW())) /2;

                Vector2D selectorPos = TileMapSystem.indexToCartesian((int)selected.x-1, (int)selected.y-1, tm);
                /*
                Color c = new Color(selectorAux.getRGB((int)offset.x,(int)offset.y), true);
                
                Vector2D xTransform = new Vector2D(tm.getTileW()/2, tm.getTileH()/2);
                Vector2D yTransform = new Vector2D(-tm.getTileW()/2, tm.getTileH()/2);
                
                if(c.getAlpha() > 0){
                    if(c.getRed() == 255){
                        selectorPos.sub(xTransform);
                        selected.x--;
                    }
                    else if(c.getBlue() == 255){
                        selectorPos.sub(yTransform);
                        selected.y--;
                    }
                    else if(c.getGreen() == 255){
                        selectorPos.add(yTransform);
                        selected.y++;
                    }
                    else{
                        selectorPos.add(xTransform);
                        selected.x++;
                    }
                }
                */
                p.setLocalPos(selectorPos);
                
                ts.select((int)selected.x, (int)selected.y);                
            }   
                
        }       
    }           
   
   
}