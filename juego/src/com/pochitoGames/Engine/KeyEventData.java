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
public class KeyEventData {
    char c;
    KeyEventType type;
    
    public KeyEventData(char c, KeyEventType type){
        this.c = c;
        this.type = type;
    }
    
    public char getChar(){
        return c;
    }
    
    public KeyEventType getType(){
        return type;
    }
}
