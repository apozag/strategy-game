/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */
public class Window extends JFrame{
    public Window(int w, int h){
        this.setSize(w, h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(Renderer.getInstance());
        this.setVisible(true);
        Renderer.getInstance().addMouseListener(InputManager.getInstance());
        Renderer.getInstance().addKeyListener(InputManager.getInstance());
        Renderer.getInstance().addMouseWheelListener(InputManager.getInstance());
        Renderer.getInstance().setFocusable(true);
        Renderer.getInstance().setDoubleBuffered(false);
    }
}
