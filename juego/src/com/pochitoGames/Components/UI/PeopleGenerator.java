/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;

/**
 *
 * @author PochitoMan
 */
public class PeopleGenerator extends Component{
    private TypeHuman type;
    
    public PeopleGenerator(TypeHuman type){
        this.type = type;
    }
    
    public TypeHuman getId(){
        return type;
    }
}
