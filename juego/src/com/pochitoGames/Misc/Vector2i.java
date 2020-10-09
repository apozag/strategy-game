/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc;

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
}
