/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems;
import com.pochitoGames.Components.PathFinding;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.MapInfo;
import com.pochitoGames.Misc.Vector2i;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 *
 * @author PochitoMan
 */
public class PathFindingSystem extends System{
    public PathFindingSystem(){
        include(PathFinding.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for(Entity e : getEntities()){
            PathFinding pf = e.get(PathFinding.class);
            //Si no está andando
            if(!pf.isWalking()){
                //Vemos si tiene paso siguiente
                Vector2i next = pf.peekNextStep();
                //Si tiene lo ponemos como nuevo target y ponemos walking a true
                if(next != null){
                    pf.setNextPos(TileMapSystem.indexToCartesian(next.col, next.row, MapInfo.getInstance().getActiveTileMap()));
                    pf.setWalking(true);
                }
                //Si no tiene paso siguiente, hemos llegado
                //Si tiene un nuevo target, calculamos el camino
                else if(pf.getTargetCell() != null){
                    pf.setSteps(aStar(pf.getCurrent(), pf.getTargetCell()));
                }
            }
            //Si ya está andando
            else{
                Position p = e.get(Position.class);
                //Si ya ha llegado al target, ponemos walking a false
                if(p.getWorldPos().equals(pf.getNextPos())){
                    pf.setCurrent(pf.pollNextStep());
                    pf.setWalking(false);
                }
                //Si no, seguimos
                else{
                    Vector2D dir = Vector2D.mult(Vector2D.sub(pf.getNextPos(), p.getWorldPos()).normalized(), 50.0f);
                    p.setLocalPos(Vector2D.add(p.getLocalPos(), dir));
                }
            }
        }
    }
    
    public static Queue<Vector2i> aStar(Vector2i start, Vector2i end){
        Queue<Vector2i> steps = new LinkedList<>();

        //algoritmo
        int[][] map = MapInfo.getInstance().getMap();

        List<Node> open = new LinkedList<Node>();
        List<Node> closed = new LinkedList<Node>();

        Node first = new Node(start, 0, 0, 0, null);

        SortByCost sorter = new SortByCost();
        while(!open.isEmpty()){
            open.sort(sorter);
            Node current = open.remove(0);

            closed.add(current);

            if(current.cell.equals((end))){
                //Vamos sacando los padres de current y los metemos en la cola que devolvemos

            }

            //Sacar vecinos
            Vector2i[] neighbous = {new Vector2i(0, 1), new Vector2i(0, -1), new Vector2i(1, 0), new Vector2i(0, -1), new Vector2i(1, 1), new Vector2i(-1, -1)};

            for(Vector2i n : neighbous){
                // Seguir mirando en https://www.annytab.com/a-star-search-algorithm-in-python/
            }
        }

        return steps;
    }

    private static Queue<Vector2i> searchNextCell(float g, Vector2i current, Queue<Vector2i> path){

    }
    
}

class Node{

    public Vector2i cell;
    public float g, h, f;
    public Node parent;

    public Node(Vector2i cell, float g, float h, float f, Node parent){
        this.cell = cell;
        this.g = g;
        this.h = h;
        this.f = f;
        this.parent = parent;
    }
}

class SortByCost implements Comparator<Node> {

    @Override
    public int compare(Node a, Node b) {
        return (int)(a.f - b.f);
    }
}
