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
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.MinerState;
import com.pochitoGames.Misc.States.WorkerState;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */
public class PeopleManager {
    
    private static PeopleManager instance;
    
    private List<Entity> people;
    
    private PeopleManager(){
        people = new LinkedList<>();
    }
    
    public static PeopleManager getInstance(){
        if(instance == null)
            instance = new PeopleManager();
        return instance;
    }
    
    public void createCharacter(TypeHuman type, TypeRole role, Vector2i cell){
        Entity e = null;
        switch(type){
            case BARBARIAN:
                e = ECS.getInstance().createEntity(null,                    
                    new Position(IsometricTransformations.isoToCartesian(cell)),
                    new Human(100,"Sol",10,10, type),
                    new PathFinding(cell)
                );
                switch(role){
                    case WORKER:
                        ECS.getInstance().addComponent(e, new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character2.png",
                                    new Vector2D(0.5f, 1.0f),
                                    true));
                        ECS.getInstance().addComponent(e, new Worker());
                        break;
                    case BUILDER:
                        ECS.getInstance().addComponent(e, new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png",
                                    new Vector2D(0.5f, 1.0f),
                                    true));
                        ECS.getInstance().addComponent(e, new Builder());
                        break;
                }
                break;
        }
        
        
        people.add(e);
    }
    /*
    public Component getNearest(TypeHuman type, TypeRole role, Vector2i cell){        
        
        switch(role){
            case WORKER:
                return getNearestWorker(type, cell);
            case BUILDER:
                return getNearestBuilder(type, cell);
            case MINER:
                return getNearestMiner(type, cell);
        }
        return null;
    }
    */
    public Builder getNearestBuilder(TypeHuman type, Vector2i cell){
        Builder nearest = null;
        int nearestDist = 9999;
        for(Entity e : people){
            Builder b = e.get(Builder.class);
            if(b != null && b.getState() == BuilderState.WAIT){
                Human h = e.get(Human.class);
                if(h == null && h.getTypeHuman() != type)
                    continue;
                PathFinding pf = e.get(PathFinding.class);
                int dist = cell.distance(pf.getCurrent());
                if(dist < nearestDist){
                    nearestDist = dist;
                    nearest = b;
                }
            }
        }
        return nearest;
    }
    
    public Worker getNearestWorker(TypeHuman type, Vector2i cell){
        Worker nearest = null;
        int nearestDist = 9999;
        for(Entity e : people){
            Worker b = e.get(Worker.class);
            if(b != null && b.getState() == WorkerState.WAIT){
                Human h = e.get(Human.class);
                if(h == null && h.getTypeHuman() != type)
                    continue;
                PathFinding pf = e.get(PathFinding.class);
                int dist = cell.distance(pf.getCurrent());
                if(dist < nearestDist){
                    nearestDist = dist;
                    nearest = b;
                }
            }
        }
        return nearest;
    }
    
    public Miner getNearestMiner(TypeHuman type, Vector2i cell){
        Miner nearest = null;
        int nearestDist = 9999;
        for(Entity e : people){
            Miner m = e.get(Miner.class);
            if(m != null && m.getState() == MinerState.WAIT){
                Human h = e.get(Human.class);
                if(h == null && h.getTypeHuman() != type)
                    continue;
                PathFinding pf = e.get(PathFinding.class);
                int dist = cell.distance(pf.getCurrent());
                if(dist < nearestDist){
                    nearestDist = dist;
                    nearest = m;
                }
            }
        }
        return nearest;
    }
}
