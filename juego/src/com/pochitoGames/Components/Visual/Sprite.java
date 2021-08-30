/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.Visual;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Engine.Engine;
import com.pochitoGames.Engine.ImageManager;
import com.pochitoGames.Engine.Vector2D;
import com.pochitoGames.Engine.Window;
import com.pochitoGames.Misc.Other.Animation;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author PochitoMan
 */
//Uno de los componentes más importantes.
//Contiene info sobre la imagen y las posibles animaciones.
public class Sprite extends Component {

    private BufferedImage[][] image;
    public String src;
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
    
    private boolean visible = true;
   
    //Indica el índice de la animacion que se está pintando actualmente. 
    private int currentAnimation = 0;
    private int currentFrame= 0;

    //Lista con las animaciones
    List<Animation> animations;
    
    public Sprite(Node node){
        Element element = (Element) node;
        String path = element.getElementsByTagName("src").item(0).getTextContent();
        animations = new ArrayList<>();
        NodeList animationList = element.getElementsByTagName("animation");
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
            animations.add(new Animation(frames, speed, w, h, xOffset, yOffset));
        }
        
        BufferedImage img = ImageManager.getImage(path);
        
        NodeList listdepth = element.getElementsByTagName("updatedepth");
        if(listdepth.getLength()>0)
            this.updateDepth = Boolean.parseBoolean(listdepth.item(0).getTextContent());
        else
            this.updateDepth = true;
        
        if (updateDepth) {
            depth = 0;
        } else {
            depth = Integer.parseInt(element.getElementsByTagName("depth").item(0).getTextContent()) + Engine.getInstance().getWindow().getSize().height;
        }                
        
