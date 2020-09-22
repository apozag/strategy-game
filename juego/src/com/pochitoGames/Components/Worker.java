/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;

/**
 * @author PochitoMan
 */
public class Worker extends Component {

    Vector2D target;
    private int life;
    private int hungry = 0;
    private boolean alive = true;
    private String name;
    private String[] tasks = new String[3];

    public Worker() {
        setLife(100);
        setHungry(0);
        setName("Worker");
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

    public void recibeDanno(int danno) {
        setLife(this.life - danno);
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

    public String getName() {
        return name;
    }

/*
    public String taskViewer(String[] tasks) {
        if (tasks[0] != null) {
            for (int i = 0; i < this.tasks.length; i++) {
                return tasks[i];
            }
        } else return null;
    } // hay q tocar algo aqui
     */


}
