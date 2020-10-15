package com.pochitoGames.Components.People;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ObjectTypes.SoldierObjects;
import com.pochitoGames.Misc.States.SoldierState;
import com.pochitoGames.Misc.ComponentTypes.TypeSoldier;

public class Soldier extends Component {

    private Vector2D target;
    private SoldierObjects[] soldierObjects = new SoldierObjects[2];
    private TypeSoldier typeSoldier;
    private SoldierState state;



    public Soldier(TypeSoldier typeSoldier) {
        setTypeSoldier(typeSoldier);
    }

    public Vector2D getTarget() {
        return target;
    }

    public void setTarget(Vector2D target) {
        this.target = target;
    }

    public SoldierObjects[] getSoldierObjects() {
        return soldierObjects;
    }

    public TypeSoldier getTypeSoldier() {
        return typeSoldier;
    }

    public void setTypeSoldier(TypeSoldier typeSoldier) {
        this.typeSoldier = typeSoldier;
    }

    public SoldierState getState() {
        return state;
    }

    public void setState(SoldierState state) {
        this.state = state;
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
