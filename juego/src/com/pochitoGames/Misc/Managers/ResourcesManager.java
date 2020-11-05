/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Managers;

import com.pochitoGames.Misc.Other.ResourceType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PochitoMan
 */
public class ResourcesManager {
    
    private static int wood = 30, stone = 30, gold = 30;
    private static Map<Integer, Map<ResourceType, Integer>> resources = new HashMap<>();

    public static int getWood() {
        return wood;
    }

    public static void setWood(int aWood) {
        wood = aWood;
    }

    public static int getStone() {
        return stone;
    }

    public static void setStone(int aStone) {
        stone = aStone;
    }

    public static int getGold() {
        return gold;
    }

    public static void setGold(int aGold) {
        gold = aGold;
    }
    
    
    public static void addGold(int amount){
        gold += amount;
    }
    
    public static void addWood(int amount){
        wood += amount;
    }
    
    public static void addStone(int amount){
        stone += amount;
    }
    
    public static void takeGold(int amount){
        gold -= amount;
    }
    
    
    public static void takeWood(int amount){
        wood -= amount;
    }


    public static void takeStone(int amount){
        stone -= amount;
    }
}