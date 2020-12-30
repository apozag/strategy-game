package com.pochitoGames.Components.People;

import com.pochitoGames.Components.Other.Tree;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.States.LumberJackState;

public class LumberJack extends Component {

    private Vector2D target;
    private Tree tree;
    private LumberJackState lumberJackState = LumberJackState.WAITING;
    private ResourceType resourceType = null;

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

    public LumberJackState getLumberJackState() {
        return lumberJackState;
    }

    public void setLumberJackState(LumberJackState lumberJackState) {
        this.lumberJackState = lumberJackState;
    }
}
