package com.pochitoGames.Engine;

import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Text;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author PochitoMan
 */

//Aquí se pinta todo lo del juego
public class Renderer extends JPanel{
    
    //Como con la cámara, sólo hay una ionstancia estática de Renderer en todo el programa, para poder llamarla desde cualquier lado.
    private static Renderer instance;
    
    //En esta lista se van guiardando los sprites que quieren ser piuntados
    private List<Sprite> renderQueue;
    
    //Igual con el texto
    private List<Text> textQueue;
        
    //El Constructor es privado para que nadie haga una instancia de Renderer
    private Renderer(){
         renderQueue = new LinkedList<>();
         textQueue = new LinkedList<>();
         addMouseListener(EventManager.getInstance());
         addKeyListener(EventManager.getInstance());
         this.setFocusable(true);
    }
    
    public static Renderer getInstance(){
        if(instance == null){
            instance = new Renderer();
        }
        return instance;
    }

    //Lo llaman los sprites cuando quieren que se les pinte
    public void renderSprite(Sprite img){
        renderQueue.add(img);
    }
    
    //Lo llaman los Text cuando quieren que se les pinte
    public void renderText(Text text){
        textQueue.add(text);
    }
    
    //Esto se llama desde Engine.
    //Se pinta todo lo que había en renderQueue y textQueue
    @Override
    public void paint(Graphics g){
        requestFocus();
        super.paintComponent(g);
        this.setBackground(new Color(0, 0, 0));
        Graphics2D g2D = (Graphics2D) g;
        //Antes de pintar se ordenan los srites por profundidad
        renderQueue.sort(new SortByDepth());
        while(!renderQueue.isEmpty()){
            Sprite s = renderQueue.remove(0);
            Vector2D dstPos = ((Position)(s.getEntity().get(Position.class))).getWorldPos();

            BufferedImage img = s.getImage().getSubimage((int)s.getSrcPos().x, (int)s.getSrcPos().y, (int)s.getSrcSize().x, (int)s.getSrcSize().y);

            Vector2D posCamCoord = Camera.getInstance().toCameraCoords(dstPos);
            g2D.drawImage(img, (int)posCamCoord.x, (int)posCamCoord.y , null);
        }
        
        while(!textQueue.isEmpty()){
            Text text = textQueue.remove(0);
            Vector2D pos = Camera.getInstance().toCameraCoords(((Position)(text.getEntity().get(Position.class))).getWorldPos());
            g2D.setFont(text.getFont());
            g2D.drawString(text.getText(), pos.x, pos.y);
        }
        
    }    
    
}

class SortByDepth implements Comparator<Sprite> 
{ 
    @Override
    public int compare(Sprite a, Sprite b) 
    { 
        return a.getDepth() - b.getDepth(); 
    } 
} 
