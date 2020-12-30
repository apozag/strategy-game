/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;
import com.pochitoGames.Components.UI.BuildingPicker;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.UIButton;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.BuildingInfo;
import com.pochitoGames.Systems.Buildings.BuildingGeneratorSystem;
/**
 *
 * @author PochitoMan
 */
public class BuildingPickerSystem extends System{
    
    public BuildingPickerSystem(){
        include(UIButton.class, BuildingPicker.class, MouseListener.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            MouseListener ml = e.get(MouseListener.class);
            BuildingPicker bp = e.get(BuildingPicker.class);
            if(ml.downLeft && ml.firstTickLeft){
                BuildingGeneratorSystem.buildingId = bp.getId();
                Entity tempImage = BuildingGeneratorSystem.tempImage;
                Sprite s = tempImage.get(Sprite.class);
                BuildingInfo b = BuildingManager.blueprints.get(bp.getId());
                float yAnchor = 1 - (float) b.size.row / (2 * b.height + b.size.col + b.size.row);
                
                ECS.getInstance().removeComponent(tempImage, s);
                if (bp.getId() == TypeBuilding.FLOOR){
                    ECS.getInstance().addComponent(tempImage, new Sprite(b.image, new Vector2D(0, yAnchor), true, 0.5f));
                }
                else{
                    ECS.getInstance().addComponent(tempImage, new Sprite(b.image, new Vector2D(0, yAnchor), true, 0.5f,  
                        new Animation(1, 1, 128, 128, 0, 0))
                );
                }
            }
        }
    }
    
}
