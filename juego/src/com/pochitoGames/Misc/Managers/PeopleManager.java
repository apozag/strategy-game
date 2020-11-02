/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Other.Vector2i;

/**
 *
 * @author PochitoMan
 */
public class PeopleManager {
    
    private static PeopleManager instance;
    
    private PeopleManager(){}
    
    public static PeopleManager getInstance(){
        if(instance == null)
            instance = new PeopleManager();
        return instance;
    }
    
    public void createCharacter(int id){
        switch(id){
            case 0:
                ECS.getInstance().createEntity(null,
                    new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png",
                            new Vector2D(0.5f, 1.0f),
                            true),
                    new Position(IsometricTransformations.isoToCartesian(new Vector2i(10, 10))),
                    new Human(100,"Sol",10,10, TypeHuman.BARBARIAN),
                    new Builder(),
                    new PathFinding(new Vector2i(10, 10))
                );
                break;
        }
    }
}
