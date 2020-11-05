package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.ObjectTypes.WorkerObject;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.BuildingState;
import java.util.Map;

public class Building extends Component {

    private TypeBuilding type;
    final private Vector2i cell;
    private int life;
    private int defense;
    private int attack;
    private boolean alive = true;
    final private int id;
    private static int contador = 0;

    private BuildingState state = BuildingState.PLANNED;

    private Map<ResourceType, Integer> resourcesNeeded;

    public boolean isAlive() {
        return alive;
    }

    private void setTypeBuilding(TypeBuilding typeBuilding) {
        //if (typeBuilding == null) System.out.print("Error no hay edificio asignado");
        this.type = typeBuilding;
    }

    public Building(int life, int defense, int attack, Vector2i cell, TypeBuilding typeBuilding, Map<ResourceType, Integer> resourcesNeeded) {
        setLife(life);
        setDefense(defense);
        setAttack(attack);
        setTypeBuilding(typeBuilding);
        this.resourcesNeeded = resourcesNeeded;
        this.cell = cell;
        id = contador++;
        updateResourcesNeeded();

    }

    public Vector2i getCell() {
        return cell;
    }

    public Vector2i getEntryCell() {
        return Vector2i.add(cell, BuildingManager.blueprints.get(type).entry);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        if (life < 0) {
            this.life = 0;
            alive = false;
        } else {
            this.life = life;
        }

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
        return type;
    }

    public boolean isFinished() {
        return resourcesNeeded.isEmpty();
    }

    public BuildingState getState() {
        return state;
    }

    public void setState(BuildingState state) {
        this.state = state;
    }

    //Devuleve un recurso que neceste (el que mas necesite)
    public ResourceType getResourceNeeded() {
        ResourceType higher = null;
        int maxAmount = 0;
        for (ResourceType t : resourcesNeeded.keySet()) {
            if (resourcesNeeded.get(t) > maxAmount) {
                maxAmount = resourcesNeeded.get(t);
                higher = t;
            }
        }
        return higher;
    }

    public void putResources(ResourceType r, int amount) {
        resourcesNeeded.put(r, resourcesNeeded.get(r) - amount);
        if (resourcesNeeded.get(r) <= 0) {
            resourcesNeeded.remove(r);
        }
        updateResourcesNeeded();
    }

    private void updateResourcesNeeded() {
        resourcesNeeded.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }
}
