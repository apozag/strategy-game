/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Visual;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Engine.Vector2i;
import com.pochitoGames.Misc.Map.TilesetMode;
import java.awt.image.BufferedImage;

/**
 *
 * @author PochitoMan
 */
public class CompoundTileMap extends Component{
    
    TilesetMode mode;
    
    int tileW, tileH;
    
    int tilesetW, tilesetH;
    
    TileMap tileMaps[][];
    
    int map[][];
    
    private Vector2i maxMapSize = new Vector2i(30, 30);
    
    public CompoundTileMap(BufferedImage tileset, int[][]map, int tileW, int tileH, int tilesetW, int tilesetH, TilesetMode mode){
        
        this.mode = mode;
        this.tileW = tileW;
        this.tileH = tileH;
        this.tilesetW = tilesetW;
        this.tilesetH = tilesetH;
        this.map = map;
        
        int width = map.length;
        int height = map[0].length;
        
        int mapMatH = height/maxMapSize.row;
        int mapMatW = width/maxMapSize.col;
        
        tileMaps = new TileMap[mapMatW][mapMatH];
        
        for(int i = 0; i < mapMatW; i++){
            for(int j = 0; j < mapMatH; j++){
                int[][] subMap = new int[maxMapSize.col][maxMapSize.row];
                for(int col = 0; col < maxMapSize.col; col++){
                    for(int row = 0; row < maxMapSize.row; row++){
                        int srcCol = i * maxMapSize.col + col;
                        int srcRow = j * maxMapSize.row + row;
                        subMap[col][row] = map[srcCol][srcRow];
                    }
                }
                
                int mapSizeX = Math.min(30, map.length    - i*maxMapSize.col);
                int mapSizeY = Math.min(30, map[0].length - j*maxMapSize.row);
                
                tileMaps[i][j] = new TileMap(tileset, subMap, tileW, tileH, tilesetW, tilesetH, mode);
                Vector2D xIsoTransform = new Vector2D( mapSizeX * tileW/2, mapSizeY * tileH/2);
                Vector2D yIsoTransform = new Vector2D(-mapSizeX * tileW/2, mapSizeY * tileH/2);
                Vector2D pos = Vector2D.add(Vector2D.mult(xIsoTransform, i), Vector2D.mult(yIsoTransform, j));
                pos.x += (mapMatW - 1) * tileW * mapSizeX /2;
                ECS.getInstance().createEntity(null, 
                    new Sprite(),
                    new Position(pos),
                    tileMaps[i][j]
                );
            }
        }
    }
    
    public void setTile(int id, int col, int row){
        int outerCol = col / maxMapSize.col;
        int outerRow = row / maxMapSize.row;
        
        int innerCol = col - outerCol * maxMapSize.col;
        int innerRow = row - outerRow * maxMapSize.row;
        
        tileMaps[outerCol][outerRow].setTile(id, innerCol, innerRow);
    }
    
    public TilesetMode getMode(){
        return mode;
    }
    

    /**
     * @return the tileW
     */
    public int getTileW() {
        return tileW;
    }

    /**
     * @return the tileH
     */
    public int getTileH() {
        return tileH;
    }

    /**
     * @return the tilesetW
     */
    public int getTilesetW() {
        return tilesetW;
    }

    /**
     * @return the tilesetH
     */
    public int getTilesetH() {
        return tilesetH;
    }
    /**
     * @return the map
     */
    public int[][] getMap() {
        return map;
    }
    
    public TileMap getTileMap(int col, int row){
        return tileMaps[col][row];
    }

    /**
     * @return the maxMapSize
     */
    public Vector2i getMaxMapSize() {
        return maxMapSize;
    }
}
