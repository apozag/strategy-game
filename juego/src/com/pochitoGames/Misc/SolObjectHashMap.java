package com.pochitoGames.Misc;

import java.util.HashMap;

public class SolObjectHashMap {

    //Aqui añado al hashmap los distintos objetos de los soldados,
    // son como bonificaciones de ataque y defensa, estan todos en la clase SoldierObjects

    private static HashMap<SoldierObjects, Integer> map = new HashMap<SoldierObjects, Integer>();
    static {
        map.put(SoldierObjects.SWORD,2);
        map.put(SoldierObjects.BOW,1);
        map.put(SoldierObjects.ARMOR,5);
        map.put(SoldierObjects.HELMET,3);
    }

    public HashMap<SoldierObjects, Integer> getMap(){
        return map;
    }

}
