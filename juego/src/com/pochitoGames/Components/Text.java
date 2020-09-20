/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components;

import com.pochitoGames.Engine.Component;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author PochitoMan
 */
public class Text extends Component {
    String text;
    Font font;
    
    public Text(String text){
        this.text = text;
        this.font = new Font("Times New Roman", Font.PLAIN, 28);
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public Font getFont(){
        return font;
    }
}
