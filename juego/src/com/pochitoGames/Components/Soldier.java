package com.pochitoGames.Components;

import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.SoldierObjects;

public class Soldier {

    private Vector2D target;
    private SoldierObjects[] soldierObjects = new SoldierObjects[2];
    private Human human;

    public Soldier (Human human){
        setHuman(human);
    }

    public Vector2D getTarget() {
        return target;
    }

    public void setTarget(Vector2D target) {
        this.target = target;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public SoldierObjects[] getSoldierObjects() {
        return soldierObjects;
    }

    public boolean addSoldierObjects(SoldierObjects objects) {
        if (objects != null) {
            for (int i = 0; i < soldierObjects.length; i++) {
                if (soldierObjects[i] != null) {
                    soldierObjects[i] = objects;
                    return true;
                }
            }
            return false;
        } else return false;
    }

}
