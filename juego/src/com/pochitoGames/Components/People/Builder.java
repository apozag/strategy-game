/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.People;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.BuilderState;

/**
 *
 * @author PochitoMan
 */
public class Builder extends Component {

    Vector2D target;
    float speed = 100;
    BuilderState state = BuilderState.WAIT;
    Building targetBuilding;
    private Building resourceBuilding;

    ResourceType carrying;
    private ResourceType needed;

    Builder targetMate = null;

    public Builder() {
    }

    public BuilderState getState() {
        return state;
    }

    public Vector2D getTarget() {
        return target;
    }

    public void setState(BuilderState state) {
        this.state = state;
    }

    public void setTarget(Vector2D target) {
        this.target = target;
    }

    public void setTargetMate(Builder b) {
        targetMate = b;
    }

    public Builder getTargetMate() {
        return targetMate;
    }

    public float getSpeed() {
        return speed;
    }

    public void setTargetBuilding(Building b) {
        this.targetBuilding = b;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public Building getResourceBuilding() {
        return resourceBuilding;
    }

    public void setResourceBuilding(Building b) {
        this.resourceBuilding = b;
    }

    public void setResourceCarrying(ResourceType type) {
        carrying = type;
    }

    public ResourceType getResourceCarrying() {
        return carrying;
    }

    public ResourceType getResourceNeeded() {
        return needed;
    }

    public void setResourceNeeded(ResourceType needed) {
        this.needed = needed;
    }
}
