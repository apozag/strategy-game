package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.ObjectTypes.WorkerObject;

public class Building extends Component {

    private TypeBuilding typeBuilding;
    private Vector2D vector2D;
    private int life;
    private int defense;
    private int attack;
    private boolean alive = true;
    private WorkerObject[] objectsNeeded = new WorkerObject[6];
    private WorkerObject[] objectsProduced = new WorkerObject[6];

    public boolean isAlive() {
        return alive;
    }

    private void setTypeBuilding(TypeBuilding typeBuilding) {
        if (typeBuilding == null) System.out.print("Error no hay edificio asignado");
        else this.typeBuilding = typeBuilding;
    }

    public WorkerObject[] getObjectsNeeded() {
        return objectsNeeded;
    }

    public WorkerObject[] getObjectsProduced() {
        return objectsProduced;
    }

    public Building(int life, int defense, int attack, TypeBuilding typeBuilding){
        setLife(life);
        setDefense(defense);
        setAttack(attack);
        setTypeBuilding(typeBuilding);

    }

    public Vector2D getVector2D() {
        return vector2D;
    }

    public void setVector2D(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        if (life < 0){
            this.life = 0;
            alive = false;
        } else  this.life = life;

    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int reciveDamage(int damage) {
        if (damage - defense > 0) {
            setLife(life - (damage - defense));
        }
        return this.life;
    }

    public int attack() {
        return attack;
    }

    public TypeBuilding getTypeBuilding() {
        return typeBuilding;
    }


    //Hay que hacer un timing para que produzca cada x tiempo
    public boolean objectProduced () {
        for(int i = 0; i<objectsProduced.length;i++){
            if (objectsProduced[i] != null){
                objectsProduced[i] = WorkerObject.BREAD;
                return true;
            } else return false;
        } return false;
    }


}
