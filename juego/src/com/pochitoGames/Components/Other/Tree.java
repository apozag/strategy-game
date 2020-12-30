package com.pochitoGames.Components.Other;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Other.Vector2i;

public class Tree extends Component {

    private int life = 100;
    private Vector2i cell;

    

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Vector2i getCell() {
        return cell;
    }

    public void setCell(Vector2i cell) {
        this.cell = cell;
    }

 
}
