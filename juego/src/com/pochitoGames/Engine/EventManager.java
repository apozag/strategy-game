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

    private List<ClickListener> clickListeners;
    
    private boolean mouseDown = false;
    private boolean mouseFirstTick = false;

    private EventManager(){
        mouseEvents = new LinkedList<>();
        keyEvents = new HashSet<>();
        clickListeners = new LinkedList<>();
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
        return mouseDown;
    }
    
    public boolean mouseClicked(){
        return mouseDown && mouseFirstTick;
    }
    public Vector2D getMousePos(){
         //Point pos = MouseInfo.getPointerInfo().getLocation();
         Point pos = Renderer.getInstance().getMousePosition();
         if(pos == null)
             return new Vector2D(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
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
        mouseFirstTick = false;
    }
    
    public void handleEvents(){
        for(ClickListener cl : clickListeners){
            for(MouseEventData e : mouseEvents){
                if(e.press)
                    cl.onMouseDown(e);
                else
                    cl.onMouseUp(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        mouseFirstTick = true;
        addMouseEvent(new MouseEventData(MouseEventType.LEFT_CLICK, new Vector2D(e.getX(), e.getY()), true));        
    }

    @Override
    public void mouseReleased(MouseEvent e) {   
        mouseDown = false;
        addMouseEvent(new MouseEventData(MouseEventType.LEFT_CLICK, new Vector2D(e.getX(), e.getY()), false));
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
    
    public void addClickListener(ClickListener listener){
        clickListeners.add(listener);
    }
}
