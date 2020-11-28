/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
/**
 *
 * @author PochitoMan
 */
public class GameInfoManager {
    private static GameInfoManager instance;
    
    private TypeHuman playerType;
    
    private GameInfoManager(){}
    
    public static GameInfoManager getInstance(){
        if(instance == null)
            instance = new GameInfoManager();
        return instance;
    }

    public TypeHuman getPlayerType() {
        return playerType;
    }

    public void setPlayerType(TypeHuman playerType) {
        this.playerType = playerType;
    }
    
}
