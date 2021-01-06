package com.pochitoGames.Components.Other;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Other.Vector2i;

public class Tree extends Component {

    private double age = 0;
    private int life = 100;
    private Vector2i cell;
    private boolean taken = false;

    public Tree(Vector2i cell) {
        this.cell = cell;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Vector2i getCell() {
        return cell;
    }

    public void setCell(Vector2i cell) {
        this.cell = cell;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken() {
        this.taken = true;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
