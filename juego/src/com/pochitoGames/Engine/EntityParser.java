/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author PochitoMan
 */
public class EntityParser {
    static Map<String, Class<? extends Component>> components = new HashMap<>();
    
    public static void registerComponent(String tag, Class<? extends Component> componentClass){
        if(components.containsKey(tag)){
            java.lang.System.out.println("Error: the tag \"" + tag + "\" is already being used.");
            return;
        }
        components.put(tag, componentClass);
    }
    
    public static void parseFile(String filename){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(filename);
            Element docEl = doc.getDocumentElement();       
            parseElement(null, docEl);           
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }
    }
    
    static void parseElement(Entity parent, Node node){
        Node childNode = node.getFirstChild();             
        while( childNode.getNextSibling()!=null ){ 
            childNode = childNode.getNextSibling();         
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            if (childNode.getNodeName().equals("component") && parent != null) {         
                ECS.getInstance().addComponent(parent, parseComponent(childNode));
            }
            
            else if(childNode.getNodeName().equals("entity")){
                Entity entity = ECS.getInstance().createEntity(parent);
                parseElement(entity, childNode);
            }
        }                
    }
    
    static Component parseComponent(Node node){                
        Element element = (Element) node; 
        Component component = null;
        try {
            Class componentClass = components.get(element.getAttribute("type"));
            Constructor constructor = componentClass.getConstructor(Node.class);
            component = (Component) constructor.newInstance(node);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(EntityParser.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return component;
    }
}
