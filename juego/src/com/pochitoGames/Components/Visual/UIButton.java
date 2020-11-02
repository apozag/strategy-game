/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Visual;

import com.pochitoGames.Engine.ClickListener;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.MouseEventData;

/**
 *
 * @author PochitoMan
 */
public class UIButton extends Component implements ClickListener{

    public boolean down = false;
    public boolean firstTick = false;
    
    @Override
    public void onMouseDown(MouseEventData e) {
    }

    @Override
    public void onMouseUp(MouseEventData e) {
    }
    
}
