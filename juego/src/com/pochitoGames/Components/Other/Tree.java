package com.pochitoGames.Components.Other;

import com.pochitoGames.Engine.Component;

public class Tree extends Component {

    private int life = 100;
    private int posX;
    private int posY;


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
