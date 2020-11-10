/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.Visibility;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Worker;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Misc.States.WorkerState;

/**
 * @author PochitoMan
 */
public class WorkerSystem extends System {

    boolean start = false;

    public WorkerSystem() {
        include(Worker.class, Position.class, PathFinding.class, Visibility.class, Human.class, Builder.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        for (Entity e : getEntities()) {
            PathFinding pf = e.get(PathFinding.class);
            Worker c = e.get(Worker.class);
            Builder f = e.get(Builder.class);
            WorkerState state = c.getWorkerState();
            Sprite sprite = e.get(Sprite.class);
            Position p = e.get(Position.class);
            switch (state) {
                case WAIT:
                    //Estamos parados hasta que nos requieran (Los edificios nos llamen)
                    break;
                case SEARCH_RESOURCE:
                    //Si hemos llegado hasta el edificio
                    if (pf.getTargetCell() == null) {
                        //Cojo el edificio (El componente Warehouse solo)
                        Warehouse wh = c.getTargetBuilding().getEntity().get(Warehouse.class);
                        //Le quito una unidad del recurso
                        wh.takeContent(c.getNeeded(), 1);
                        //Cojo al compañero al que le tengo que llevar el recurso
                        Worker mate = c.getTargetMate();
                        //Cojo su pathfinding para saber su casilla
                        PathFinding mpf = mate.getEntity().get(PathFinding.class);
                        //Le digo a MI pathfinding que vaya a la casilla del compa
                        pf.setTargetCell(mpf.getCloseCell());
                        //Me congo en estado CARRY_RESOURCE
                        c.setWorkerState(WorkerState.CARRY_RESOURCE);
                        //Ahora empezará a andar solito hacia el compañero
                    }
                    break;
                case CARRY_RESOURCE:
                    //Si he llegado al compañero
                    if (pf.getTargetCell() == null) {
                        //Cojo al compa
                        Worker mate = c.getTargetMate();
                        //Cojo el edificio que está constrruyendo mi compa (getTargetBuilding)
                        //Y le meto (putResources) una unidad del recurso que necesita (getResourcesNeeded)
                        mate.getTargetBuilding().putResources(c.getNeeded(), 1);
                        //Pongo al compa de vuelta al estado BUILDING (estaba en ON_HOLD)
                        // mate.setWorkerState(BuilderState.BUILD); //TODO Aqui tengo dudas de comer hacerlo, porque tengo que cambiar el estado del otro builder a BUILD
                        //Me pongo en WAIT
                        c.setWorkerState(WorkerState.WAIT);
                    }
                    break;
            }
        }
    }
}
