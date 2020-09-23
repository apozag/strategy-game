package com.pochitoGames.Systems;

import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Soldier;
import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Engine.*;
import com.pochitoGames.Engine.System;

public class BuildingSystem extends System {


    boolean start = false;

    public BuildingSystem(){
        include(Soldier.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {

        for(Entity e : getEntities()){
            if(!start){
                for(MouseEventData data : EventManager.getInstance().getMouseEvents()){
                    if(data.getType() == MouseEventType.LEFT_CLICK && SpriteSystem.isInsideSprite((Sprite)(e.get(Sprite.class)), data.getPosition()))
                        start = !start;
                }
            }
            else{
                Position position = ((Position)e.get(Position.class));
                position.setLocalPos(Vector2D.add(position.getLocalPos(), new Vector2D(2.0f, 1.0f)));
            }
        }
    }
}
