/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Other;

import com.pochitoGames.Engine.Vector2D;

/**
 *
 * @author PochitoMan
 */

//Contiene info sobre una animación en concreto.
public class Animation {
    private int frames;
    private int speed;
    private int xOffset;
    private int yOffset;
    private Vector2D size;
    
    public Animation(int frames, int speed, int w, int h, int xOffset, int yOffset){
        this.frames = frames;
        this.speed = speed;
        this.size = new Vector2D(w, h);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * @return the frames
     */
    public int getFrames() {
        return frames;
    }

    /**
     * @param frames the frames to set
     */
    public void setFrames(int frames) {
        this.frames = frames;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.size.x = w;
    }

    /**
     * @param h the h to set
     */
    public void setH(int h) {
        this.size.y = h;
    }
    
    public Vector2D getSize(){
        return size;
    }

    /**
     * @return the xOffset
     */
    public int getXoffset() {
        return xOffset;
    }

    /**
     * @param xOffset the xOffset to set
     */
    public void setXoffset(int xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * @return the yOffset
     */
    public int getYoffset() {
        return yOffset;
    }

    /**
     * @param yOffset the yOffset to set
     */
    public void setYoffset(int yOffset) {
        this.yOffset = yOffset;
    }
}
