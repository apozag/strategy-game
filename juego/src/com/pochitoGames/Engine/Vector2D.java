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
    public Vector2D(Vector2i v){
        this.x = v.col;
        this.y = v.row;
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
    
    public float magnitude(){
        return (float) Math.sqrt(x*x+y*y);
    }
    public Vector2D normalized(){
        float mag = magnitude();
        return new Vector2D(x/mag, y/mag);
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
    public static Vector2D normalized(Vector2D v){
        float mag = v.magnitude();
        return new Vector2D(v.x/mag, v.y/mag);
    }
    
    public boolean equals(Vector2D v){
        return this.x == v.x && this.y == v.y;
    }
}
