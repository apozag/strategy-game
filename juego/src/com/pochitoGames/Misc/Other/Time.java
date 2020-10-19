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
//Una chapuza porque me da conflicto la clase System de Engine con el System de Java
public class Time {
    public static long getTicks(){
        return System.currentTimeMillis();
    }
}
