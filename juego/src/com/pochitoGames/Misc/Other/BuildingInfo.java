/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Other;

/**
 *
 * @author PochitoMan
 */
public class BuildingInfo{
    public int id;
    public Vector2i entry;
    public Vector2i size;
    public int height;
    
    public BuildingInfo(int id, Vector2i entry, Vector2i size, int height){
        this.id = id;
        this.entry = entry;
        this.size = size;
        this.height = height;
    }
    
    public BuildingInfo(BuildingInfo b){
        this.id = b.id;
        this.entry = b.entry;
        this.size = b.size;
        this.height = b.height;
    }
}
