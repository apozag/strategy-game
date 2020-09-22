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
public class Vector2D {
    public float x, y;
    
    public Vector2D(){x = 0; y = 0;}
    public Vector2D(float x, float y){this.x = x; this.y =y;}
    public Vector2D(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }
    
    public void set(float x, float y){this.x = x; this.y = y;}
    
    public void add(Vector2D v){
        x += v.x;
        y += v.y;
    }
    public void sub(Vector2D v){
        x -= v.x;
        y -= v.y;
    }
    
    public void mult(float f){
        x *= f;
        y *= f;
    }
    
    public static Vector2D add(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }
    public static Vector2D mult(Vector2D v, float s){
        return new Vector2D(v.x * s, v.y * s);
    }
    public static Vector2D sub(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }
    
}
