package com.pochitoGames.Systems.Buildings;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.*;
import com.pochitoGames.Engine.System;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.States.BuildingState;
import com.pochitoGames.Misc.States.BuilderState;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;

public class BuildingSystem extends System {

    public BuildingSystem() {
        include(Building.class, Position.class);
        exclude();
    }

    @Override
    public void update(double dt) {

        for (Entity e : getEntities()) {
            Building b = e.get(Building.class);
            Sprite s = e.get(Sprite.class);
            BuildingState state = b.getState();
            if (b.getLife() <= 10) b.setState(BuildingState.BEING_REPAIRED);
                switch (state) {
                    case PLANNED:
                        if(b.isFinished()){
                            b.setState(BuildingState.BUILDING);
                        }
                        break;
                    case BUILDING:
                        
                        if (b.isFinished()) {
                            if (s != null)
                                s.setCurrentAnimationIndex(1);
                                //El suelo es un caso especial: no se cambia su sprite sino que se avisa al tilemap para que cambie la imagen
                            else if (b.getTypeBuilding() == TypeBuilding.FLOOR) {
                                MapInfo.getInstance().setTileId(b.getCell().col, b.getCell().row, 5);
                            }
                            b.setState(BuildingState.FINISHED);
                        }
                        else if(b.builder.getTargetBuilding()!= b){
                            b.setState(BuildingState.PLANNED);
                        }
                        break;
                    case FINISHED:
                        // Si es suelo, realmente ya no hace nada asique se puede eliminar la entidad
                        if(b.getTypeBuilding() == TypeBuilding.FLOOR){
                            ECS.getInstance().removeEntity(e);
                        }
                        break;
                    case BEING_REPAIRED:
                        Builder c = PeopleManager.getInstance().getNearestBuilder(b.getOwnerType(), b.getEntryCell());
                        PathFinding pf = c.getEntity().get(PathFinding.class);
                        pf.setTargetCell(b.getEntryCell());
                        pf.start();
                        c.setTargetBuilding(b);
                        c.setState(BuilderState.REPAIR);

                }

        }
    }
}
