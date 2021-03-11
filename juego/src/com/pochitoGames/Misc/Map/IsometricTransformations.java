/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Map;

import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Engine.Vector2i;

/**
 *
 * @author PochitoMan
 */
public class IsometricTransformations {
    public static Vector2D isoToCartesian(Vector2i iso){
        TileMap tm = MapInfo.getInstance().getActiveTileMap();
        Vector2D xTransform = new Vector2D(tm.getTileW()/2, tm.getTileH()/2);
        Vector2D yTransform = new Vector2D(-tm.getTileW()/2, tm.getTileH()/2);
        Vector2D pos = Vector2D.add(Vector2D.mult(xTransform, iso.col), Vector2D.mult(yTransform, iso.row));
        pos.x += (tm.getMap().length-1) * (tm.getTileW()/2);
        return pos;
    }
    public static Vector2i cartesianToIso(Vector2D pos){
        TileMap tm = MapInfo.getInstance().getActiveTileMap();
        
        Vector2D offset = new Vector2D(pos.x%tm.getTileW(), pos.y%tm.getTileH());
                
        Vector2D translatedCartesian = new Vector2D(pos.x - (MapInfo.getInstance().getMap().length) * tm.getTileW() / 2, pos.y);
        Vector2i selected = new Vector2i(0, 0);

        selected.col = (int)(((translatedCartesian.x * 2 / tm.getTileW()) + translatedCartesian.y * 2 / tm.getTileH()) /2);
        selected.row = (int)((translatedCartesian.y * 2 / tm.getTileH() - (translatedCartesian.x * 2 / tm.getTileW())) /2);
        
        return selected;
    }
}
