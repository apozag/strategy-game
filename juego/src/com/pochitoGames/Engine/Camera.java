/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

/**
 *
 * @author PochitoMan
 */

//La camara es una clase estática que guarda la posición y velocidad de la cámara 
//para poder calcular la posición absoluta de las entidades del juego (worldPos del compoente Position)
public class Camera {
    Vector2D pos;
    Vector2D vel;
    
    //Solo hay una instancia de Camera en tosdo el programa. Así nos quitamos de tener una referencia a la cámara en todos lados.
    static Camera instance;
    
    //El constructor es privado para que nadie pueda crear una instancia de camera desde fuera.
    private Camera(Vector2D pos){
        this.pos = pos;
        this.vel = new Vector2D(0, 0);
    }
    
    //Devuelve la instancia única de Camera. 
    //Si no la hay aún la crea y la devuelve.
    public static Camera getInstance(){
        if(instance == null){
            instance = new Camera(new Vector2D(0, 0));
        }
        return instance;
    }
    
    public void setVel(Vector2D vel){
        this.vel = vel;
    }
    
    public Vector2D getVel(){
        return vel;
    }
    
    public void setPos(Vector2D pos){
        this.pos = pos;
    }
    
    public Vector2D getPos(){
        return pos;
    }
    
    public void update(double dt){
        
        EventManager manager = EventManager.getInstance();
        /*
        if(manager.isKeyDown('a'))
            vel.x = -100;
        else if(manager.isKeyDown('d'))
            vel.x = 100;
        else
            vel.x = 0;
        
        if(manager.isKeyDown('w'))
            vel.y = -100;
        else if(manager.isKeyDown('s'))
            vel.y = 100;
        else
            vel.y = 0;
        */
        Vector2D mousePos = manager.getMousePos();
        if(mousePos.x < 100)
            vel.x = -100;
        else if (mousePos.x > Renderer.getInstance().getWidth()-100)
            vel.x = 100;
        else
            vel.x = 0;
        
        if(mousePos.y < 100)
            vel.y = -100;
        else if (mousePos.y > Renderer.getInstance().getHeight()-100)
            vel.y = 100;
        else
            vel.y = 0;
        
        
        
        pos.x += vel.x * dt;
        pos.y += vel.y * dt;
    }
   
    //La usarán las entidades para convertir su posición local (en el mundo) a una posición relativa a la cámara.
    //De esta manera, si la cámara se mueve, todo lo del juego se moverá hacia el lado contrario para dar ese efecto.
    public Vector2D toCameraCoords(Vector2D v){
        v.sub(pos);
        return v;
    }
    
    public Vector2D toWorldCoords(Vector2D v){
        v.add(pos);
        return v;
    }
}
