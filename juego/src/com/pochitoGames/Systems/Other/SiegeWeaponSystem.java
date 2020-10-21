package com.pochitoGames.Systems.Other;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.Visibility;
import com.pochitoGames.Components.Other.SiegeWeapon;
import com.pochitoGames.Components.People.Archer;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Engine.System;

public class SiegeWeaponSystem extends System {

    public SiegeWeaponSystem(){
        include(Human.class, Position.class, SiegeWeapon.class, Visibility.class, PathFinding.class);
        exclude();
    }


    @Override
    public void update(double dt) {

    }
}
