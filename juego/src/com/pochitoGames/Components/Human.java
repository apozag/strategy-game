package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.SoldierObjects;
import com.pochitoGames.Misc.TypeSoldier;
import com.pochitoGames.Misc.WorkerObject;
import com.pochitoGames.Misc.SoldierState;

public class Human extends Component {

    private int life;
    private int hungry = 0;
    private boolean alive = true;
    private String name;
    private int attack;
    private int defense;
    private float velocity = 5;

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public Human(int life, String name, int attack, int defense) {
        setHungry(0);
        setLife(life);
        setName(name);
        setAttack(attack);
        setDefense(defense);
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
            this.life = 0;
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


    public boolean getFood(WorkerObject object) {
        if (object != null) {
            setHungry(0);
            return true;
        }
        return false;

    }

}
