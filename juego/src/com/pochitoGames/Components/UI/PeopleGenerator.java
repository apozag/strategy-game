/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author PochitoMan
 */
public class PeopleGenerator extends Component{
    private TypeHuman type;
    private TypeRole role;
    
    public PeopleGenerator(Node node){
        Element e = (Element) node;
        String roleString = e.getElementsByTagName("role").item(0).getTextContent();
        this.role = TypeRole.valueOf(roleString);
        this.type = TypeHuman.BARBARIAN;
    }
    
    public PeopleGenerator(TypeHuman type, TypeRole role){
        this.type = type;
        this.role = role;
    }
    
    public TypeHuman getType(){
        return type;
    }
    public TypeRole getRole(){
        return role;
    }
}
