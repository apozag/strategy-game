/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.Buildings.Canteen;
import com.pochitoGames.Components.Buildings.GoldFoundry;
import com.pochitoGames.Components.Buildings.LumberjackHut;
import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Components.Buildings.Refinery;
import com.pochitoGames.Components.Buildings.Sawmill;
import com.pochitoGames.Components.Buildings.School;
import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.PanelActivator;
import com.pochitoGames.Components.Visual.SeeThrough;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.MapInfo;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Engine.Vector2i;
import com.pochitoGames.Misc.Other.BuildingInfo;
import com.pochitoGames.Misc.Other.ResourceType;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

/**
 * @author PochitoMan
 */
public class BuildingManager {

    private static BuildingManager instance;
    // Blueprints contiene la info común de cada tipo de edificio. Mirar clase BuildingInfo.
    public static Map<TypeBuilding, BuildingInfo> blueprints = new HashMap<TypeBuilding, BuildingInfo>();

    // resourcesNeeded contiene los recursos que necesita cada tipo de edificio para construirse
    private static Map<TypeBuilding, Map<ResourceType, Integer>> resourcesNeeded = new HashMap<>();

    // buildings contiene la lista de edificios creados
    private List<Building> buildings;
    
    private BuildingManager() {
        buildings = new ArrayList<>();

        loadBuildingInfo();
    }

    public static BuildingManager getInstance() {
        if (instance == null) {
            instance = new BuildingManager();
        }
        return instance;
    }

