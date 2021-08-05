/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author PochitoMan
 */
public class BuildingPicker extends Component{
    private TypeBuilding buildingId;
    
    public BuildingPicker(Node node){
        Element e = (Element) node;
        String buildingString = e.getElementsByTagName("building").item(0).getTextContent();
        this.buildingId = TypeBuilding.valueOf(buildingString);
    }
    
    public BuildingPicker(TypeBuilding id){
        this.buildingId = id;
    }
    
    public TypeBuilding getId(){
        return buildingId;
    }
}
