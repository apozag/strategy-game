/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

/**
 *
 * @author PochitoMan
 */
public class MouseEventData {
    private MouseEventType type;
    private Vector2D position;
    
    public MouseEventData(MouseEventType type, Vector2D pos){
        this.type = type;
        this.position = pos;
    }

    /**
     * @return the type
     */
    public MouseEventType getType() {
        return type;
    }

    /**
     * @return the position
     */
    public Vector2D getPosition() {
        return position;
    }
    
}
