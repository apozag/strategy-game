/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Misc.Other.BuildingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Systems.Buildings.QuarrySystem;

/**
 * @author PochitoMan
 */
public class BuildingManager {

    private static BuildingManager instance;
    public static Map<TypeBuilding, BuildingInfo> blueprints = new HashMap<TypeBuilding, BuildingInfo>();
    private static Map<TypeBuilding, Map<ResourceType, Integer>> resourcesNeeded = new HashMap<>();

    private List<Building> buildings;

    private BuildingManager() {
        //              id                                      id       pos               pos entrada         ancho y alto    altura
        blueprints.put(TypeBuilding.SAWMILL, new BuildingInfo(100, new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Ayuntamiento
        blueprints.put(TypeBuilding.QUARRY, new BuildingInfo(101, new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Almacén
        blueprints.put(TypeBuilding.CANTEEN, new BuildingInfo(102, new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Lo que sea
        blueprints.put(TypeBuilding.SCHOOL, new BuildingInfo(103, new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Lo que sea
        blueprints.put(TypeBuilding.CASTLE, new BuildingInfo(104, new Vector2i(-1, 0), new Vector2i(2, 2), 1));   // Lo que sea
        blueprints.put(TypeBuilding.FLOOR, new BuildingInfo(6, new Vector2i(0, 0), new Vector2i(1, 1), 0));   // Suelo

        resourcesNeeded.put(TypeBuilding.SAWMILL, new HashMap<ResourceType, Integer>() {
            {
                put(ResourceType.WOOD, 2);
                put(ResourceType.STONE, 2);
            }
        });
        resourcesNeeded.put(TypeBuilding.QUARRY, new HashMap<ResourceType, Integer>() {
            {
                put(ResourceType.WOOD, 2);
                put(ResourceType.STONE, 2);
            }
        });
        resourcesNeeded.put(TypeBuilding.CANTEEN, new HashMap<ResourceType, Integer>() {
            {
                put(ResourceType.WOOD, 2);
                put(ResourceType.STONE, 2);
            }
        });
        resourcesNeeded.put(TypeBuilding.SCHOOL, new HashMap<ResourceType, Integer>() {
            {
                put(ResourceType.WOOD, 2);
                put(ResourceType.STONE, 2);
            }
        });
        resourcesNeeded.put(TypeBuilding.CASTLE, new HashMap<ResourceType, Integer>() {
            {
                put(ResourceType.WOOD, 0);
                put(ResourceType.STONE, 0);
            }
        });
        resourcesNeeded.put(TypeBuilding.FLOOR, new HashMap<ResourceType, Integer>() {
            {
                put(ResourceType.STONE, 1);
            }
        });


        buildings = new ArrayList<>();
    }

    public static BuildingManager getInstance() {
        if (instance == null) {
            instance = new BuildingManager();
        }
        return instance;
    }

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public void build(TypeHuman ownerType, TypeBuilding type, Vector2i cell) {

        if (cell.col < 0 || cell.col >= MapInfo.getInstance().getWidth() || cell.row < 0 || cell.row >= MapInfo.getInstance().getHeight())
            return;

        final BuildingInfo b = blueprints.get(type);

        for (int i = 0; i < b.size.col; i++) {
            for (int j = 0; j < b.size.row; j++) {
                int cellId = MapInfo.getInstance().getTileId(new Vector2i(cell.col + i, cell.row + j));
                if (cellId >= 100 || cellId < 0 || cellId == 5 || cellId == 6)
                    return;
            }
        }

        for (int i = 0; i < b.size.col; i++) {
            for (int j = 0; j < b.size.row; j++) {
                MapInfo.getInstance().setTileId(cell.col + i, cell.row + j, blueprints.get(type).id);
            }
        }
        Building newBuilding = null;
        float yAnchor = 1 - (float) b.size.row / (2 * b.height + b.size.col + b.size.row);
        switch (type) {
            case SAWMILL:
                newBuilding = new Building(ownerType, 100, 50, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_wood.png", new Vector2D(0, yAnchor), true,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 127, 0)),
                        newBuilding);
                break;
            case QUARRY:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_stone.png", new Vector2D(0, yAnchor), true,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 127, 0)),
                        newBuilding, new Quarry());
                break;
            case SCHOOL:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_1.png", new Vector2D(0, yAnchor), true,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        newBuilding);
                break;
            case CANTEEN:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_1.png", new Vector2D(0, yAnchor), true,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        newBuilding);
                break;
            case CASTLE:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\building_castle.png", new Vector2D(0, yAnchor), true,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        newBuilding,
                        new Warehouse(new HashMap<ResourceType, Integer>() {
                            {
                                put(ResourceType.WOOD, 200);
                                put(ResourceType.STONE, 200);
                                put(ResourceType.GOLD, 200);
                            }
                        }));
                break;
            case FLOOR:
                newBuilding = new Building(ownerType, 0, 0, 0, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        newBuilding);
                break;
        }

        buildings.add(newBuilding);
    }

    public Building getNearestBuilding(Vector2i cell, TypeBuilding type) {
        Building nearest = new Building(null, 0, 0, 0, new Vector2i(999, 999), null, null);
        double nearestDist = 9999;
        for (Building b : buildings) {
            BuildingInfo bi = blueprints.get(b.getTypeBuilding());
            double dist = cell.distance(Vector2i.add(bi.entry, b.getCell()));
            if (b.getTypeBuilding() == type && dist < nearestDist) {
                nearest = b;
                nearestDist = dist;
            }
        }
        if (nearest.getCell().col >= 999 || nearest.getCell().row >= 999)
            return null;
        return nearest;
    }

    public Building getNearestWarehouse(Vector2i cell, ResourceType type) {
        Building nearest = null;
        int nearestDist = 9999;
        for (Building b : buildings) {
            Warehouse wh = b.getEntity().get(Warehouse.class);
            //Solo preguntamos si tiene al menos una unidad!!! Cuidao
            if (wh != null && wh.hasResource(type)) {
                int dist = cell.distance(Vector2i.add(b.getCell(), blueprints.get(b.getTypeBuilding()).entry));
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearest = b;
                }
            }
        }
        return nearest;
    }

    public Building getUnfinishedBuilding() {
        for (Building b : buildings) {
            if (!b.isFinished())
                return b;
        }
        return null;
    }
}