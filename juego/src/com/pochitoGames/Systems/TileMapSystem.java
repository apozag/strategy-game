/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Components.TileMap;
import com.pochitoGames.Engine.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
/**
 *
 * @author PochitoMan
 */
public class TileMapSystem extends System{
    
    public TileMapSystem(){
        include(TileMap.class, Position.class, Sprite.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            
            TileMap tileMap = e.get(TileMap.class);
            
            
            
            if(!tileMap.isImageSet())
                updateImage(tileMap);
        }
    }
    
    void updateImage(TileMap tm){
        BufferedImage image = null;
        Graphics2D g2d;
        BufferedImage tile;
        switch(tm.getMode()){
            case ORTHOGONAL:
                image = new BufferedImage(tm.getMap().length * tm.getTileW(), tm.getMap()[0].length * tm.getTileH() ,TYPE_INT_ARGB);
                g2d = image.createGraphics();
                for(int i = 0; i < tm.getMap().length; i++){
                    for(int j = 0; j < tm.getMap()[i].length; j++){
                        int row = tm.getMap()[i][j] / tm.getTilesetW();
                        int column = tm.getMap()[i][j] - row * tm.getTilesetW();
                        tile = tm.getTileset().getSubimage(column*tm.getTileW(), row * tm.getTileH(), tm.getTileW(), tm.getTileH());
                        g2d.drawImage(tile, i*tm.getTileW() + image.getWidth()/2, j*tm.getTileH(), null);
                    }
                }
                break;
            case ISOMETRIC:
                image = new BufferedImage(tm.getMap().length * tm.getTileW(), tm.getMap()[0].length * tm.getTileH(), TYPE_INT_ARGB);
                g2d = image.createGraphics();
                Vector2D xTransform = new Vector2D(tm.getTileW()/2, tm.getTileH()/2);
                Vector2D yTransform = new Vector2D(-tm.getTileW()/2, tm.getTileH()/2);
                for(int i = 0; i < tm.getMap().length; i++){
                    for(int j = 0; j < tm.getMap()[i].length; j++){
                        int row = tm.getMap()[i][j] / tm.getTilesetW();
                        int column = tm.getMap()[i][j] - row * tm.getTilesetW();
                        Vector2D tilePos = Vector2D.add(Vector2D.mult(xTransform, i), Vector2D.mult(yTransform, j));
                        tile = tm.getTileset().getSubimage(column*tm.getTileW(), row * tm.getTileH(), tm.getTileW(), tm.getTileH());
                        g2d.drawImage(tile, (int)tilePos.x + image.getWidth()/2, (int)tilePos.y , null);
                    }
                }
                break;        
        }               
        tm.getSprite().setImage(image);
        tm.getSprite().setDepth(-10);
        tm.markAsSet();
    }
    
    
}
