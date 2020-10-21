/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc.Map;

import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Engine.ImageManager;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PochitoMan
 */

//Solo carga un TileMap y lo devuelve. 
//Se llama al método de forma estática.
public class TileMapLoader {
    public static TileMap LoadTileMap(String mapFile, String costFile, String imagePath, int height, int width, int tileW, int tileH, TilesetMode mode){
       String data = ""; 
       try { 
           data = new String(Files.readAllBytes(Paths.get(mapFile)));
       } catch (IOException ex) {
           Logger.getLogger(TileMap.class.getName()).log(Level.SEVERE, null, ex);
       }

       String[] lines = data.split("\n");

       int[][] map = new int[width][height];
       
       int [][] mapInfo = new int[width][height];
       //int mapInfoIndex = 0;
       //Map<Integer, Integer> tileIds = new HashMap<>();

       int row = 0, col = 0;

       //Leer csv con tilemap
       for(int i = 0; i < lines.length; i++){
           String num = "";
            for(int j = 0; j < lines[i].length(); j++){
                char c = lines[i].charAt(j);
                if(c != ',')
                    num+= c;
                else{
                    int n = Integer.parseInt(num);
                    //if(!tileIds.containsKey(n))
                        //tileIds.put(n, mapInfoIndex++);
                    mapInfo[col][row] = n;
                    map[col][row] = n;
                    num = "";
                    col++;
                }
            }
            int n = Integer.parseInt(num.trim());
            //if(tileIds.containsKey(n))
                //tileIds.put(n, mapInfoIndex++);
            mapInfo[col][row] = n;      
            map[col][row] = n;
            col = 0;
            row++;
       }
       
       Map<Integer, Integer> walkCost = new HashMap<>();
       
       //leer csv con costes
       try { 
           data = new String(Files.readAllBytes(Paths.get(costFile)));
       } catch (IOException ex) {
           Logger.getLogger(TileMap.class.getName()).log(Level.SEVERE, null, ex);
       }
       lines = data.split("\n");
       for(int i = 0; i < lines.length; i++){
            String idx = "";
            String cost = "";
            String line = lines[i];
            int charIdx = 0;
            while(line.charAt(charIdx) != ','){
                idx += line.charAt(charIdx);
                charIdx++;
            }
            charIdx++;
            while(charIdx < line.length()-1){
                cost += line.charAt(charIdx);
                charIdx++;
            }
            walkCost.put(Integer.parseInt(idx), Integer.parseInt(cost));
       }

       BufferedImage image = ImageManager.getImage(imagePath);
       
       TileMap tileMap = new TileMap(image, map, tileW, tileH, image.getWidth() / tileW, image.getHeight() / tileH, mode);
       
       MapInfo.getInstance().setMap(mapInfo);
       MapInfo.getInstance().setWalkCost(walkCost);
       MapInfo.getInstance().setActiveTileMap(tileMap);
       
       return tileMap;

   }
}
