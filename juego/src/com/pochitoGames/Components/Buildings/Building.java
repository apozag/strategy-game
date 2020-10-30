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
    private int id;
    private static int contador = 0;


    public boolean isAlive() {
        return alive;
    }

    private void setTypeBuilding(TypeBuilding typeBuilding) {
        if (typeBuilding == null) System.out.print("Error no hay edificio asignado");
        else this.typeBuilding = typeBuilding;
    }


    public Building(int life, int defense, int attack, TypeBuilding typeBuilding) {
        setLife(life);
        setDefense(defense);
        setAttack(attack);
        setTypeBuilding(typeBuilding);
        id = contador++;

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
        if (life < 0) {
            this.life = 0;
            alive = false;
        } else this.life = life;

    }

    public int getId() {
        return id;
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


}
