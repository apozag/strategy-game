/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Visual;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.ImageManager;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Misc.Other.Animation;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PochitoMan
 */
//Uno de los componentes más importantes.
//Contiene info sobre la imagen y las posibles animaciones.
public class Sprite extends Component {

    private BufferedImage image;
    //srcPos no se refiere a la posción del sprite, sino a la posicioón del cacho de imagen que se va a renderizar
    //Si no tiene animaciones, será 0, 0
    private Vector2D srcPos;
    //Igual con srcSize. Si no tiene animaciones, será igual al tamaño de la imagen.
    //Si SI tiene animaciones, será igual al tamaño del frame de la animación.
    private Vector2D srcSize;

    private Vector2D anchor;
    //profuncidad a la hora de pintar unos sprites sobre otros
    private float depth;

    private boolean updateDepth;

    private boolean animated = false;
    
    private float transparency = 1.0f;
   
    //Indica el índice de la animacion que se está pintando actualmente. 
    //Si no tiene animaciones, es -1
    private int currentAnimation = -1;

    //Lista con las animaciones
    List<Animation> animations;

    public Sprite() {
        depth = 0;
        srcPos = new Vector2D(0, 0);
        srcSize = new Vector2D(0, 0);
        anchor = new Vector2D(0, 0);
    }

    public Sprite(String path, Vector2D anchor, boolean updateDepth, float transparency, Animation... animations) {
        image = ImageManager.getImage(path);
        this.updateDepth = updateDepth;
        if (updateDepth) {
            depth = 0;
        } else {
            depth = Float.MAX_VALUE;
        }
        srcPos = new Vector2D(0, 0);
        if(image != null)
            srcSize = new Vector2D(image.getWidth(), image.getHeight());
        else 
            srcSize = new Vector2D(0, 0);
        this.animations = new LinkedList<>();
        for (Animation anim : animations) {
            animated = true;
            currentAnimation = 0;
            this.animations.add(anim);//Aquí la metemos en la lista.
        }
        this.anchor = anchor;
        this.transparency = transparency;
        
    }

    //Hay dos constructores parecidos pero pasándole el path de la imagen o pasándole la imagen directamete
    public Sprite(BufferedImage image, Vector2D anchor, boolean updateDepth, float transparency, Animation... animations) {
        this.image = image;
        this.updateDepth = updateDepth;
        if (updateDepth) {
            depth = 0;
        } else {
            depth = Float.MAX_VALUE;
        }
        srcPos = new Vector2D(0, 0);
        srcSize = new Vector2D(image.getWidth(), image.getHeight());

        for (Animation anim : animations) {
            animated = true;
            currentAnimation = 0;
            this.animations.add(anim);
        }
        this.anchor = anchor;
        this.transparency = transparency;
    }

    public void setSrcSize(Vector2D size) {
        srcSize = size;
    }

    public void setSrcPos(Vector2D pos) {
        srcPos = pos;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        setSrcSize(new Vector2D(image.getWidth(), image.getHeight()));
    }
    
    public void setImage(String path) {
        image = ImageManager.getImage(path);
        setSrcSize(new Vector2D(image.getWidth(), image.getHeight()));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getDepth() {
        return depth;
    }

    public Vector2D getSrcPos() {
        return srcPos;
    }

    public Vector2D getSrcSize() {
        return srcSize;
    }

    public Vector2D getAnchor() {
        return anchor;
    }

    public void setCurrentAnimationIndex(int index) {
        if (animated && index < animations.size()) {
            currentAnimation = index;
        }
    }

    public int getCurrentAnimationIndex() {
        return currentAnimation;
    }

    public Animation getCurrentAnimation() {
        if (!animated || currentAnimation < 0) {
            return null;
        }
        return animations.get(currentAnimation);
    }

    public boolean isDepthUpdated() {
        return updateDepth;
    }

    public boolean isAnimated() {
        return animated;
    }

    public float getTransparency() {
        return transparency;
    }
    
    public void setTransparency(float transparency){
        this.transparency = transparency;
    }
    
    public void dye(Color color) {
        if(image != null){
            int w = image.getWidth();
            int h = image.getHeight();
            BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = dyed.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.setComposite(AlphaComposite.SrcAtop);
            g.setColor(color);
            g.fillRect(0, 0, w, h);
            g.dispose();
            image = dyed;
        }
  }
}
