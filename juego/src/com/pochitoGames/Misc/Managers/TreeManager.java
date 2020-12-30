/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
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
public class TreeManager {
    private static TreeManager instance;
    
    List<Tree> trees = new LinkedList<>();
    
    private TreeManager(){
        
    }
    public static TreeManager getInstance(){
        if(instance == null){
            instance = new TreeManager();
        }
        return instance;
    }
    
    public void createTree(Vector2i cell){
        Tree tree = new Tree();
        ECS.getInstance().createEntity(null, 
                new Position(IsometricTransformations.isoToCartesian(cell)),
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\TREE.png", new Vector2D(0.5f, 0.0f), true, 1.0f),
                tree
        );
        
        trees.add(tree);
    }
    
    public Tree getNearestTree(){
        if(trees.isEmpty())
            return null;
        return trees.get(0);
    }
    
    
}