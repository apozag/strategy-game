package com.pochitoGames.Systems.People;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.Visibility;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Charriot;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.System;

public class CharriotSystem extends System {

    public CharriotSystem() {
        include(Position.class, Sprite.class, Human.class, Charriot.class, Visibility.class, PathFinding.class);
        exclude();
    }

    @Override
    public void update(double dt) {

    }
}
