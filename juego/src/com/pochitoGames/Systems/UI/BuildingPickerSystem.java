/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;
import com.pochitoGames.Components.GameLogic.BuildingPicker;
import com.pochitoGames.Components.Visual.UIButton;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Systems.Buildings.BuildingGeneratorSystem;
/**
 *
 * @author PochitoMan
 */
public class BuildingPickerSystem extends System{
    
    public BuildingPickerSystem(){
        include(UIButton.class, BuildingPicker.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            UIButton b = e.get(UIButton.class);
            BuildingPicker bp = e.get(BuildingPicker.class);
            if(b.down && b.firstTick){
                BuildingGeneratorSystem.buildingId = bp.getId();
            }
        }
    }
    
}
