/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Visual;

import com.pochitoGames.Engine.Component;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author PochitoMan
 */
public class Text extends Component {
    String text;
    Font font;
    Color color;
    
    boolean centered = false;
    
    public Text(String text, Color color){
        this.text = text;
        this.font = new Font("Times New Roman", Font.PLAIN, 28);
        this.color = color;
    }
    public Text(String text, Color color, boolean centered){
        this.text = text;
        this.font = new Font("Times New Roman", Font.PLAIN, 28);
        this.color = color;
        this.centered = centered;
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
    
    public Color getColor(){
        return color;
    }
    
    public boolean isCentered(){
        return centered;
    }
}
