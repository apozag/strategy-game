package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.*;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.States.BuildingState;
import com.pochitoGames.Misc.States.BuilderState;

public class BuildingSystem extends System {

    public BuildingSystem(){
        include(Building.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {

        for(Entity e : getEntities()){
            Building b = e.get(Building.class);
            Sprite s = e.get(Sprite.class);
            BuildingState state = b.getState();
            switch(state){
                case PLANNED:
                    if(!b.isFinished()){
                        Builder c = PeopleManager.getInstance().getNearestBuilder(b.getEntryCell()); 
                        if(c != null){
                            PathFinding pf = c.getEntity().get(PathFinding.class);                            
                            c.setTargetBuilding(b);
                            pf.setTargetCell(b.getEntryCell());
                            c.setState(BuilderState.BUILD);
                            b.setState(BuildingState.BUILDING);                                                       
                        }
                    }
                    else{
                        b.setState(BuildingState.BUILDING);
                    }
                    break;
                case BUILDING:
                    if(b.isFinished()){
                        if(s != null)
                            s.setCurrentAnimationIndex(1);   
                        b.setState(BuildingState.FINISHED);
                    }
                    break;
                case FINISHED:
                    break;
            }
            
        }
    }
}
