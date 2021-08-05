/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PochitoMan
 */
public class EventManager {
    private static EventManager instance;
    
    private Map<String, List<Component>> listeners;
    
    private EventManager(){ 
        listeners = new HashMap<>();
    }
    
    public static EventManager getInstance(){
        if(instance==null)
            instance = new EventManager();
        return instance;
    }
    
    public void listenEvent(String tag, Component... listenerList){
        if(!listeners.containsKey(tag))
            listeners.put(tag, new ArrayList<>());
        for(Component listener : listenerList)
            listeners.get(tag).add(listener);
    }
    
    public void disregardEvent(String tag, Component... listenerList){
        for(Component listener : listenerList)
            listeners.get(tag).remove(listener);
        if(listeners.get(tag).isEmpty())
            listeners.remove(tag);
    }
    
    public void triggerEvent(String tag){
        if(!listeners.containsKey(tag))
            return;
        for(Component listener : listeners.get(tag)){
            listener.triggerEvent(tag);
        }
    }
    
}
