package com.pochitoGames.Components.People;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ObjectTypes.WorkerObject;

public class Human extends Component {

    private int life;
    private int hungry = 50;
    private boolean alive = true;
    private String name;
    private int attack;
    private int defense;
    private float velocity = 5;
    private TypeHuman typeHuman;

    public TypeHuman getTypeHuman() {
        return typeHuman;
    }

    public void setTypeHuman(TypeHuman typeHuman) {
        this.typeHuman = typeHuman;
    }

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

    public Human(int life, String name, int attack, int defense, TypeHuman typeHuman) {
        setHungry(0);
        setLife(life);
        setName(name);
        setAttack(attack);
        setDefense(defense);
        setTypeHuman(typeHuman);
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
