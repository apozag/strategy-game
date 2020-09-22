/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author PochitoMan
 */
public class EventManager implements MouseListener, MouseWheelListener, KeyListener{
       private static List<MouseEventData> mouseEvents;
       private static Set<Character> keyEvents;
       private static EventManager instance;
       private int mouseWheelRotation;
       
       private EventManager(){
           mouseEvents = new LinkedList<>();
           keyEvents = new HashSet<>();
       }
       
       public static EventManager getInstance(){
           if(instance == null)
               instance = new EventManager();
           return instance;
       }
       
       public void addMouseEvent(MouseEventData e){
           mouseEvents.add(e);
       }
       
       public List<MouseEventData> getMouseEvents(){
           return Collections.unmodifiableList(mouseEvents);
       }
       
       public boolean isMousePressed(){
           for(MouseEventData e : mouseEvents){
               if(e.getType() == MouseEventType.LEFT_CLICK)
                   return true;
           }
           return false;
       }
       public Vector2D getMousePos(){
            Point pos = MouseInfo.getPointerInfo().getLocation();
            return new Vector2D(pos.x, pos.y);
       }
       
       public boolean isKeyDown(char c){
           return keyEvents.contains(c);
       }
       
       public int getMouseWheelRotation(){
           return mouseWheelRotation;
       }
       
       public void clearEvents(){
           mouseEvents.clear();
           mouseWheelRotation = 0;
       }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void mousePressed(MouseEvent e) {
        addMouseEvent(new MouseEventData(MouseEventType.LEFT_CLICK, new Vector2D(e.getX(), e.getY())));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyEvents.add(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyEvents.remove(e.getKeyChar());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelRotation += e.getWheelRotation();
    }
}
