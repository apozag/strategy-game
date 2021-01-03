package com.pochitoGames.Components.People;

import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.LumberJackState;

public class LumberJack extends Component {

    private Vector2D target;
    private Tree tree;
    private Entity hut;
    private LumberJackState lumberJackState = LumberJackState.WAITING;
    private ResourceType resourceType = null;
    
    private double waitTime = 5000.0;// milliseconds
    private double lastTime = 999999999;// milliseconds

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Vector2D getTarget() {
        return target;
    }

    public void setTarget(Vector2D target) {
        this.target = target;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public LumberJackState getState() {
        return lumberJackState;
    }

    public void setState(LumberJackState lumberJackState) {
        this.lumberJackState = lumberJackState;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }

    public Entity getHut() {
        return hut;
    }

    public void setHut(Entity hut) {
        this.hut = hut;
    }
}
