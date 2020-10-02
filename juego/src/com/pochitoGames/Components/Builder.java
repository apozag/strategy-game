/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ConstructorState;

/**
 *
 * @author PochitoMan
 */
public class Builder extends Component{

    Vector2D target;
    float speed = 100;
    ConstructorState state = ConstructorState.WAITING;
    
    public Builder(){
    }
    
    public ConstructorState getState(){
        return state;
    }
    
    public Vector2D getTarget(){
        return target;
    }
    
    public void setState(ConstructorState state){
        this.state = state;
    }
    
    public void setTarget(Vector2D target){
        this.target = target;
    }
    
    public float getSpeed(){
        return speed;
    }
    
    
}
