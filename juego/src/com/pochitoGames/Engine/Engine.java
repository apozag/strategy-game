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
import com.pochitoGames.Systems.ConstructorSystem;
import com.pochitoGames.Systems.TextSystem;
import com.pochitoGames.Systems.TileMapSystem;
import com.pochitoGames.Systems.TileSelectorSystem;

/**
 * @author PochitoMan
 */

//Engine se encarga del bucle principal y poco más.
public class Engine {

    //Todos las entidades están en ecs. 
    //Es donde está la chicha del juego.
    ECS ecs = new ECS();

    Window window;

    //Pondremos este booleano a false cuando haya que acabar el juego (se cierra sola al pulsar la X de la ventana de todas formas).
    public static boolean running = true;
    private String a;

    final int SCR_HEIGHT = 768, SCR_WIDTH = 1024;

    //dt es el paso del tiempo. Cuanto más grande, más "tiempo" pasará entre frames y más rápido irá el juego.
    double dt = 0.1;

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
        ecs.addSystems(new TileMapSystem(), new SpriteSystem(), new WorkerSystem(), new TextSystem(), new TileSelectorSystem(), new ConstructorSystem());

        Entity gear = ecs.createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\player.png",
                        new Animation(8, 100, 250, 500, 0, 0),
                        new Animation(8, 100, 250, 500, 0, 500),
                        new Animation(14, 50, 250, 500, 0, 1000),
                        new Animation(14, 50, 250, 500, 0, 1500)),
                new Position(new Vector2D(0, 0)),
                new Soldier(new Human(100,"Sol",10,10), TypeSoldier.SWORD_MAN),
                new Builder(new Vector2D(500, 300)));

        ecs.createEntity(null,
                new Position(new Vector2D(100, 200)),
                new Text("Vamoooooos loco"));

        Entity tilemap = ecs.createEntity(null,
                new Sprite(),
                new Position(new Vector2D(0, 0)),
                TileMapLoader.LoadTileMap("src\\com\\pochitoGames\\Resources\\TileMaps\\iso_2.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\Terreno piedra.png" , 30, 30, 64, 32, TilesetMode.ISOMETRIC));

        ecs.createEntity(tilemap,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\selected_tile.png"),
                new Position(new Vector2D(0, 0)),
                new TileSelector(tilemap.get(TileMap.class)),
                new Text("")
        );
        
    }

    public void mainLoop() throws InterruptedException {
        while (running) {
            //Tenemos una cámara (clase estática) que hay que updatear.
            Camera.getInstance().update(dt);
            //al updatear ecs, se updatean los sistemas y, por tanto, las entidades.
            ecs.update(dt);
            //Esto hace que se pinten todas las imágenes que han llamado a render() durante esta iteración (se pintan todas a la vez).
            //Tambien el texto
            Renderer.getInstance().repaint();

            //Al acabar la iteración, se limpian los eventos de ratón, teclado, etc, porque sólo vvalen para una vez.
            EventManager.getInstance().clearEvents();
            //Tenemos que esperar un rato (1000/30 == 30FPS) para que no se quede pillado en un bucle infinito.
            Thread.sleep(1000 / 30);
        }
    }

    public void clear() {

    }
}
