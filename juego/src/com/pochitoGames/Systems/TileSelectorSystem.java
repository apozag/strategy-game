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
            Position p = ((Position)(e.get(Position.class)));
            TileSelector ts = ((TileSelector)(e.get(TileSelector.class)));
            TileMap tm = ts.getMap();
                        
            Vector2D mousePos = Camera.getInstance().toWorldCoords(EventManager.getInstance().getMousePos());
            if(mousePos.x > 0 && mousePos.y > 0){
                Vector2D offset = new Vector2D(mousePos.x%tm.getTileW(), mousePos.y%tm.getTileH());
                Vector2D selectedCell = new Vector2D((int)(mousePos.x/tm.getTileW()),(int)(mousePos.y/(tm.getTileH()) - 2));
                Vector2D cellPos = new Vector2D(selectedCell.x * tm.getTileW(), selectedCell.y * (tm.getTileH()));
                
                Color c = new Color(selectorAux.getRGB((int)offset.x,(int)offset.y), true);

                Vector2D xTransform = new Vector2D(tm.getTileW()/2, tm.getTileH()/2);
                Vector2D yTransform = new Vector2D(-tm.getTileW()/2, tm.getTileH()/2);

                if(c.getAlpha() > 0){
                    if(c.getRed() == 255){
                        cellPos.sub(xTransform);
                    }
                    else if(c.getBlue() == 255){
                        cellPos.sub(yTransform);
                    }
                    else if(c.getGreen() == 255){
                        cellPos.add(yTransform);
                    }
                    else{
                        cellPos.add(xTransform);
                    }
                }

                p.setLocalPos(cellPos);
                
                selectedCell = new Vector2D(cellPos.x*2 / tm.getTileW(), cellPos.y*2/tm.getTileH());

                ts.select((int)selectedCell.x, (int)selectedCell.y);
                java.lang.System.out.println("Selected (" + (int)selectedCell.x + " ," +  (int)selectedCell.y + ")");

            }
            
        }
    }
}