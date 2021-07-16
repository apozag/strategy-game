/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.AlignmentType;
import java.util.ArrayList;
import java.util.List;

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

    List<PanelRect> children;

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
                int maxCols = (int) (size.x / (cell_size.x + h_padding));
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
                /*
                int maxCols = (int) (size.x / (cell_size.x + h_padding));
                int n = (int)Math.ceil(children.size() / maxCols);
                float offset = n * cell_size.y / 2;
                for(int i = 0; i < children.size(); i++){
                    PanelRect c = children.get(i);
                    Position cpos = c.getEntity().get(Position.class);
                    int row = (int) (i/maxCols);
                    cpos.setLocalPos(new Vector2D(cpos.getLocalPos().x, offset + row * (cell_size.y + v_padding)));
                }
*/
                int n = children.size();
                int maxCols = (int) (size.x / (cell_size.x + h_padding));
                int maxRows = (int) Math.ceil(n/maxCols) + 1;
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
}
