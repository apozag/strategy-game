/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author PochitoMan
 */

//Siempre hay que cargar las nuevas imángenes a traves de ImageManager.
public class ImageManager {
    
    //Contiene un hashmap en el que la clave es la ruta de la imagen.
    //Así, nos aseguramos de que no cargamos dos veces la misma imagen y la reutilizamos.
    static private Map<String, BufferedImage> images = new HashMap<>();

    
    //Con este método se cargan las imágenes o se sacan del hashmap según ya las hayamos pedido o no
    public static BufferedImage getImage(String path){
        if(path.equals(""))
            return null;
        BufferedImage img = images.get(path);
        if(img == null){
            try {
                img = ImageIO.read(new File(path));
            } catch (IOException ex) {
                Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            images.put(path, img);
        }
        return img;
    }
}
