package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Misc.ArcherObjects;
import com.pochitoGames.Misc.ArcherState;
import com.pochitoGames.Misc.TypeArcher;

public class Archer extends Component {

    private Entity target;
    private float speed = 100;
    private ArcherState state = ArcherState.WAITING;
    private int attackRange;
    private TypeArcher typeArcher;
    private ArcherObjects[] archerObjects = new ArcherObjects[2];
    private int level = 0;

    public ArcherState getState() {
        return state;
    }

    public void setState(ArcherState state) {
        this.state = state;
    }

    public Archer(int attackRange) {
        setAttackRange(attackRange);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArcherObjects[] getArcherObjects() {
        return archerObjects;
    }

    public void setArcherObjects(ArcherObjects[] archerObjects) {
        this.archerObjects = archerObjects;
    }


    public TypeArcher getTypeArcher() {
        return typeArcher;
    }

    public void setTypeArcher(TypeArcher typeArcher) {
        this.typeArcher = typeArcher;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void increaseLevel() {
        if (this.level > 10) {
            setLevel(++this.level);
        }
    }

    public boolean setObject(ArcherObjects archerObject) {
        if (archerObject != null) {
            for (int i = 0; i < this.archerObjects.length; i++) {
                if (this.archerObjects[i] != null) {
                    this.archerObjects[i] = archerObject;
                    return true;
                }
            }
            return false;
        } else return false;
    }

}
