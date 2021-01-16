/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.Visual.SeeThrough;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.Vector2i;

import java.util.LinkedList;
import java.util.List;

/**
 * @author PochitoMan
 */
public class TreeManager {
    private static TreeManager instance;

    List<Tree> trees = new LinkedList<>();



    private TreeManager() {

    }

    public static TreeManager getInstance() {
        if (instance == null) {
            instance = new TreeManager();
        }
        return instance;
    }

    public void createTree(Vector2i cell) {
        Tree tree = new Tree(cell);
        ECS.getInstance().createEntity(null,
                new Position(IsometricTransformations.isoToCartesian(cell)),
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\tree_lifetime.png", new Vector2D(0.25f, 0.75f + 0.125f), true, 1.0f, 
                    new Animation(1, 100, 128, 256, 0, 0), 
                    new Animation(1, 100, 128, 256, 128, 0),
                    new Animation(1, 100, 128, 256, 256, 0)
                    ),
                new SeeThrough(),
                new MouseListener(0),
                tree
        );

        MapInfo.getInstance().setTileId(cell.col, cell.row, 200);

        java.lang.System.out.println(cell.col + ", " + cell.row);

        trees.add(tree);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
        ECS.getInstance().removeEntity(tree.getEntity());
        MapInfo.getInstance().setTileId(tree.getCell().col, tree.getCell().row, 4);

    }

    public Tree getNearestTree(Vector2i cell) {
        Tree nearest = null;
        int nearestDist = Integer.MAX_VALUE;
        for (Tree tree : trees) {
            if (tree.getAge() >= Tree.ADULT_AGE && !tree.isTaken()) {
                int dist = tree.getCell().distance(cell);
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearest = tree;
                }
            }
        }
        return nearest;
    }

    public Vector2i getPlantableCell(Vector2i cell) {
        int radius = 5;
        int iter = 0;
        int maxIter = 20;
        Vector2i candidate = new Vector2i(cell.col + (int) (Math.random() * radius), cell.row + (int) (Math.random() * radius));
        while (MapInfo.getInstance().getTileId(candidate) != 4) {
            candidate = new Vector2i(cell.col + (int) (Math.random() * 2*radius) - radius, cell.row + (int) (Math.random() * 2*radius) - radius);
            //if(candidate.col < 0 || candidate.col > MapInfo.getInstance().getWidth())
            iter++;
            if (iter >= maxIter) {
                iter = 0;
                radius += 5;                
            }
        }
        return candidate;
    }

    public List<Tree> getTrees() {
        return trees;
    }

}