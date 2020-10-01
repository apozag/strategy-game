package com.pochitoGames.Engine;

import com.pochitoGames.Components.Sprite;
import com.pochitoGames.Components.Position;
import com.pochitoGames.Components.Text;
import com.pochitoGames.Misc.Time;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
    
    boolean renderLock = false;
    
    long lastTick;
    int tickIndex = 0;
    int MAX_TICKS = 50;
    int[] lastFPS = new int[MAX_TICKS];
    int currentFPS;
        
    //El Constructor es privado para que nadie haga una instancia de Renderer
    private Renderer(){
        renderQueue = new LinkedList<>();
        textQueue = new LinkedList<>();
    }
    
    public static Renderer getInstance(){
        if(instance == null){
            instance = new Renderer();
        }
        return instance;
    }

    //Lo llaman los sprites cuando quieren que se les pinte
    public void renderSprite(Sprite img){
        if(!renderLock)
            renderQueue.add(img);
    }
    
    //Lo llaman los Text cuando quieren que se les pinte
    public void renderText(Text text){
        if(!renderLock)
            textQueue.add(text);
    }
    
    //Esto se llama desde Engine.
    //Se pinta todo lo que había en renderQueue y textQueue
    @Override
    public void paint(Graphics g){
        //BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //Gaphics bfg = bf.getGraphics();
        
        if(!renderLock && !renderQueue.isEmpty()){
            renderLock = true;
            requestFocus();
            super.paintComponent(g);
            this.setBackground(new Color(0, 0, 0));
            Graphics2D g2D = (Graphics2D) g;
            //Antes de pintar se ordenan los srites por profundidad
            renderQueue.sort(new SortByDepth());
            AffineTransform transform = Camera.getInstance().getZoom();
            //g2D.scale(transform.getScaleX(), transform.getScaleY());
            while(!renderQueue.isEmpty()){
                Sprite s = renderQueue.remove(0);
                if(s != null && s.getImage() != null){
                    Position p = s.getEntity().get(Position.class);
                    Vector2D dstPos = p.getWorldPos();
                    BufferedImage img = s.getImage();
                    if(s.getCurrentAnimationIndex() > 0)
                        img = img.getSubimage((int)s.getSrcPos().x, (int)s.getSrcPos().y, (int)s.getSrcSize().x, (int)s.getSrcSize().y);
                    
                    if(!p.isLocked())
                        dstPos = Camera.getInstance().toCameraCoords(dstPos);

                    dstPos.x -= s.getSrcSize().x*s.getAnchor().x;
                    dstPos.y -= s.getSrcSize().y*s.getAnchor().y;
                    g2D.drawImage(img, (int)dstPos.x, (int)dstPos.y , null);
                }
            }

            while(!textQueue.isEmpty()){
                Text text = textQueue.remove(0);
                Position p = text.getEntity().get(Position.class);
                Vector2D pos = p.getWorldPos();
                if(!p.isLocked())
                    pos = Camera.getInstance().toCameraCoords(p.getWorldPos());
                g2D.setFont(text.getFont());
                g2D.setColor(text.getColor());
                g2D.drawString(text.getText(), pos.x, pos.y);
            }
            
            //FPS counter
            long currentTick = Time.getTicks();
            int fps = (int)(1000/(currentTick - lastTick));
            lastTick = currentTick;
            lastFPS[tickIndex] = fps;
            tickIndex++;
            if(tickIndex >= MAX_TICKS-1){
                currentFPS = 0;
                for (int i = 0; i < tickIndex-1; i++) {
                    currentFPS += lastFPS[i];
                }
                currentFPS /= tickIndex+1;
                tickIndex = 0;            

            }
            g2D.setColor(Color.white);
            
            g2D.drawString("FPS: " + currentFPS, 50, 50);
        }
        renderLock = false;
        //g.drawImage(bf, this.getWidth(), this.getHeight(), this);
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
