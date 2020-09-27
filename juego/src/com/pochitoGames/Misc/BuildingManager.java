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
public class BuildingManager {
    private static BuildingManager instance;
    
    
    
    private BuildingManager(){
        
    }
    
    public static BuildingManager getInstance(){
        if(instance == null){
            instance = new BuildingManager();
        }
        return instance;
    }
}
