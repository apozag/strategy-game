/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Other;

/**
 *
 * @author PochitoMan
 */
public class Vector2i {
    public int col, row;
    public Vector2i(int col, int row){
        this.col = col; 
        this.row = row;
    }
    public boolean equals(Vector2i v){
        return this.col == v.col && this.row == v.row;
    }
    public int distance(Vector2i v){
        return Math.abs(v.col - col) + Math.abs(v.row - row);
    }
    public int manhattan(){
        return col + row;
    }
    public static Vector2i add(Vector2i v1, Vector2i v2){
        return new Vector2i(v1.col + v2.col, v1.row + v2.row);
    }
}
