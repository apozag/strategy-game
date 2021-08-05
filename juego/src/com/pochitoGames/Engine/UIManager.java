/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.PanelRect;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Engine.ECS;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.AlignmentType;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.RectAlignment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

/**
 *
 * @author PochitoMan
 */
public class UIManager {

    private Map<String, Entity> panels = new HashMap<>();
    private String activePanel = null;

    private static UIManager instance;

    private UIManager() {
    }

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    public static Entity makeUI(Entity e, List<Entity> children) {
        if (!checkComponents(e)) {
            java.lang.System.out.println("Could not generate panel");
            return e;
        }
        PanelRect pr = e.get(PanelRect.class);
        for (Entity child : children) {
            child.setParent(e);
            e.addChild(child);
            pr.addChild(child.get(PanelRect.class));
        }
        return e;
    }

    public void addPanel(String tag, Entity e) {
        e.deactivate();
        panels.put(tag, e);
    }

    public void activatePanel(String tag) {
        if (activePanel != null) {
            panels.get(activePanel).deactivate();
        }
        panels.get(tag).activate();
        activePanel = tag;
    }

    public void deactivateActivePanel() {
        if (activePanel != null) {
            panels.get(activePanel).deactivate();
        }
    }

    public String getActivePanel() {
        return activePanel;
    }

    private static boolean checkComponents(Entity e) {
        return e.get(PanelRect.class) != null
                && e.get(Position.class) != null
                && e.get(MouseListener.class) != null;
    }

    public void loadUI() {
        String filename = "src\\com\\pochitoGames\\Resources\\GameInfo\\uiPanels.xml";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(filename);
            NodeList nodeList = doc.getElementsByTagName("panel");

            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node node = nodeList.item(i);
                String tag = node.getAttributes().getNamedItem("tag").getNodeValue();
                Element element = (Element) node;
                panels.put(tag, parsePanel(null, element));

            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Entity parsePanel(Entity parent, org.w3c.dom.Node node) {
        PanelRect rect = new PanelRect(AlignmentType.BEGIN, AlignmentType.BEGIN, new Vector2D(0, 0), new Vector2D(0, 0), 0, 0);
        Position pos = new Position(new Vector2D(), true);
        Entity panel = ECS.getInstance().createEntity(parent, new MouseListener(1), pos, rect);
        NodeList panelStuff = node.getChildNodes();
        for (int j = 0; j < panelStuff.getLength(); j++) {
            org.w3c.dom.Node childNode = panelStuff.item(j);
            if(childNode.getNodeName().equals("#text"))
                continue;
            Element childElement = (Element)childNode;
            switch (childNode.getNodeName()) {
                case "alignment":
                {
                    String h = childElement.getElementsByTagName("horizontal").item(0).getTextContent();
                    String v = childElement.getElementsByTagName("vertical").item(0).getTextContent();
                    switch (h) {
                        case "BEGIN":
                            rect.h_alignment = AlignmentType.BEGIN;
                            break;
                        case "MIDDLE":
                            rect.h_alignment = AlignmentType.MIDDLE;
                            break;
                        case "END":
                            rect.h_alignment = AlignmentType.END;
                            break;
                    }
                    switch (v) {
                        case "BEGIN":
                            rect.v_alignment = AlignmentType.BEGIN;
                            break;
                        case "MIDDLE":
                            rect.v_alignment = AlignmentType.MIDDLE;
                            break;
                        case "END":
                            rect.v_alignment = AlignmentType.END;
                            break;
                    }
                }
                    break;

                case "size":
                {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    rect.size = new Vector2D(Float.parseFloat(x), Float.parseFloat(y));
                }
                break;

                case "cellsize": {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    rect.cell_size = new Vector2D(Float.parseFloat(x), Float.parseFloat(y));
                }
                break;

                case "padding": {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    rect.h_padding = Float.parseFloat(x);
                    rect.v_padding = Float.parseFloat(y);
                }
                break;

                case "sprite":
                    String imagefile = childElement.getElementsByTagName("src").item(0).getTextContent();
                    List<Animation> animations = new ArrayList<>();
                    NodeList animationList = childElement.getElementsByTagName("animation");
                    for(int i = 0; i < animationList.getLength(); i++){                        
                        Element anim = (Element) animationList.item(i);
                        int frames = Integer.parseInt(anim.getElementsByTagName("frames").item(0).getTextContent());
                        int speed = Integer.parseInt(anim.getElementsByTagName("speed").item(0).getTextContent());
                        Element sizeElem = (Element) anim.getElementsByTagName("size").item(0);
                        int w = Integer.parseInt(sizeElem.getElementsByTagName("x").item(0).getTextContent());
                        int h = Integer.parseInt(sizeElem.getElementsByTagName("y").item(0).getTextContent());
                        Element offsetElem = (Element) anim.getElementsByTagName("offset").item(0);
                        int xOffset = Integer.parseInt(offsetElem.getElementsByTagName("x").item(0).getTextContent());
                        int yOffset = Integer.parseInt(offsetElem.getElementsByTagName("y").item(0).getTextContent());
                        //sprite.addAnimation(new Animation(frames, speed, w, h, xOffset, yOffset));
                        animations.add(new Animation(frames, speed, w, h, xOffset, yOffset));
                    }
                    Sprite sprite = new Sprite(imagefile, new Vector2D(0, 0), true, 1.0f, animations);
                    ECS.getInstance().addComponent(panel, sprite);
                    break;

                case "position": {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    pos.setLocalPos(new Vector2D(Float.parseFloat(x), Float.parseFloat(y)));
                }
                break;                
                
                case "children": {
                    NodeList children = childElement.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        String name = children.item(i).getNodeName();
                        if (!name.equals("#text") ) 
                            rect.addChild(parsePanel(panel, children.item(i)).get(PanelRect.class));
                    }
                }
                break;

            }
        }
        return panel;
    }
}
