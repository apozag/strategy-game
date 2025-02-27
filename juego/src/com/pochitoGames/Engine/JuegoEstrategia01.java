
package com.pochitoGames.Engine;

import java.util.logging.Level;
import java.util.logging.Logger;

//Esta es la clase y funcion principal.
public class JuegoEstrategia01 {

    public static void main(String[] args) {
        //Necesitamos crear un Engine y lo iniciamos
        Engine engine = Engine.getInstance();
        engine.init();
        try {
            //Llamamos a mainLoop que se empezará el bucle principal del juego
            engine.mainLoop();
        } catch (InterruptedException ex) {
            Logger.getLogger(JuegoEstrategia01.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
