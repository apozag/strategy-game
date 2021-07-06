/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.GameInfoManager;
import com.pochitoGames.Engine.Vector2i;
import java.awt.Color;

/**
 *
 * @author PochitoMan
 */
public class BuildingGeneratorSystem extends System{

    public static TypeBuilding buildingId = null;
    public static Entity tempImage;
    
    public BuildingGeneratorSystem(){
        tempImage = ECS.getInstance().createEntity(null,
                new Position(new Vector2D(0, 0)),
                new Sprite("", new Vector2D(0, 0), true,0.5f));
        include(TileSelector.class, Sprite.class, Position.class, MouseListener.class);
        exclude();
    }
    
    @Override
    public void update(double dt) {        
        for(Entity e : getEntities()){
            if(tempImage.getParent() == null)
                tempImage.setParent(e);
            MouseListener ml = e.get(MouseListener.class);
            TileSelector ts = e.get(TileSelector.class);
            Sprite s = e.get(Sprite.class);
            Vector2i selected = ts.getSelected();                    
            Sprite tempS = tempImage.get(Sprite.class);            
            
            if(buildingId != null){    
                ml.layer = 0;
                s.setVisible(false);
                // Si no se puede construir, se pone en rojo la imagen                
                if(!BuildingManager.getInstance().canBuild(buildingId, selected)){
                    tempS.dye(Color.red);
                }
                else{
                    tempS.dye(Color.white);
                    if(ml.downLeft && ml.firstTickLeft){                
                        // Quitamos imagen transparente
                        /////////////////////// CUIDADO ///////////////////////////////
                        // ¡Posible bug si cambia el orden de update de los sistemass!
                        //////////////////////////////////////////////////////////////
                        if(BuildingManager.getInstance().build(GameInfoManager.getInstance().getPlayerType(), buildingId, selected) && 
                                buildingId != TypeBuilding.FLOOR){
                            buildingId = null;
                            tempS.setTransparency(0.0f);
                        }
                    }
                    
                }
                
                if(ml.downRight && ml.firstTickRight){
                        buildingId = null;
                        tempS.setTransparency(0.0f);
                    }
            }
            else{
                s.setVisible(true);
                ml.layer = -1;
            }
        }
    }
    
}
