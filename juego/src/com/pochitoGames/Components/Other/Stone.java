/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Other;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;

/**
 *
 * @author PochitoMan
 */
public class Stone extends Component {
    public ResourceType type = ResourceType.RAW_STONE;
    public Vector2i cell;
    public boolean taken = false;
    public Stone(Vector2i cell){
        this.cell = cell;
    }
    public Stone(Vector2i cell, ResourceType type){
        this.cell = cell;
        this.type = type;
    }
}
