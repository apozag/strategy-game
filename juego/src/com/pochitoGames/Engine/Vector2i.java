/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.lang.Math;

/**
 * @author PochitoMan
 */
public class Vector2i {
    public int col, row;

    public Vector2i(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
    public Vector2i(Vector2i v){
        this.col = v.col;
        this.row = v.row;
    }

    public boolean equals(Vector2i v) {

        return this.col == v.col && this.row == v.row;
    }

    public int distance(Vector2i v) {
        return Math.abs(v.col - col) + Math.abs(v.row - row);
    }

    public int manhattan() {
        return Math.abs(col) + Math.abs(row);
    }

    public static Vector2i add(Vector2i v1, Vector2i v2) {
        return new Vector2i(v1.col + v2.col, v1.row + v2.row);
    }
    
    public static Vector2i sub(Vector2i v1, Vector2i v2) {
        return new Vector2i(v1.col - v2.col, v1.row - v2.row);
    }

    public static Vector2i mult(Vector2i v, int i) {
        return new Vector2i(v.col * i, v.row * i);
    }

    public static boolean isBigger (Vector2i v1, Vector2i v2){
        //if (Math.sqrt()) 
        return true;
    }


}
