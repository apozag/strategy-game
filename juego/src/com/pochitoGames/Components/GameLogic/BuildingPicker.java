/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Engine.Component;

/**
 *
 * @author PochitoMan
 */
public class BuildingPicker extends Component{
    private int buildingId;
    
    public BuildingPicker(int id){
        this.buildingId = id;
    }
    
    public int getId(){
        return buildingId;
    }
}
