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
    public MouseEventType type;
    public Vector2D position;
    public boolean press;
    
    public MouseEventData(MouseEventType type, Vector2D pos, boolean press){
        this.type = type;
        this.position = pos;
        this.press = press;
    }
    
}
