/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.People;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ObjectTypes.WorkerObject;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.WorkerState;

/**
 * @author PochitoMan
 */
public class Worker extends Component {

    Vector2D target;
    private String[] tasks = new String[3];
    private WorkerObject object;
    private WorkerState workerState = WorkerState.WAIT;
    Building targetBuilding;
    ResourceType carrying;
    private ResourceType needed;
    Builder targetMate = null;
        
    private Warehouse srcWarehouse = null;

    public Builder getTargetMate() {
        return targetMate;
    }

    public void setTargetMate(Builder targetMate) {
        this.targetMate = targetMate;
    }

    public ResourceType getCarrying() {
        return carrying;
    }

    public void setCarrying(ResourceType carrying) {
        this.carrying = carrying;
        
    }

    public ResourceType getResourceNeeded() {
        return needed;
    }

    public void setResourceNeeded(ResourceType needed) {
        this.needed = needed;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    public WorkerState getState() {
        return workerState;
    }

    public void setState(WorkerState workerState) {
        this.workerState = workerState;
    }

    public Worker() {
    }

    public WorkerObject getObject() {
        return object;
    }

    public boolean addObject(WorkerObject object) {
        if (object != null) {
            this.object = object;
            return true;
        } else return false;
    }

    public boolean deleteObject() {
        if (object != null) {
            this.object = null;
            return true;
        } else return false;
    }


    public String taskViewer(String[] tasks) {
        if (tasks[0] != null) {
            for (int i = 0; i < this.tasks.length; i++) {
                return tasks[i];
            }
            return "Empty";
        } else return "Empty";
    }

    public Warehouse getSrcWarehouse() {
        return srcWarehouse;
    }

    public void setSrcWarehouse(Warehouse srcWarehouse) {
        this.srcWarehouse = srcWarehouse;
    }
    
}
