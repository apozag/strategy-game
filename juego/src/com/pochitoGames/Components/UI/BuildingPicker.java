/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;

/**
 *
 * @author PochitoMan
 */
public class BuildingPicker extends Component{
    private TypeBuilding buildingId;
    
    public BuildingPicker(TypeBuilding id){
        this.buildingId = id;
    }
    
    public TypeBuilding getId(){
        return buildingId;
    }
}
