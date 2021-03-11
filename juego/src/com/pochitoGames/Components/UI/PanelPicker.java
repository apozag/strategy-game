/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;

/**
 *
 * @author PochitoMan
 */
public class PanelPicker extends Component{
    private String tag;
    
    public PanelPicker(String tag){
        this.tag = tag;
    }
    
    public String getTag(){
        return tag;
    }
}
