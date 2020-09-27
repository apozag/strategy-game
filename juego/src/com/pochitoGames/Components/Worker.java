/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.WorkerObject;

/**
 * @author PochitoMan
 */
public class Worker extends Component {

    Vector2D target;
    private String[] tasks = new String[3];
    private WorkerObject object;

    public Worker() {
    }

    public WorkerObject getObject() {
        return object;
    }

    public boolean addObject(WorkerObject object) {
        if (object != null) {
            this.object = object;
            return true;
        } else return false;
    }

    public boolean deleteObject() {
        if (object != null) {
            this.object = null;
            return true;
        } else return false;
    }


    public String taskViewer(String[] tasks) {
        if (tasks[0] != null) {
            for (int i = 0; i < this.tasks.length; i++) {
                return tasks[i];
            }
            return null;
        } else return null;
    }


}
