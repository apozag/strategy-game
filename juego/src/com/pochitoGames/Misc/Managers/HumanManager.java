package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;

public class HumanManager {

    static HumanManager instance;
    int nestId = 0;

    private HumanManager(){

    }

    public static HumanManager getInstance(){
        if(instance == null)
            instance = new HumanManager();
        return instance;
    }

    public void createHuman(TypeHuman type, Vector2D pos){
        switch (type){
            case HUMAN:
                break;
            case ORC:
                break;
        }
    }

}
