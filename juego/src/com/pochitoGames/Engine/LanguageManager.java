/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;


import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import java.io.File;  
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
/**
 *
 * @author PochitoMan
 */
public class LanguageManager {
    
    Map<String, String> text;
    
    private static LanguageManager instance;
    
    private LanguageManager(){
        text = new HashMap<>();
    }
    
    public static LanguageManager getInstance(){
        if(instance == null)
            instance = new LanguageManager();
        return instance;
    }
    
    public void loadLanguage(Language lang){
        String filename = "src\\com\\pochitoGames\\Resources\\Localization\\";
        switch(lang){
            case ENGLISH:
                filename = filename.concat("eng.xml");
                break;
            case SPANISH:
                filename = filename.concat("esp.xml");
                break;
            default: 
                java.lang.System.out.println("Unsupported language");
                return;
        }
        
        try{    
            //File file = new File(filename);  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            DocumentBuilder db = dbf.newDocumentBuilder();  
            Document doc = db.parse(filename);  
            NodeList nodeList = doc.getElementsByTagName("entry"); 
            
            text.clear();
            
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                String id = node.getAttributes().getNamedItem("id").getNodeValue();
                String t = node.getTextContent();
                text.put(id, t);
            }
        }
        catch(IOException | ParserConfigurationException | DOMException | SAXException e){
            e.printStackTrace();  
        }
        
    }
    
    public String getText(String id){
        return text.get(id);
    }
    

}
