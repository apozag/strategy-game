package com.pochitoGames.Components.People;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ObjectTypes.WorkerObject;

public class Human extends Component {

    private float life;
    private float hunger;
    private float maxHunger = 50;
    private boolean alive = true;
    private String name;
    private int attack;
    private int defense;
    private float velocity = 5;
    private TypeHuman typeHuman;    
    private double lastHungerTime;

    
    public Human(int life, String name, int attack, int defense, TypeHuman typeHuman) {
        setHunger(maxHunger);
        setLife(life);
        setName(name);
        setAttack(attack);
        setDefense(defense);
        setTypeHuman(typeHuman);
        setLastHungerTime(java.lang.System.currentTimeMillis());
    }
    
    public TypeHuman getTypeHuman() {
        return typeHuman;
    }

    public void setTypeHuman(TypeHuman typeHuman) {
        this.typeHuman = typeHuman;
    }

    public boolean isAlive() {
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

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setLife(float life) {
        if (life < 0) {
            alive = false;
            this.life = 0;

        } else this.life = life;
    }

    public void setHunger(float hunger) {
        this.hunger = hunger;
    }
    
    public void subtractHunger(float amount){
        setHunger(this.hunger - amount);
    }

    public float reciveDamage(float damage) {
        setLife(life - (damage - defense));
        return this.life;
    }


    public void setName(String name) {
        this.name = name;
    }

    public float getLife() {
        return life;
    }

    public float getHunger() {
        return hunger;
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
    
    public void restoreHunger(){
        this.hunger = this.maxHunger;
    }

    public double getLastHungerTime() {
        return lastHungerTime;
    }

    public void setLastHungerTime(double lastAteTime) {
        this.lastHungerTime = lastAteTime;
    }
    
    public boolean isHungerFull(){
        return Math.abs(this.hunger-maxHunger) <= 5;
    }

}
