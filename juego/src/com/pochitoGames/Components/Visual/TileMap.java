/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Visual;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Map.TilesetMode;
import com.pochitoGames.Misc.Other.Vector2i;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */
public class TileMap extends Component{
    
    BufferedImage tileset;
    
    private int[][] map;
    
    private int tileW;
    private int tileH;
    private int tilesetW;
    private int tilesetH;
    
    //La imagen del mapa en sí se guarda en el componente Sprite
    //Para no calcularlo cada frame, imageSet es false cuando hay que recalcular la imagen (por ejemplo, se pone carretera en un tile)
    //esto de calcular la imagen de Sprite se hace en TileMapSystem
    boolean imageSet = false;
    List<Vector2i> updatedCells = new ArrayList<>();
    
    TilesetMode mode;
        
    //Parámetros
    //
    // tileset:     El tileset del tilemap
    // map:         El id del tile que va en cada posición (el csv)
    // tileW:       El ancho de un tile.
    // tileH:       El alto de un tile.
    // tilesetW:    El ancho de la imagen del tileset (no el tilemap)
    // tilesetH:    El alto de la imagen del tileset (no el tilemap)
    public TileMap(BufferedImage tileset,int[][]map, int tileW, int tileH, int tilesetW, int tilesetH, TilesetMode mode){
        this.map = map;
        this.tileset = tileset;
        this.tileW = tileW;
        this.tileH = tileH;
        this.tilesetW = tilesetW;
        this.tilesetH = tilesetH;
        this.mode = mode;
    }
    
    public BufferedImage getTileset (){
        return tileset;
    }
    
    public Sprite getSprite(){
        return getEntity().get(Sprite.class);
    }    
    
    public boolean isImageSet(){
        return imageSet;
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
    
    public void markAsSet(){
        imageSet = true;
    }

    /**
     * @return the map
     */
    public int[][] getMap() {
        return map;
    }
    
    public Vector2i getUpdatedCell(int i){return updatedCells.get(i);}
    
    public int getUpdatedCellsSize(){
        return updatedCells.size();
    }
    
    public void clearUpdatedCells(){
        updatedCells.clear();
    }
        
    public void setTile(int id, int col, int row){
        map[col][row] = id;
        imageSet = false;
        updatedCells.add(new Vector2i(col, row));
    }
}
