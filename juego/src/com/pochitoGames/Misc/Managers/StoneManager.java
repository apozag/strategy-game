/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Stone;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.SeeThrough;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Vector2i;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */
public class StoneManager {
    private static StoneManager instance;
    
    private List<Stone> stone = new LinkedList<>();
    
    private StoneManager(){}
    
    public static StoneManager getInstance(){
        if(instance == null)
            instance = new StoneManager();
        return instance;
    }
    
    public void createStone(Vector2i cell) {
        Stone s = new Stone();
         ECS.getInstance().createEntity(null, 
                new Position(IsometricTransformations.isoToCartesian(cell)),
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\stone.png", new Vector2D(0.0f, 0.5f), true, 1.0f),
                new SeeThrough(),
                new MouseListener(0),
                s
        );
         MapInfo.getInstance().setTileId(cell.col, cell.row, 201);
         stone.add(s);
    }
    
    public void removeStone(Stone stone){
        this.stone.remove(stone);
        ECS.getInstance().removeEntity(stone.getEntity());
        MapInfo.getInstance().setTileId(stone.cell.col, stone.cell.row, 4);
    }
}
