package com.pochitoGames.Components.GameLogic;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Entity;

import java.util.HashSet;
import java.util.List;

public class Visibility extends Component {

    private int visibilityRadius;

    public Visibility (int visibilityRadius){
        setVisibilityRadius(visibilityRadius);
    }

    private HashSet<Entity> visibility = new HashSet();

    public void addEntity (Entity e){
        if(!visibility.contains(e)){
            visibility.add(e);
        }
    }

    public boolean removeEntity (Entity e){
        if (visibility.contains(e)){
            visibility.remove(e);
            return true;
        } else return false;
    }

    public int getVisibilityRadius() {
        return visibilityRadius;
    }

    public void setVisibilityRadius(int visibilityRadius) {
        this.visibilityRadius = visibilityRadius;
    }

    public HashSet<Entity> getVisibility() {
        return visibility;
    }

    public void setVisibility(HashSet<Entity> visibility) {
        this.visibility = visibility;
    }
}
