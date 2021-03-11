/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Engine.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class UIManager {
    
    private Map<String, Entity> panels = new HashMap<>();
    private String activePanel = null;
    
    private static UIManager instance;
    
    private UIManager(){}
    
    public static UIManager getInstance(){
        if(instance == null){
            instance = new UIManager();
        }
        return instance;
    }
    
    public void addPanel(String tag, Entity e){
        e.deactivate();
        panels.put(tag, e);
    }
    
    public void activatePanel(String tag){
        if(activePanel != null){
            panels.get(activePanel).deactivate();
        }
        panels.get(tag).activate();
        activePanel = tag;
    }
    
    public void deactivateActivePanel(){
        if(activePanel != null){
            panels.get(activePanel).deactivate();
        }
    }
    
    public String getActivePanel(){
        return activePanel;
    }
}
