/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Miner;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.MinerState;
import com.pochitoGames.Misc.States.WorkerState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author PochitoMan
 */
public class PeopleManager {

    private static PeopleManager instance;

    private Map<TypeHuman, List<Entity>> people;

    private PeopleManager() {
        people = new HashMap<>();
        people.put(TypeHuman.BARBARIAN, new ArrayList<Entity>());
        people.put(TypeHuman.DEMON, new ArrayList<Entity>());
        people.put(TypeHuman.DWARF, new ArrayList<Entity>());
        people.put(TypeHuman.ELF, new ArrayList<Entity>());
        people.put(TypeHuman.GOBLIN, new ArrayList<Entity>());
        people.put(TypeHuman.ORC, new ArrayList<Entity>());
        people.put(TypeHuman.HUMAN, new ArrayList<Entity>());
        
    }

    public static PeopleManager getInstance() {
        if (instance == null)
            instance = new PeopleManager();
        return instance;
    }

    public void createCharacter(TypeHuman type, TypeRole role, Vector2i cell) {
        Entity e = null;
        switch (type) {
            case BARBARIAN:
                e = ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Human(100, "Sol", 10, 10, type),
                        new PathFinding(cell)
                );
                switch (role) {
                    case WORKER:
                        ECS.getInstance().addComponent(e, 
                            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character2.png", new Vector2D(0.5f, 1.0f), true, 1.0f),
                            new Worker());
                    break;
                    case BUILDER:
                        ECS.getInstance().addComponent(e, 
                            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png", new Vector2D(0.5f, 1.0f), true, 1.0f),
                            new Builder());
                    break;
                    case MINER:
                        ECS.getInstance().addComponent(e, 
                            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png", new Vector2D(0.5f, 1.0f), true, 1.0f), 
                            new Miner());
                    break;
                }
                break;
        }
        people.get(type).add(e);
        MapInfo.getInstance().setPeopleLayerCell(cell, e.getId());
    }

    public Builder getNearestBuilder(TypeHuman type, Vector2i cell) {
        Builder nearest = null;
        int nearestDist = 9999;
        for (Entity e : people.get(type)) {
            Builder b = e.get(Builder.class);
            if (b != null && b.getState() == BuilderState.WAIT) {
                Human h = e.get(Human.class);
                if (h == null)
                    continue;
                PathFinding pf = e.get(PathFinding.class);
                int dist = cell.distance(pf.getCurrent());
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearest = b;
                }
            }
        }
        return nearest;
    }

    public Worker getNearestWorker(TypeHuman type, Vector2i cell) {
        Worker nearest = null;
        int nearestDist = 9999;
        for (Entity e : people.get(type)) {
            Worker b = e.get(Worker.class);
            if (b != null && b.getState() == WorkerState.WAIT) {
                Human h = e.get(Human.class);
                if (h == null && h.getTypeHuman() != type)
                    continue;
                PathFinding pf = e.get(PathFinding.class);
                int dist = cell.distance(pf.getCurrent());
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearest = b;
                }
            }
        }
        return nearest;
    }

    public Miner getNearestMiner(TypeHuman type, Vector2i cell) {
        Miner nearest = null;
        int nearestDist = 9999;
        for (Entity e : people.get(type)) {
            Miner m = e.get(Miner.class);
            if (m != null && m.getState() == MinerState.WAIT) {
                Human h = e.get(Human.class);
                if (h == null && h.getTypeHuman() != type)
                    continue;
                PathFinding pf = e.get(PathFinding.class);
                int dist = cell.distance(pf.getCurrent());
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearest = m;
                }
            }
        }
        return nearest;
    }
    
    /*
    
    public Builder getNearestBuilder(TypeHuman type, Vector2i cell) {
        Entity e = dijkstraPeople(cell, type, TypeRole.BUILDER);
        if (e != null)
            return e.get(Builder.class);
        return null;
    }
    public Worker getNearestWorker(TypeHuman type, Vector2i cell) {
        Entity e = dijkstraPeople(cell, type, TypeRole.WORKER);
        if (e != null)
            return e.get(Worker.class);
        return null;
    }
     public Miner getNearestMiner(TypeHuman type, Vector2i cell) {
        Entity e = dijkstraPeople(cell, type, TypeRole.MINER);
        if (e != null)
            return e.get(Miner.class);
        return null;
    }
    private Entity dijkstraPeople(Vector2i start, TypeHuman type, TypeRole role) {        

        //En open estan los candidatos a visitar
        List<Node> open = new LinkedList<>();
       
        //En closed están los que ya hemos visitado
        List<Node> closed = new LinkedList<>();

        Node first = new Node(start, 0);

        open.add(first);

        SortByG sorter = new SortByG();
        while (!open.isEmpty()) {
            // TODO: No hace falta ordenar, se puede buscar solo el mas cerca y ya
            open.sort(sorter);
            Node current = open.remove(0);

            closed.add(current);
                        
            int id = MapInfo.getInstance().getPeopleLayerCell(current.cell);
            if (id != -1) {
                //Miramos que personaje es y lo devolvemos
                for(Entity e : people.get(type)){
                    if(e.getId() == id){
                        Component c = null;
                        switch(role){
                            case BUILDER:
                                Builder b = e.get(Builder.class);
                                if(b != null && b.getState() == BuilderState.WAIT)
                                    c = b;
                                break;
                            case WORKER:
                                Worker w = e.get(Worker.class);
                                if(w != null && w.getState() == WorkerState.WAIT)
                                    c = w;
                                break;
                            case MINER:
                                Miner m = e.get(Miner.class);
                                if(m != null && m.getState() == MinerState.WAIT)
                                    c = m;
                                break;                               
                        }
                        if(c != null)                           
                            return e;
                        break;
                    }
                }
            }

            //Sacar vecinos
            Vector2i p = current.cell;
            Vector2i[] neighbors = {new Vector2i(p.col, p.row+1),
                                    new Vector2i(p.col, p.row-1),
                                    new Vector2i(p.col + 1, p.row),
                                    new Vector2i(p.col - 1, p.row)};
                                    //new Vector2i(p.col + 1, p.row + 1),
                                    //new Vector2i(p.col-1, p.row-1),
                                    //new Vector2i(p.col + 1, p.row - 1),
                                    //new Vector2i(p.col - 1, p.row + 1)};

            int mapW = MapInfo.getInstance().getActiveTileMap().getMap().length;
            int mapH = MapInfo.getInstance().getActiveTileMap().getMap()[0].length;

            for (Vector2i n : neighbors) {
                if(n.col < 0 || n.col >= mapW || n.row < 0 || n.row >= mapH)
                    continue;

                int cellId = MapInfo.getInstance().getTileId(n);

                float walkCost = MapInfo.getInstance().getTileWalkCost(n);

                Node neighbor = new Node(n,0);

                if(walkCost < 0 || cellId < 0 || containsNode(closed, neighbor))
                    continue;

                neighbor.dist = walkCost + current.dist;

                if(addToOpen(open, neighbor)){
                    open.add(neighbor);
                }
            }
        }
        
        //No se ha encontrado camino. Se devuelve lista vacía
        java.lang.System.out.println("No path to Character");  
        return null;
    }
    
    private static boolean containsNode(List<Node> list, Node node){
        for(Node n : list){
            if(node.cell.equals(n.cell))
                return true;
        }
        return false;
    }
    private static boolean addToOpen(List<Node> list, Node node){
        for(Node n : list){
            if(node.cell.equals(n.cell) && node.dist >= n.dist){
                return false;
            }
        }
        return true;
    }
*/
}
    class Node{
        public float dist;
        public Vector2i cell;
        public Node(Vector2i cell, float dist){
            this.cell = cell;
            this.dist = dist;
        }
    }
    

    class SortByG implements Comparator<Node> {

        @Override
        public int compare(Node a, Node b) {
            return (int) (a.dist - b.dist);
        }
    }

