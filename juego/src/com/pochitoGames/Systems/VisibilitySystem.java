package com.pochitoGames.Systems;

import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Visibility;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Engine.Vector2D;

import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.util.List;

public class VisibilitySystem extends System {

    public VisibilitySystem() {
        include(Visibility.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {
        List<Entity> entities = getEntities();
        for (int i = 0; i < entities.size()-1; i++) {
            Entity e1 = entities.get(i);
            Visibility v1 = e1.get(Visibility.class);
            Position p1 = e1.get(Position.class);
            for(int j = i+1; j < entities.size(); j++){
                Entity e2 = entities.get(j);
                Position p2 = e2.get(Position.class);
                Visibility v2 = e2.get(Visibility.class);
                float dist = Vector2D.sub(p1.getWorldPos(), p2.getWorldPos()).magnitude();
                if( dist < v1.getVisibilityRadius()){
                    v1.addEntity(e2);
                }
                if(dist < v2.getVisibilityRadius()) {
                    v2.addEntity(e1);
                }
            }
            for(Entity e : v1.getVisibility()){
                Position p2 = e.get(Position.class);
                float dist = Vector2D.sub(p1.getWorldPos(), p2.getWorldPos()).magnitude();
                if( dist > v1.getVisibilityRadius()){
                    v1.removeEntity(e);
                }
            }

        }
    }
}
