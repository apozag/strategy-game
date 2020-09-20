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
import com.pochitoGames.Engine.ImageManager;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 *
 * @author PochitoMan
 */
public class TileSelectorSystem extends System{

    BufferedImage selectorAux = ImageManager.getImage("src\\com\\pochitoGames\\Resources\\Sprites\\selector.png");
    
    public TileSelectorSystem(){
        include(Position.class, Sprite.class, TileSelector.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            //Sprite s = ((Sprite)(e.get(Sprite.class)));
            Position p = ((Position)(e.get(Position.class)));
            TileSelector tl = ((TileSelector)(e.get(TileSelector.class)));
            TileMap tm = tl.getMap();
            Vector2D mousePos = EventManager.getInstance().getMousePos();
            //p.setLocalPos(snapToGrid(mousePos, tm));
            //Vector2D cellPos = new Vector2D(mousePos.x/tm.getTileW(), mousePos.y / tm.getTileH());
            //Vector2D offset = new Vector2D(mousePos.x % tm.getTileW(), mousePos.y % tm.getTileH() );
            p.setLocalPos(toIso(mousePos, tm));
        }
    }
    
    Vector2D toIso(Vector2D p, TileMap tm){
        Vector2D offset = new Vector2D(p.x%tm.getTileW(), p.y%tm.getTileH());
        p = Camera.getInstance().toWorldCoords(p);
        Vector2D cellPos = new Vector2D((int)(p.x/tm.getTileW()) * tm.getTileW(), (int)(p.y/(tm.getTileH()) - 2) * (tm.getTileH()));
        
        Color c = new Color(selectorAux.getRGB((int)offset.x,(int)offset.y));
        
        Vector2D xTransform = new Vector2D(tm.getTileW()/2, tm.getTileH()/2);
        Vector2D yTransform = new Vector2D(-tm.getTileW()/2, tm.getTileH()/2);
        
        if(c.getAlpha() > 0){
            if(c.getRed() == 255)
                cellPos.sub(xTransform);
            else if(c.getBlue() == 255)
                cellPos.sub(yTransform);
            else if(c.getGreen() == 255)
                cellPos.add(yTransform);
            else
                cellPos.add(xTransform);
        }
        
        return cellPos;
    }    
    
    public Vector2D snapToGrid(Vector2D p, TileMap tm){
        /*
        Vector2D mapPos = ((Position)(tm.getEntity().get(Position.class))).getWorldPos();
        mapPos.x += ((Sprite)(tm.getEntity().get(Sprite.class))).getImage().getWidth()/2;
        mapPos = Camera.getInstance().toCameraCoords(mapPos);
*/
        double tx = Math.ceil(p.x /  tm.getTileW()/2) + (p.y / tm.getTileH()/2);
        double ty = Math.ceil(p.y / tm.getTileH()/2) - (p.x / tm.getTileW()/2);

        
        Vector2D xTransform = new Vector2D(tm.getTileW()/2, tm.getTileH()/2);
        Vector2D yTransform = new Vector2D(-tm.getTileW()/2, tm.getTileH()/2);

        return Vector2D.add(Vector2D.mult(xTransform, (float)tx), Vector2D.mult(yTransform,(float)ty));
    }
    
}
