/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;;

import org.w3c.dom.Node;
import org.w3c.dom.Node;
import org.w3c.dom.Node;

/**
 *
 * @author PochitoMan
 */

//Con los componentes se define el comportamiento de las entidades
//Es una clase abstracta y todos los comopnentes deben heredar de ella.
public abstract class Component {
    
    //Tienen una referencia a la entidad a a que pertenecen
    Entity e;        
    
    public void Component(Node xmlNode){}
    
    public void setEntity(Entity e){
        this.e = e;
    }
    
    public Entity getEntity(){
        return e;
    }
    
    public void triggerEvent(String tag){}
    
}
