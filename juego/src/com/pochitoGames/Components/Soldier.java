package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.SoldierObjects;
import com.pochitoGames.Misc.TypeSoldier;
import com.pochitoGames.Misc.WorkerObject;
import com.pochitoGames.Misc.SoldierState;

public class Soldier  extends Component {

    private SoldierState state = SoldierState.WAITING;
    float speed = 5;
    private TypeSoldier typeSoldier;
    private Vector2D target;
    private int life;
    private int hungry = 0;
    private boolean alive = true;
    private String name;
    private String[] tasks = new String[3];
    private int attack;
    private int defense;
    private int dexterity; //Por pensar
    private SoldierObjects[] soldierObjects = new SoldierObjects[2];

    public TypeSoldier getTypeSoldier() {
        return typeSoldier;
    }

    public void setTypeSoldier(TypeSoldier typeSoldier) {
        this.typeSoldier = typeSoldier;
    }

    public Soldier(int life, String name, int attack, int defense, TypeSoldier typeSoldier) {
        setHungry(0);
        setLife(life);
        setName(name);
        setAttack(attack);
        setDefense(defense);
        setTypeSoldier(typeSoldier);
    }


    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setLife(int life) {
        if (life < 0) {
            alive = false;
        } else this.life = life;

    }

    public void setHungry(int hungry) {
        if (hungry <= 100) {
            this.hungry = 100;
        } else if (hungry < 0) {
            this.hungry = 0;
            alive = false;
        } else {
            this.hungry = ++hungry;
        }

    }

    public int reciveDamage(int damage) {
        if (damage - defense > 0) {
            setLife(life - (damage - defense));
        }
        return this.life;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public int getHungry() {
        return hungry;
    }

    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
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

    public SoldierState getState() {
        return state;
    }

    public void setState(SoldierState state) {
        this.state = state;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int attack() {
        return attack;
    }

    public int getDexterity() {
        return dexterity;
    }

    public boolean getFood(WorkerObject object) {
        if (object != null) {
            setHungry(0);
            return true;
        }
        return false;

    }

    public Vector2D getTarget(){
        return target;
    }
}