    public boolean build(TypeHuman ownerType, TypeBuilding type, Vector2i cell) {

        if (cell.col < 0 || cell.col >= MapInfo.getInstance().getWidth() || cell.row < 0 || cell.row >= MapInfo.getInstance().getHeight()) {
            return false;
        }

        final BuildingInfo b = blueprints.get(type);

        if (!canBuild(type, cell)) {
            return false;
        }

        for (int i = 0; i < b.size.col; i++) {
            for (int j = 0; j < b.size.row; j++) {
                MapInfo.getInstance().setTileId(cell.col + i, cell.row - j, blueprints.get(type).id);
            }
        }
        Building newBuilding = null;
        float yAnchor = 1 - (float) b.size.row / (2 * b.height + b.size.col + b.size.row);
        switch (type) {
            case SAWMILL:
                newBuilding = new Building(ownerType, 100, 50, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 127, 0)),
                        new SeeThrough(),
                        new Sawmill(),
                        new MouseListener(0),
                        newBuilding,
                        new Warehouse(b.warehouse)
                );
                break;
            case QUARRY:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 127, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        newBuilding,
                        new Quarry(),
                        new Warehouse(b.warehouse)
                );
                break;
            case SCHOOL:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        newBuilding,
                        new School(),
                        new PanelActivator("SCHOOL")
                );
                break;
            case CANTEEN:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Canteen(),
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        new Warehouse(b.warehouse),
                        newBuilding);
                break;
            case CASTLE:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        newBuilding,
                        new Warehouse(b.warehouse)
                );
                break;
            case REFINERY:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        newBuilding,
                        new Refinery(2000),
                        new Warehouse(b.warehouse)
                );
                break;
            case GOLD_FOUNDRY:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        newBuilding,
                        new GoldFoundry(2000),
                        new Warehouse(b.warehouse)
                );
                break;
            case LUMBERJACK_HUT:
                newBuilding = new Building(ownerType, 50, 30, 10, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        new Sprite(b.image, new Vector2D(0, yAnchor), true, 1.0f,
                                new Animation(1, 1, 128, 128, 0, 0),
                                new Animation(1, 1, 128, 128, 128, 0)),
                        new SeeThrough(),
                        new MouseListener(0),
                        newBuilding,
                        new LumberjackHut(),
                        new Warehouse(b .warehouse)
                );
                break;
            case FLOOR:
                newBuilding = new Building(ownerType, 0, 0, 0, cell, type, new HashMap<>(resourcesNeeded.get(type)));
                ECS.getInstance().createEntity(null,
                        new Position(IsometricTransformations.isoToCartesian(cell)),
                        newBuilding);
                break;
        }

        buildings.add(newBuilding);
        
        return true;
    }

    // Devuelve true si se puede construir un edificio del tipo type en la casilla cell
    public boolean canBuild(TypeBuilding type, Vector2i cell) {
        BuildingInfo b = blueprints.get(type);

        int entryCellId = MapInfo.getInstance().getTileId(Vector2i.add(cell, b.entry));
        if (entryCellId >= 100 || entryCellId < 0) {
            return false;
        }
        for (int i = 0; i < b.size.col; i++) {
            for (int j = 0; j < b.size.row; j++) {
                int cellId = MapInfo.getInstance().getTileId(new Vector2i(cell.col + i, cell.row - j));
                if (cellId >= 100 || cellId < 0 || cellId == 5 || cellId == 6) {
                    return false;
                }
            }
        }
        return true;
    }

    public Building getNearestBuilding(Vector2i cell, TypeBuilding type) {
        Building nearest = null;
        double nearestDist = 9999;
        for (Building b : buildings) {
            BuildingInfo bi = blueprints.get(b.getTypeBuilding());
            double dist = cell.distance(Vector2i.add(bi.entry, b.getCell()));
            if (b.isFinished() && b.getTypeBuilding() == type && dist < nearestDist) {
                nearest = b;
                nearestDist = dist;
            }
        }
        if (nearest == null || nearest.getCell().col >= 999 || nearest.getCell().row >= 999) {
            return null;
        }
        return nearest;
    }

    public Building getNearestWarehouseGet(Vector2i cell, ResourceType type, TypeBuilding excludeType, Building excludeBuilding) {
        Building candidate = null;
        int nearestDist = 9999;
        int greatestAmount = 0;
        for (Building b : buildings) {
            if (b.isFinished()) {
                Warehouse wh = b.getEntity().get(Warehouse.class);
                if (wh != null && b != excludeBuilding && b.getTypeBuilding() != excludeType && wh.doesAddToGlobal() && wh.hasResource(type)) {
                    if (wh.getContent(type) > greatestAmount) {
                        greatestAmount = wh.getContent(type);
                        candidate = b;
                    } else if (wh.getContent(type) == greatestAmount) {
                        int dist = cell.distance(Vector2i.add(b.getCell(), blueprints.get(b.getTypeBuilding()).entry));
                        if (dist < nearestDist) {
                            nearestDist = dist;
                            candidate = b;
                        }
                    }
                }
            }
        }
        return candidate;
    }

    public Building getNearestWarehousePut(Vector2i cell, ResourceType type, TypeBuilding excludeType, Building excludeBuilding) {
        Building candidate = null;
        int nearestDist = 9999;
        int lowestAmount = 9999;
        for (Building b : buildings) {
            if (b.isFinished()) {
                Warehouse wh = b.getEntity().get(Warehouse.class);
                if (wh != null && b != excludeBuilding && b.getTypeBuilding() != excludeType && wh.canHave(type)) {
                    if (wh.getContent(type) < lowestAmount) {
                        lowestAmount = wh.getContent(type);
                        candidate = b;
                    } else if (wh.getContent(type) == lowestAmount) {
                        int dist = cell.distance(Vector2i.add(b.getCell(), blueprints.get(b.getTypeBuilding()).entry));
                        if (dist < nearestDist) {
                            nearestDist = dist;
                            candidate = b;
                        }
                    }
                }
            }
        }
        return candidate;
    }

    private void loadBuildingInfo() {
        String filename = "src\\com\\pochitoGames\\Resources\\GameInfo\\buildings.xml";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(filename);
            NodeList nodeList = doc.getElementsByTagName("building");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                TypeBuilding type = TypeBuilding.valueOf(element.getAttributeNode("type").getValue());

                String life = element.getElementsByTagName("life").item(0).getTextContent();
                String defense = element.getElementsByTagName("defense").item(0).getTextContent();
                String attack = element.getElementsByTagName("attack").item(0).getTextContent();

                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());

                Element entry = (Element) element.getElementsByTagName("entry").item(0);
                String x = entry.getElementsByTagName("x").item(0).getTextContent();
                String y = entry.getElementsByTagName("y").item(0).getTextContent();
                Vector2i entryCell = new Vector2i(Integer.parseInt(x), Integer.parseInt(y));

                Element size = (Element) element.getElementsByTagName("size").item(0);
                x = size.getElementsByTagName("x").item(0).getTextContent();
                y = size.getElementsByTagName("y").item(0).getTextContent();
                Vector2i sizeVec = new Vector2i(Integer.parseInt(x), Integer.parseInt(y));

                int height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());

                String imgSrc = element.getElementsByTagName("img").item(0).getTextContent();

                Map<ResourceType, Integer> resMap = new HashMap<>();
                Element resNeeded = (Element) element.getElementsByTagName("resourcesneeded").item(0);
                NodeList resList = resNeeded.getElementsByTagName("resource");
                for (int j = 0; j < resList.getLength(); j++) {
                    Node elemNode = resList.item(j);
                    Element resElem = (Element) elemNode;
                    ResourceType resType = ResourceType.valueOf(resElem.getAttribute("type"));
                    int amount = Integer.parseInt(elemNode.getTextContent());

                    resMap.put(resType, amount);
                }

                resourcesNeeded.put(type, resMap);

                Element whElem = (Element) element.getElementsByTagName("warehouse").item(0);
                Warehouse wh = null;
                if (whElem != null) {
                    boolean addsToGlobal = false;
                    if (whElem.getAttribute("addsToGlobal").equals("YES")) {
                        addsToGlobal = true;
                    }
                    Map<ResourceType, Integer> content = new HashMap<>();
                    Map<ResourceType, Integer> capacity = new HashMap<>();
                    HashSet<ResourceType> transferable = new HashSet<>();
                    resList = whElem.getElementsByTagName("resource");
                    for (int j = 0; j < resList.getLength(); j++) {
                        Node elemNode = resList.item(j);
                        Element resElem = (Element) elemNode;
                        ResourceType resType = ResourceType.valueOf(resElem.getAttribute("type"));
                        String isTransferable = resElem.getAttribute("transferable");
                        int amount = Integer.parseInt(resElem.getElementsByTagName("amount").item(0).getTextContent());
                        int cap = Integer.parseInt(resElem.getElementsByTagName("capacity").item(0).getTextContent());

                        content.put(resType, amount);
                        capacity.put(resType, cap);
                        if (isTransferable.equals("YES")) {
                            transferable.add(resType);
                        }
                    }

                    wh = new Warehouse(
                            addsToGlobal,
                            content,
                            capacity,
                            transferable
                    );
                }
                blueprints.put(type, new BuildingInfo(
                        id,
                        entryCell,
                        sizeVec,
                        height,
                        imgSrc,
                        resMap,
                        wh
                ));

            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }
    }

    /*
    // No se usa de momento
    public Building getUnfinishedBuilding() {
        for (Building b : buildings) {
            if (!b.isFinished())
                return b;
        }
        return null;
    }
     */
}
