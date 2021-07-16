/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    static Map<String, Class<? extends Component>> components;
    
    static void registerComponent(String tag, Class<? extends Component> componentClass){
        if(components.containsKey(tag)){
            java.lang.System.out.println("Error: the tag \"" + tag + "\" is already being used.");
            return;
        }
        components.put(tag, componentClass);
    }
    
    static void parseFile(String filename){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(filename);
            Element docEl = doc.getDocumentElement();       
            Node childNode = docEl.getFirstChild();  
           
            while( childNode.getNextSibling()!=null ){          
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {         
                    Element childElement = (Element) childNode;             
                    parseElement(null, childElement);
                }       
                childNode = childNode.getNextSibling();         
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }
    }
    
    static void parseElement(Entity parent, Node element){
        Node childNode = element.getFirstChild();             
        while( childNode.getNextSibling()!=null ){ 
            
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            if (childNode.getNodeName().equals("component") && parent != null) {         
                ECS.getInstance().addComponent(parent, parseComponent(childNode));
            }
            
            else if(childNode.getNodeName().equals("element")){
                Entity entity = ECS.getInstance().createEntity(parent);
                parseElement(entity, childNode);
            }
            childNode = childNode.getNextSibling();         
        }                
    }
    
    static Component parseComponent(Node node){                
        Element element = (Element) node; 
        Constructor constructor = null;
        Component component = null;
        try {
            constructor = components.get(element.getAttribute("type")).getConstructor(Node.class);
            component = (Component) constructor.newInstance(node);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(EntityParser.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return component;
    }
}
