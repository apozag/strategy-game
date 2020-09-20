/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Misc;

import com.pochitoGames.Components.TileMap;
import com.pochitoGames.Engine.ImageManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PochitoMan
 */

//Solo carga un TIleMap y lo devuelve. 
//Se llama al método de forma estática.
public class TileMapLoader {
    public static TileMap LoadTileMap(String file, String imagePath, int height, int width, int tileW, int tileH, TilesetMode mode){
       String data = ""; 
       try { 
           data = new String(Files.readAllBytes(Paths.get(file)));
       } catch (IOException ex) {
           Logger.getLogger(TileMap.class.getName()).log(Level.SEVERE, null, ex);
       }

       String[] lines = data.split("\n");

       int[][] map = new int[width][height];

       int row = 0, col = 0;

       for(int i = 0; i < lines.length; i++){
           String num = "";
           for(int j = 0; j < lines[i].length(); j++){
               char c = lines[i].charAt(j);
               if(c != ',')
                   num+= c;
               else{
                   map[col][row] = Integer.parseInt(num);
                   num = "";
                   col++;
               }
           }
           map[col][row] = Integer.parseInt(num.trim());
           col = 0;
           row++;
       }

       BufferedImage image = ImageManager.getImage(imagePath);

       return new TileMap(image, map, tileW, tileH, image.getWidth() / tileW, image.getHeight() / tileH, mode);

   }
}
