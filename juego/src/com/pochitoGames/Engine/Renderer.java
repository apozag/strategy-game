package com.pochitoGames.Engine;

import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.Visual.Text;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
/**
 *
 * @author PochitoMan
 */

//Aquí se pinta todo lo del juego
public class Renderer extends JPanel{
    
    private static Renderer instance;
            
    //En esta lista se van guardando los sprites que quieren ser piuntados
    private PriorityQueue<Sprite> renderQueue;
    
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
        renderQueue = new PriorityQueue<>(new SortByDepth());
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
        if(!renderLock && !renderQueue.isEmpty()){
            renderLock = true;
            requestFocus();
            super.paintComponent(g);
            this.setBackground(new Color(0, 0, 0));
            Graphics2D g2D = (Graphics2D) g;
            //AffineTransform transform = Camera.getInstance().getZoom();
            //g2D.scale(transform.getScaleX(), transform.getScaleY());           
           
            while(!renderQueue.isEmpty()){
                Sprite s = renderQueue.poll();
                if(s != null && s.getImage() != null){
                    Position p = s.getEntity().get(Position.class);
                    
                    BufferedImage img = s.getImage();                   
                    
                    Vector2D dstPos = p.getWorldPos();
                    
                    if(!p.isLocked())
                        dstPos = Camera.getInstance().toCameraCoords(dstPos);
                    
                    if(s.getTransparency() < 1.0f)
                        g2D.setComposite(AlphaComposite.SrcOver.derive(s.getTransparency())); 
                    
                    dstPos.x -= s.getSrcSize().x*s.getAnchor().x;
                    dstPos.y -= s.getSrcSize().y*s.getAnchor().y;
                    g2D.drawImage(img, (int)dstPos.x, (int)dstPos.y , null);     
                    
                    // Deshacer trasformaciones
                    if(s.getTransparency() < 1.0f)
                        g2D.setComposite(AlphaComposite.SrcOver.derive(1.0f)); 
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
                String str = text.getText();
                int rWidth = 0;
                int rHeight = 0;
                if(text.isCentered()){
                    Rectangle2D rect = text.getFont().getStringBounds(str, new FontRenderContext(null, true, true));
                    rWidth = (int) Math.round(rect.getWidth());
                    rHeight = (int) Math.round(rect.getHeight());
                }
                //int rHeight = (int) Math.round(rect.getHeight());
                g2D.drawString(str, pos.x - rWidth/2, pos.y + rHeight);
            }
            
            //FPS counter
            long currentTick = java.lang.System.currentTimeMillis();
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
            
            g2D.dispose();
        }
        renderLock = false;
    }            
    
}



class SortByDepth implements Comparator<Sprite> 
{ 
    @Override
    public int compare(Sprite a, Sprite b) 
    { 
        return (int)(a.getDepth() - b.getDepth()); 
    } 
} 
