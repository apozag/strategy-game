/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.UI;

import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.TreeGenerator;
import com.pochitoGames.Components.UI.UIButton;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.TreeManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Engine.Vector2i;

/**
 * @author PochitoMan
 */
public class TreeGeneratorSystem extends System {

    public TreeGeneratorSystem() {
        include(TreeGenerator.class, MouseListener.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()) {
            MouseListener ml = e.get(MouseListener.class);
            if(ml.downLeft && ml.firstTickLeft) {
                Vector2i cell;
                do{
                    cell = new Vector2i((int) (Math.random() * MapInfo.getInstance().getHeight() - 1), (int) (Math.random() * MapInfo.getInstance().getHeight() - 1));
                }while(MapInfo.getInstance().getTileId(cell) >= 100 || MapInfo.getInstance().getTileId(cell) == 5 || MapInfo.getInstance().getTileId(cell) == 6);
                TreeManager.getInstance().createTree(cell);
            }
        }
    }

}
