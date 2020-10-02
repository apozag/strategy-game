/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import com.pochitoGames.Components.*;
import com.pochitoGames.Misc.TypeSoldier;
import com.pochitoGames.Systems.SpriteSystem;
import com.pochitoGames.Systems.WorkerSystem;
import com.pochitoGames.Misc.TilesetMode;
import com.pochitoGames.Misc.Animation;
import com.pochitoGames.Misc.TileMapLoader;
import com.pochitoGames.Misc.Time;
import com.pochitoGames.Systems.BuildingGeneratorSystem;
import com.pochitoGames.Systems.ConstructorSystem;
import com.pochitoGames.Systems.TextSystem;
import com.pochitoGames.Systems.TileMapSystem;
import com.pochitoGames.Systems.TileSelectorSystem;
import java.awt.Color;

/**
 * @author PochitoMan
 */

//Engine se encarga del bucle principal y poco más.
public class Engine {

    Window window;

    //Pondremos este booleano a false cuando haya que acabar el juego (se cierra sola al pulsar la X de la ventana de todas formas).
    public static boolean running = true;
    private String a;

    final int SCR_HEIGHT = 768, SCR_WIDTH = 1024;

    int FPS = 60;
    //dt es el paso del tiempo. Cuanto más grande, más "tiempo" pasará entre frames y más rápido irá el juego.
    double dt = 5.0/FPS;

    public Engine() {
    }

    //Creamos las entidades y les metemos los componentes a través de createEntity() de ECS
    public void init() {
/*
        int b = (int) (Math.random() * 2);
        if (b == 1)  a = "src\\com\\pochitoGames\\Resources\\TileMaps\\Terreno flores.png";
        else a = "s rc\\com\\pochitoGames\\Resources\\TileMaps\\tileset_2.png";

*/
        window = new Window(SCR_WIDTH, SCR_HEIGHT);
        Camera.getInstance().setScreenSize(SCR_WIDTH, SCR_WIDTH);
        ECS.getInstance().addSystems(new TileMapSystem(), new SpriteSystem(), new WorkerSystem(), new TextSystem(), new TileSelectorSystem(), new ConstructorSystem(), new BuildingGeneratorSystem());

        Entity gear = ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\player.png",
                        new Vector2D(0.5f, 1.0f),
                        true,
                        new Animation(8, 100, 250, 500, 0, 0),
                        new Animation(8, 100, 250, 500, 0, 500),
                        new Animation(14, 50, 250, 500, 0, 1000),
                        new Animation(14, 50, 250, 500, 0, 1500)),
                new Position(new Vector2D(0, 500)),
                new Soldier(TypeSoldier.SWORD_MAN),
                new Human(100,"Sol",10,10),
                new Builder());

        ECS.getInstance().createEntity(null,
                new Position(new Vector2D(100, 200)),
                new Text("Vamoooooos loco", Color.red));

        Entity tilemap = ECS.getInstance().createEntity(null,
                new Sprite(),
                new Position(new Vector2D(0, 0)),
                TileMapLoader.LoadTileMap("src\\com\\pochitoGames\\Resources\\TileMaps\\iso_2.csv","src\\com\\pochitoGames\\Resources\\TileMaps\\cost.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\Terreno piedra.png" , 120, 120, 64, 32, TilesetMode.ISOMETRIC));

        ECS.getInstance().createEntity(tilemap,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\selected_tile.png", new Vector2D(0, 0), true),
                new Position(new Vector2D(0, 0)),
                new TileSelector(tilemap.get(TileMap.class))
        );
        /*
        ECS.getInstance().createEntity(null, 
                new Position(new Vector2D(50, 50), true), 
                new Text("", Color.white), 
                new FPScounter()
        );
        */
    }

    public void mainLoop() throws InterruptedException {
        while (running) {
            //Tenemos una cámara (clase estática) que hay que updatear.
            Camera.getInstance().update(dt);
            
            //al updatear ecs, se updatean los sistemas y, por tanto, las entidades.
            ECS.getInstance().update(dt);
            
            //Esto hace que se pinten todas las imágenes que han llamado a render() durante esta iteración (se pintan todas a la vez).
            //Tambien el texto
            Renderer.getInstance().repaint();

            //Al acabar la iteración, se limpian los eventos de ratón, teclado, etc, porque sólo valen para una vez.
            EventManager.getInstance().clearEvents();
            
            //Tenemos que esperar un rato (1000/30 == 30FPS) para que no se quede pillado en un bucle infinito.
            Thread.sleep(1000 / FPS);
        }
    }

    public void clear() {

    }
}
