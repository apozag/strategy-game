/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;
import com.pochitoGames.Components.PathFinding;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.MapInfo;
import com.pochitoGames.Misc.Vector2i;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author PochitoMan
 */
public class PathFindingSystem extends System{
    public PathFindingSystem(){
        include(PathFinding.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            PathFinding pf = e.get(PathFinding.class);
            if(!pf.isWalking()){
                Vector2i next = pf.peekNextStep();
                if(next != null){
                    Vector2i v = pf.pollNextStep();
                    pf.setNextPos(TileMapSystem.indexToCartesian(v.col, v.row, MapInfo.getInstance().getActiveTileMap()));
                    pf.setWalking(true);
                }
                else{
                    //aStar();
                }
            }
            else{
                Position p = e.get(Position.class);
                if(p.getWorldPos().equals(pf.getNextPos())){
                    Vector2D dir = Vector2D.mult(Vector2D.sub(pf.getNextPos(), p.getWorldPos()).normalized(), 50.0f);
                    p.setLocalPos(Vector2D.add(p.getLocalPos(), dir));
                }
            }
        }
    }
    
    public static Queue<Vector2i> aStar(Vector2i start, Vector2i end){
        Queue<Vector2i> steps = new LinkedList<>();
        //algoritmo
        return steps;
    }
    
}
