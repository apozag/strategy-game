/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;

/**
 *
 * @author PochitoMan
 */
public class MouseListener extends Component {
    
    public int layer = 0;
    
    public boolean downLeft = false;
    public boolean firstTickLeft = false;
    public boolean releasedLeft = false;   
    
    public boolean downRight = false;
    public boolean firstTickRight = false;
    public boolean releasedRight = false; 
    
    public boolean mouseOver = false;
    
    public MouseListener(int layer){
        this.layer = layer;
    }
    
}
