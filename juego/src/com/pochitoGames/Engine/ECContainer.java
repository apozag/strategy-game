/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */

//Por esta clase no te ralles que casi no se usa
public class ECContainer {
    public Entity entity;
    public Component[] components;
    
    public ECContainer(Entity e, Component... c){
        entity = e;
        components = c;
    }
    
}