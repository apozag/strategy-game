/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc;

/**
 *
 * @author PochitoMan
 */
public class TileInfo {
    private int id;
    private boolean walkable;
    private float walkCost;
    
    public TileInfo(int id, float walkCost, boolean walkable){
        this.id = id;
        this.walkable = walkable;
        this.walkCost = walkCost;
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the walkable
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * @return the walkCost
     */
    public float getWalkCost() {
        return walkCost;
    }

}