        if(img == null)
            srcSize = new Vector2D(0, 0);
        else{
            if(animations == null || animations.isEmpty()){
                srcPos = new Vector2D(0, 0);
                srcSize = new Vector2D(img.getWidth(), img.getHeight());
                this.image = new BufferedImage[1][];
                this.image[0] = new BufferedImage[1];
                this.image[0][0] = img;
                this.animations = new LinkedList<>();
                this.animations.add(new Animation(1, 100, (int)srcSize.x, (int)srcSize.y, 0, 0));
            }
            else{
                int idx = 0;
                this.image = new BufferedImage[animations.size()][];
                this.animations = animations;
                for (Animation anim : animations) {

                    this.image[idx] = new BufferedImage[anim.getFrames()];
                    for(int i = 0; i < anim.getFrames(); i++){
                        this.image[idx][i] = img.getSubimage((int)anim.getXoffset() + (int)anim.getSize().x * i, (int)anim.getYoffset(), (int)anim.getSize().x, (int)anim.getSize().y);            
                    }
                    idx++;
                }
                srcSize = new Vector2D(this.image[0][0].getWidth(), this.image[0][0].getHeight());
            }
        }
        this.anchor = new Vector2D(0,0);
        this.transparency = transparency;
    }

    public Sprite() {
        depth = 0;
        this.animations = new LinkedList<>();
        srcPos = new Vector2D(0, 0);
        srcSize = new Vector2D(0, 0);
        anchor = new Vector2D(0, 0);
    }

    public Sprite(String path, Vector2D anchor, boolean updateDepth, float transparency, Animation... animations) {
        BufferedImage img = ImageManager.getImage(path);
        this.updateDepth = updateDepth;
        if (updateDepth) {
            depth = 0;
        } else {
            depth = Float.MAX_VALUE;
        }
        srcPos = new Vector2D(0, 0);        
        
        this.animations = new LinkedList<>();
        if(img == null)
            srcSize = new Vector2D(0, 0);
        else{
            if(animations.length == 0){
                srcPos = new Vector2D(0, 0);
                srcSize = new Vector2D(img.getWidth(), img.getHeight());
                this.image = new BufferedImage[1][];
                this.image[0] = new BufferedImage[1];
                this.image[0][0] = img;
                this.animations.add(new Animation(1, 100, (int)srcSize.x, (int)srcSize.y, 0, 0));
            }
            else{
                int idx = 0;
                this.image = new BufferedImage[animations.length][];
                for (Animation anim : animations) {
                    this.animations.add(anim);

                    this.image[idx] = new BufferedImage[anim.getFrames()];
                    for(int i = 0; i < anim.getFrames(); i++){
                        this.image[idx][i] = img.getSubimage((int)anim.getXoffset() + (int)anim.getSize().x * i, (int)anim.getYoffset(), (int)anim.getSize().x, (int)anim.getSize().y);            
                    }
                    idx++;
                }
                srcSize = new Vector2D(this.image[0][0].getWidth(), this.image[0][0].getHeight());
            }
        }
        this.anchor = anchor;
        this.transparency = transparency;
        src = path;
    }

    //Hay dos constructores parecidos pero pasándole el path de la imagen o pasándole la imagen directamete
    public Sprite(BufferedImage image, Vector2D anchor, boolean updateDepth, float transparency, Animation... animations) {
        BufferedImage img = image;
        this.updateDepth = updateDepth;
        if (updateDepth) {
            depth = 0;
        } else {
            depth = Float.MAX_VALUE;
        }        
        
        this.animations = new LinkedList<>();
        
        if(img == null)
            srcSize = new Vector2D(0, 0);
        else{
            if(animations.length == 0){
                srcPos = new Vector2D(0, 0);
                srcSize = new Vector2D(img.getWidth(), img.getHeight());
                this.image = new BufferedImage[1][];
                this.image[0] = new BufferedImage[1];
                this.image[0][0] = img;
                this.animations.add(new Animation(1, 100, (int)srcSize.x, (int)srcSize.y, 0, 0));
            }
            else{
                int idx = 0;
                this.image = new BufferedImage[animations.length][];
                for (Animation anim : animations) {
                    this.animations.add(anim);

                    this.image[idx] = new BufferedImage[anim.getFrames()];
                    for(int i = 0; i < anim.getFrames(); i++){
                        this.image[idx][i] = img.getSubimage((int)anim.getXoffset() + (int)anim.getSize().x * i, (int)anim.getYoffset(), (int)anim.getSize().x, (int)anim.getSize().y);            
                    }
                    idx++;
                }
                srcSize = new Vector2D(this.image[0][0].getWidth(), this.image[0][0].getHeight());
            }
        }
        this.anchor = anchor;
        this.transparency = transparency;
    }    

    //Hay dos constructores parecidos pero pasándole el path de la imagen o pasándole la imagen directamete
    public Sprite(String path,  Vector2D anchor, boolean updateDepth, float transparency, List<Animation> animations) {
        BufferedImage img = ImageManager.getImage(path);
        this.updateDepth = updateDepth;
        if (updateDepth) {
            depth = 0;
        } else {
            depth = Float.MAX_VALUE;
        }        
        
        
        if(img == null)
            srcSize = new Vector2D(0, 0);
        else{
            if(animations == null || animations.isEmpty()){
                srcPos = new Vector2D(0, 0);
                srcSize = new Vector2D(img.getWidth(), img.getHeight());
                this.image = new BufferedImage[1][];
                this.image[0] = new BufferedImage[1];
                this.image[0][0] = img;
                this.animations = new LinkedList<>();
                this.animations.add(new Animation(1, 100, (int)srcSize.x, (int)srcSize.y, 0, 0));
            }
            else{
                int idx = 0;
                this.image = new BufferedImage[animations.size()][];
                this.animations = animations;
                for (Animation anim : animations) {

                    this.image[idx] = new BufferedImage[anim.getFrames()];
                    for(int i = 0; i < anim.getFrames(); i++){
                        this.image[idx][i] = img.getSubimage((int)anim.getXoffset() + (int)anim.getSize().x * i, (int)anim.getYoffset(), (int)anim.getSize().x, (int)anim.getSize().y);            
                    }
                    idx++;
                }
                srcSize = new Vector2D(this.image[0][0].getWidth(), this.image[0][0].getHeight());
            }
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
        srcPos = new Vector2D(0, 0);
        if(animations.isEmpty()){
            this.image = new BufferedImage[1][];
            this.image[0] = new BufferedImage[1];
            this.image[0][0] = image;
            this.animations.add(new Animation(1, 100, (int)srcSize.x, (int)srcSize.y, 0, 0));
            srcSize = new Vector2D(image.getWidth(), image.getHeight());
        }
        else{
            int idx = 0;
            this.image = new BufferedImage[animations.size()][];
            for (Animation anim : animations) {
                this.animations.add(anim);

                this.image[idx] = new BufferedImage[anim.getFrames()];
                for(int i = 0; i < anim.getFrames(); i++){
                    this.image[idx][i] = image.getSubimage((int)anim.getXoffset() + (int)anim.getSize().x * i, (int)anim.getYoffset(), (int)anim.getSize().x, (int)anim.getSize().y);            
                }
                idx++;
            }
            srcSize = new Vector2D(this.image[0][0].getWidth(), this.image[0][0].getHeight());
        }
    }
    
    public void setImage(String path) {
        setImage(ImageManager.getImage(path));
    }

    public BufferedImage getImage() {
        if(image == null)
            return null;
        return image[currentAnimation][currentFrame];
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
        if (index < animations.size()) {
            srcSize = new Vector2D(image[index][0].getWidth(), image[index][0].getHeight());
            currentAnimation = index;
        }
    }
    
    public void setCurrentFrame(int frame){
        this.currentFrame = frame;
    }

    public int getCurrentAnimationIndex() {
        return currentAnimation;
    }
    
    public int getCurrentFrame(){
        return currentFrame;
    }

    public Animation getCurrentAnimation() {
        //if (!animated || currentAnimation < 0) {
        //    return null;
        //}
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
        if(image[0][0] != null){
            int w = image[0][0].getWidth();
            int h = image[0][0].getHeight();
            BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = dyed.createGraphics();
            g.drawImage(image[0][0], 0, 0, null);
            g.setComposite(AlphaComposite.SrcAtop);
            g.setColor(color);
            g.fillRect(0, 0, w, h);
            g.dispose();
            image[0][0] = dyed;
        }
  }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void addAnimation(Animation animation){
        animations.add(animation);
    }
}
