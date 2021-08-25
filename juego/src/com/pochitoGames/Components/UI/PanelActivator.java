/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author PochitoMan
 */
public class PanelActivator extends Component{
    private String tag;
    
    public PanelActivator(Node node){
        Element e = (Element) node;
        this.tag = e.getElementsByTagName("tag").item(0).getTextContent();
    }
    
    public PanelActivator(String tag){
        this.tag = tag;
    }
    
    public String getTag(){
        return tag;
    }
}
