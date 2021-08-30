/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Other;

import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Other.ResourceType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class Backpack extends Component{
    ResourceType resource = null;
    private Sprite resourceSprite;

    public Backpack(){}
    
    public void setCarrying(ResourceType type){
        resource = type;
        
        if(type == null){
            this.resourceSprite.setCurrentAnimationIndex(0);
            return;
        }
        
        switch(type){
            case RAW_WOOD:
                this.resourceSprite.setCurrentAnimationIndex(1);
                break;
            case RAW_STONE:
                this.resourceSprite.setCurrentAnimationIndex(2);
                break;
            case RAW_GOLD:
                this.resourceSprite.setCurrentAnimationIndex(3);
                break;
            case WOOD:
                this.resourceSprite.setCurrentAnimationIndex(4);
                break;
            case STONE:
                this.resourceSprite.setCurrentAnimationIndex(5);
                break;
            case GOLD:
                this.resourceSprite.setCurrentAnimationIndex(6);
                break;
            case MEAT:
                this.resourceSprite.setCurrentAnimationIndex(7);
                break;
        }
    }
    
    public ResourceType getCarrying(){
        return resource;
    }
    
    public Sprite getResourceSprite() {
        return resourceSprite;
    }
    
    public void setResourceSprite(Sprite resourceSprite) {
        this.resourceSprite = resourceSprite;
    }
}
