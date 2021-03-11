/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Visual;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Engine.Vector2i;

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

            if(!tileMap.isImageSet()){
                updateImage(tileMap);
                tileMap.clearUpdatedCells();
            }
        }
    }
    
    void updateImage(TileMap tm){
        BufferedImage image = null;
        Graphics2D g2d;
        BufferedImage tile;
        int[][] map = tm.getMap();
        switch(tm.getMode()){
            case ORTHOGONAL:
                image = new BufferedImage(map.length * tm.getTileW(), map[0].length * tm.getTileH() ,TYPE_INT_ARGB);
                g2d = image.createGraphics();
                for(int i = 0; i < map.length; i++){
                    for(int j = 0; j < map[i].length; j++){
                        int row = map[i][j] / tm.getTilesetW();
                        int column = map[i][j] - row * tm.getTilesetW();
                        tile = tm.getTileset().getSubimage(column*tm.getTileW(), row * tm.getTileH(), tm.getTileW(), tm.getTileH());
                        g2d.drawImage(tile, i*tm.getTileW() + image.getWidth()/2, j*tm.getTileH(), null);
                    }
                }
                break;
            case ISOMETRIC:
                if(tm.getUpdatedCellsSize() > 0){
                    g2d = (Graphics2D) tm.getSprite().getImage().getGraphics();
                    for(int i = 0; i < tm.getUpdatedCellsSize(); i++){
                        Vector2i cell = tm.getUpdatedCell(i);
                        int row = tm.getMap()[cell.col][cell.row] / tm.getTilesetW();
                        int column = tm.getMap()[cell.col][cell.row] - row * tm.getTilesetW();
                        tile = tm.getTileset().getSubimage(column * tm.getTileW(), row * tm.getTileH(), tm.getTileW(), tm.getTileH());
                        Vector2D pos = IsometricTransformations.isoToCartesian(new Vector2i(cell.col, cell.row));
                        g2d.drawImage(tile, (int)pos.x, (int)pos.y, null);
                    }
                }
                else{
                    image = new BufferedImage((map.length + map[0].length) * tm.getTileW()/2, (map.length + map[0].length) * tm.getTileH()/2, TYPE_INT_ARGB);
                    g2d = image.createGraphics();
                    for(int i = 0; i < map.length; i++){
                        for(int j = 0; j < map[i].length; j++){
                            int row = map[i][j] / tm.getTilesetW();
                            int column = map[i][j] - row * tm.getTilesetW();
                            Vector2D tilePos = IsometricTransformations.isoToCartesian(new Vector2i(i, j));//indexToCartesian(i, j, tm);
                            tile = tm.getTileset().getSubimage(column*tm.getTileW(), row * tm.getTileH(), tm.getTileW(), tm.getTileH());
                            g2d.drawImage(tile, (int)tilePos.x, (int)tilePos.y , null);
                        }
                    }
                    tm.getSprite().setImage(image);
                }
                break;        
        }               
        
        tm.getSprite().setDepth(-10);
        tm.markAsSet();
    }    
}
