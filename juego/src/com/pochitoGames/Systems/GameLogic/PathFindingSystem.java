/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.GameLogic;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Engine.Vector2i;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author PochitoMan
 */
public class PathFindingSystem extends System {

    public PathFindingSystem() {
        include(PathFinding.class, Position.class);
        exclude();
    }

    int count = 0;

    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            PathFinding pf = e.get(PathFinding.class);
            if(!pf.started())
                continue;
            //Si no está andando
            if (!pf.isWalking()) {

                //Vemos si tiene paso siguiente
                Vector2i next = pf.peekNextStep();

                //Si tiene lo ponemos como nuevo target y ponemos walking a true
                if (next != null) {

                    //Si nos topamos con otra persona, evadimos (?)  
                    /*
                    int nextCellId = MapInfo.getInstance().getPeopleLayerCell(next);
                    if(nextCellId != -1 && nextCellId != e.getId()){
                        for(Entity other : getEntities()){
                            if(other != e && other.getId() == MapInfo.getInstance().getPeopleLayerCell(next)){
                                PathFinding pfo = other.get(PathFinding.class);
                                if(pfo.getTargetCell() == null){
                                    pfo.setTargetCell(MapInfo.getInstance().getCloseCell(pfo.getCurrent()));
                                    break;
                                }
                            }
                        }
                        List<Vector2i> newPath  = aStar(pf.getCurrent(), pf.getTargetCell(), e.id, true);
                        if(newPath == null)
                            continue;
                        pf.setSteps(newPath);
                        next = pf.peekNextStep();
                    }
                     */
                    //Actualizamos posición en el mapa
                    MapInfo.getInstance().updatePeopleLayerCell(pf.getCurrent(), next, e.getId());

                    pf.setNextPos(Vector2D.add(IsometricTransformations.isoToCartesian(next),
                            new Vector2D(MapInfo.getInstance().getActiveTileMap().getTileW() * 0.5f, MapInfo.getInstance().getActiveTileMap().getTileH() * 0.5f)));
                    pf.setWalking(true);
                } //Si no tiene paso siguiente, hemos llegado
                //Si tiene un nuevo target, calculamos el camino
                else if (pf.getTargetCell() != null) {
                    java.lang.System.out.println("pf call: " + count++);
                    pf.setSteps(aStar(pf.getCurrent(), pf.getTargetCell(), e.id, false));
                }
            } //Si ya está andando
            else {
                Position p = e.get(Position.class);
                //Si ya ha llegado al target, ponemos walking a false
                if (aproximateEquals(p.getWorldPos(), pf.getNextPos(), 5)) {
                    pf.setCurrent(pf.pollNextStep());
                    //Si este es el cell final, quitamos el target
                    if (pf.getCurrent().equals(pf.getTargetCell())) {
                        pf.setTargetCell(null);
                        pf.reset();
                    }
                    pf.setWalking(false);
                } //Si no, seguimos
                else {
                    Vector2D dir = Vector2D.mult(Vector2D.sub(pf.getNextPos(), p.getWorldPos()).normalized(), (float) (pf.getSpeed() * dt));
                    p.setLocalPos(Vector2D.add(p.getLocalPos(), dir));
                }
            }
        }
    }

    public boolean aproximateEquals(Vector2D v1, Vector2D v2, float tolerance) {
        return Math.abs(v1.x - v2.x) <= tolerance && Math.abs(v1.y - v2.y) < tolerance;
    }

    private void patchPath(List<Vector2i> path, int begin, int end, int id) {
        List<Vector2i> patch = aStar(path.get(begin), path.get(end), id, true);
        path.subList(begin, end).clear();
        path.addAll(begin, patch);
    }

    public static List<Vector2i> aStar(Vector2i start, Vector2i end, int id, boolean avoidPeople) {
                        
        int[][] map = MapInfo.getInstance().getMap();
        List<Vector2i> steps = new LinkedList<>();

        if (start != null && end != null && MapInfo.getInstance().getTileWalkCost(end) >= 0 && end.col >= 0 && end.col < map.length && end.row >= 0 && end.row < map[0].length) {
            
            int totalDistance = end.distance(start);
            int minOptimalDistance = 40;
            Comparator comparator = totalDistance < minOptimalDistance? new SortByCostOptimal() : new SortByCost();
            
            //En open estan los candidatos a visitar
            PriorityQueue<Node> open = new PriorityQueue<>(100, comparator);

            //En closed están los que ya hemos visitado
            List<Node> closed = new LinkedList<>();

            Node first = new Node(start, 0, 0, 0, null, 0);

            open.add(first);

            while (!open.isEmpty()) {

                Node current = open.poll();

                closed.add(current);

                if (current.cell.equals((end))) {
                    //Vamos sacando los padres de current y los metemos en la cola que devolvemos
                    extractParents(current, steps);
                    steps.remove(0);
                    return steps;
                }

                //Sacar vecinos
                Vector2i p = current.cell;
                Vector2i[] neighbors = {new Vector2i(p.col, p.row + 1),
                    new Vector2i(p.col, p.row - 1),
                    new Vector2i(p.col + 1, p.row),
                    new Vector2i(p.col - 1, p.row),
                    new Vector2i(p.col + 1, p.row + 1),
                    new Vector2i(p.col - 1, p.row - 1),
                    new Vector2i(p.col + 1, p.row - 1),
                    new Vector2i(p.col - 1, p.row + 1)};

                int mapW = MapInfo.getInstance().getActiveTileMap().getMap().length;
                int mapH = MapInfo.getInstance().getActiveTileMap().getMap()[0].length;

                for (Vector2i n : neighbors) {
                    if (n.col < 0 || n.col >= mapW || n.row < 0 || n.row >= mapH
                            || (avoidPeople && current.stepNum < 3 && MapInfo.getInstance().getPeopleLayerCell(n) != -1 && MapInfo.getInstance().getPeopleLayerCell(n) != id)) {
                        continue;
                    }

                    int cellId = MapInfo.getInstance().getTileId(n);

                    float walkCost = MapInfo.getInstance().getTileWalkCost(n);

                    Node neighbor = new Node(n, 0, 0, 0, current, current.stepNum + 1);

                    if (walkCost < 0 || cellId < 0 || containsNode(closed, neighbor)) {
                        continue;
                    }

                    neighbor.g = walkCost + current.g;
                    neighbor.h = end.distance(n);
                    neighbor.f = neighbor.g + neighbor.h;
                    
                    if(neighbor.f <= current.f){
                        neighbor.f = 0;
                    }

                    if (addToOpen(open, neighbor)) {
                        open.add(neighbor);
                    }
                }
            }
        }

        //No se ha encontrado camino. Se devuelve null
        java.lang.System.out.println("No path");

        return null;
    }

    public static List<Vector2i> aStarFloor(Vector2i start, Vector2i end, int id, boolean avoidPeople) {
        int[][] map = MapInfo.getInstance().getMap();
        List<Vector2i> steps = new LinkedList<>();

        if (start != null && end != null && MapInfo.getInstance().getTileWalkCost(end) >= 0 && end.col >= 0 && end.col < map.length && end.row >= 0 && end.row < map[0].length) {

            //En open estan los candidatos a visitar
            PriorityQueue<Node> open = new PriorityQueue<>(100, new SortByCost());

            //En closed están los que ya hemos visitado
            List<Node> closed = new LinkedList<>();

            Node first = new Node(start, 0, 0, 0, null, 0);

            open.add(first);

            SortByCost sorter = new SortByCost();
            while (!open.isEmpty()) {
                // TODO: No hace falta ordenar, se puede buscar solo el mas cerca y ya
                Node current = open.poll();

                closed.add(current);

                if (current.cell.equals((end))) {
                    //Vamos sacando los padres de current y los metemos en la cola que devolvemos
                    extractParents(current, steps);
                    steps.remove(0);
                    return steps;
                }

                //Sacar vecinos
                Vector2i p = current.cell;
                Vector2i[] neighbors = {new Vector2i(p.col, p.row + 1),
                    new Vector2i(p.col, p.row - 1),
                    new Vector2i(p.col + 1, p.row),
                    new Vector2i(p.col - 1, p.row),
                    new Vector2i(p.col + 1, p.row + 1),
                    new Vector2i(p.col - 1, p.row - 1),
                    new Vector2i(p.col + 1, p.row - 1),
                    new Vector2i(p.col - 1, p.row + 1)};

                int mapW = MapInfo.getInstance().getActiveTileMap().getMap().length;
                int mapH = MapInfo.getInstance().getActiveTileMap().getMap()[0].length;

                for (Vector2i n : neighbors) {
                    if (n.col < 0 || n.col >= mapW || n.row < 0 || n.row >= mapH
                            || (avoidPeople && current.stepNum < 3 && MapInfo.getInstance().getPeopleLayerCell(n) != -1 && MapInfo.getInstance().getPeopleLayerCell(n) != id)) {
                        continue;
                    }

                    int cellId = MapInfo.getInstance().getTileId(n);

                    float walkCost = MapInfo.getInstance().getTileWalkCost(n);

                    Node neighbor = new Node(n, 0, 0, 0, current, current.stepNum + 1);

                    if ((cellId != 5 || containsNode(closed, neighbor)) && (!n.equals(end) || cellId != 6)) {
                        continue;
                    }

                    neighbor.g = walkCost + current.g;
                    neighbor.h = end.distance(n);
                    neighbor.f = neighbor.g + neighbor.h;

                    if (addToOpen(open, neighbor)) {
                        open.add(neighbor);
                    }
                }
            }
        }

        //No se ha encontrado camino. Se devuelve lista vacía
        java.lang.System.out.println("No path");

        return null;
    }

    private static boolean addToOpen(PriorityQueue<Node> list, Node node) {
        for (Node n : list) {
            if (node.cell.equals(n.cell) && node.f >= n.f) {
                return false;
            }
        }
        return true;
    }
    

    private static boolean containsNode(List<Node> list, Node node) {
        for (Node n : list) {
            if (node.cell.equals(n.cell)) {
                return true;
            }
        }
        return false;
    }

    private static void extractParents(Node n, List<Vector2i> list) {
        if (n != null) {
            list.add(0, n.cell);
            extractParents(n.parent, list);
        }
    }

}

class Node {

    public Vector2i cell;
    public float g, h, f;
    public Node parent;
    public int stepNum;

    public Node(Vector2i cell, float g, float h, float f, Node parent, int stepNum) {
        this.cell = cell;
        this.g = g;
        this.h = h;
        this.f = f;
        this.parent = parent;
        this.stepNum = stepNum;
    }
}

class SortByCostOptimal implements Comparator<Node> {

    @Override
    public int compare(Node a, Node b) {
        return (int) (a.f - b.f);
    }
}

class SortByCost implements Comparator<Node> {

    @Override
    public int compare(Node a, Node b) {
        return (int) (a.h - b.h);
    }
}
