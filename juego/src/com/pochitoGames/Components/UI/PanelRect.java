/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Entity;
import com.pochitoGames.Engine.EntityParser;
import com.pochitoGames.Engine.EventManager;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.AlignmentType;
import com.pochitoGames.Engine.UIManager;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author PochitoMan
 */
public class PanelRect extends Component {

    public AlignmentType h_alignment;
    public AlignmentType v_alignment;
    
    public Vector2D size;
    public Vector2D cell_size;

    public float h_padding;
    public float v_padding;   
    
    public boolean initialized;

    List<PanelRect> children;
    
    public String name;
    
    public PanelRect(Node node){        
        
        Element element = (Element) node;
        if(element.hasAttribute("name")){
            name = element.getAttribute("name");
        }
        
        Node childNode = node.getFirstChild();             
        while( childNode.getNextSibling()!=null ){ 
            childNode = childNode.getNextSibling();         
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            Element childElement = (Element) childNode;           
            
            switch(childNode.getNodeName()){
                case "alignment":
                {
                    String h = childElement.getElementsByTagName("horizontal").item(0).getTextContent();
                    String v = childElement.getElementsByTagName("vertical").item(0).getTextContent();
                    switch (h) {
                        case "BEGIN":
                            h_alignment = AlignmentType.BEGIN;
                            break;
                        case "MIDDLE":
                            h_alignment = AlignmentType.MIDDLE;
                            break;
                        case "END":
                            h_alignment = AlignmentType.END;
                            break;
                    }
                    switch (v) {
                        case "BEGIN":
                            v_alignment = AlignmentType.BEGIN;
                            break;
                        case "MIDDLE":
                            v_alignment = AlignmentType.MIDDLE;
                            break;
                        case "END":
                            v_alignment = AlignmentType.END;
                            break;
                    }
                }
                    break;
                case "size":  
                {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    size = new Vector2D(Float.parseFloat(x), Float.parseFloat(y));
                }
                    break;
                case "cellsize":
                {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    cell_size = new Vector2D(Float.parseFloat(x), Float.parseFloat(y));
                }
                    break;
                case "padding":
                {
                    String x = childElement.getElementsByTagName("x").item(0).getTextContent();
                    String y = childElement.getElementsByTagName("y").item(0).getTextContent();
                    h_padding = Float.parseFloat(x);
                    v_padding = Float.parseFloat(y);
                }
                    break;                    
            }
        }         
        children = new ArrayList<>();
    }

    public PanelRect(AlignmentType h_alignment, AlignmentType v_alignment, Vector2D size, Vector2D cell_size, float h_padding, float v_padding, List<PanelRect> children) {
        this(h_alignment, v_alignment, size, cell_size, h_padding, v_padding);
        for(PanelRect child : children){
            addChild(child);
        }
    }
    
    public PanelRect(AlignmentType h_alignment, AlignmentType v_alignment, Vector2D size, Vector2D cell_size, float h_padding, float v_padding) {
        this.h_alignment = h_alignment;
        this.v_alignment = v_alignment;
        this.size = size;
        this.cell_size = cell_size;
        this.h_padding = h_padding;
        this.v_padding = v_padding;
        children = new ArrayList<>();
    }
    
    public List<PanelRect> getChildren(){
        return children;
    }

    public void addChild(PanelRect child) {
        children.add(child);
        switch (h_alignment) {
            case BEGIN: 
            {
                float xPos;
                PanelRect lastChild = children.size() < 2 ? null : children.get(children.size() - 2);
                if (lastChild == null) {
                    xPos = h_padding;
                }
                else{
                    Vector2D lastChildPos = ((Position)(lastChild.getEntity().get(Position.class))).getLocalPos();                
                    xPos = lastChildPos.x + cell_size.x + h_padding;
                    if(xPos + cell_size.x + h_padding > size.x)
                        xPos = h_padding;
                }
                Position pos = child.getEntity().get(Position.class);
                pos.setLocalPos(new Vector2D(xPos, pos.getLocalPos().y));
                
            }
                break;
            case MIDDLE:
            {
                int n = children.size();
                int maxCols = Math.max(1, (int)(size.x / (cell_size.x + h_padding)));
                int maxRows = (int) Math.ceil(n/maxCols);
                for(int i = 0; i < children.size(); i++){
                    PanelRect c = children.get(i);
                    Position cpos = c.getEntity().get(Position.class);
                    int row = (int) (i/maxCols);
                    int col = i - (row * maxCols);
                    int n_local = maxCols;
                    if(row == maxRows-1)
                        n_local = n - (maxRows-1)*maxCols;
                    float offset = n_local * cell_size.x / 2;
                    cpos.setLocalPos(new Vector2D(offset + col * (cell_size.x + h_padding), cpos.getLocalPos().y));
                }
            }
                break;
            case END: 
            {
                float xPos;
                PanelRect lastChild = children.size() < 2 ? null : children.get(children.size() - 2);
                if (lastChild == null) {
                    xPos = size.x - cell_size.x - h_padding;
                }
                else{
                    Vector2D lastChildPos = ((Position)(lastChild.getEntity().get(Position.class))).getLocalPos();                
                    xPos = lastChildPos.x - cell_size.x - h_padding;
                    if(xPos - h_padding < 0)
                        xPos = size.x - cell_size.x - h_padding;
                }
                Position pos = child.getEntity().get(Position.class);
                pos.setLocalPos(new Vector2D(xPos, pos.getLocalPos().y));
            }
                break;
        }        
        switch (v_alignment) {
            case BEGIN:
            {
                float yPos;
                PanelRect lastChild = children.size() < 2 ? null : children.get(children.size() - 2);
                if (lastChild == null) {
                    yPos = v_padding;
                }
                else{
                    Vector2D lastChildPos = ((Position)(lastChild.getEntity().get(Position.class))).getLocalPos();  
                    yPos = lastChildPos.y;
                    float x = lastChildPos.x + cell_size.x + h_padding;
                    if(x + cell_size.x + h_padding > size.x)
                        yPos += cell_size.y + v_padding;
                }
                Position pos = child.getEntity().get(Position.class);
                pos.setLocalPos(new Vector2D(pos.getLocalPos().x, yPos));
            }
                break;
            case MIDDLE:
            {
                int n = children.size();
                int maxCols = Math.max(1, (int)(size.x / (cell_size.x + h_padding)));
                int maxRows = (int) Math.ceil(n/maxCols);
                for(int i = 0; i < children.size(); i++){
                    PanelRect c = children.get(i);
                    Position cpos = c.getEntity().get(Position.class);
                    int row = (int) (i/maxCols);
                    float offset = (size.y - maxRows * cell_size.y) / 2;
                    cpos.setLocalPos(new Vector2D(cpos.getLocalPos().x, offset + row * (cell_size.y + v_padding)));
                }
            }
                break;
            case END: 
            {
                float yPos;
                PanelRect lastChild = children.size() < 2 ? null : children.get(children.size() - 2);
                if (lastChild == null) {
                    yPos = size.y - cell_size.y - v_padding;
                }
                else{
                    Vector2D lastChildPos = ((Position)(lastChild.getEntity().get(Position.class))).getLocalPos();                
                    yPos = lastChildPos.y - cell_size.y - v_padding;
                    if(yPos - h_padding < 0)
                        yPos -= cell_size.y - v_padding;
                }
                Position pos = child.getEntity().get(Position.class);
                pos.setLocalPos(new Vector2D(pos.getLocalPos().x, yPos));
            }
                break;
        }        
    }
    
    @Override
    public void triggerEvent(String tag){
    }
    
}
