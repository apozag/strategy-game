/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Soldier;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.Visual.Text;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ComponentTypes.TypeSoldier;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.TileMapLoader;
import com.pochitoGames.Misc.Map.TilesetMode;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Systems.Visual.SpriteSystem;
import com.pochitoGames.Systems.People.WorkerSystem;
import com.pochitoGames.Systems.Buildings.BuildingGeneratorSystem;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;
import com.pochitoGames.Systems.People.BuilderSystem;

import com.pochitoGames.Systems.Visual.TextSystem;
import com.pochitoGames.Systems.Visual.TileMapSystem;
import com.pochitoGames.Systems.GameLogic.TileSelectorSystem;
import java.awt.Color;

/**
 * @author PochitoMan
 */

//Engine se encarga del bucle principal y poco más.
public class Engine {

    Window window;

    //Pondremos este booleano a false cuando haya que acabar el juego (se cierra sola al pulsar la X de la ventana de todas formas).
    public static boolean running = true;
    //private String a;

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
        else a = "src\\com\\pochitoGames\\Resources\\TileMaps\\tileset_2.png";

*/
        window = new Window(SCR_WIDTH, SCR_HEIGHT);
        Camera.getInstance().setScreenSize(SCR_WIDTH, SCR_WIDTH);

        ECS.getInstance().addSystems(new TileMapSystem(), new SpriteSystem(), new WorkerSystem(), new TextSystem(), new TileSelectorSystem(), new BuilderSystem(), new BuildingGeneratorSystem(), new PathFindingSystem());

        Entity tilemap = ECS.getInstance().createEntity(null,
            new Sprite(),
            new Position(new Vector2D(0, 0)),
            TileMapLoader.LoadTileMap("src\\com\\pochitoGames\\Resources\\TileMaps\\iso_2.csv","src\\com\\pochitoGames\\Resources\\TileMaps\\cost.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\tileSet.png" , 30, 30, 64, 32, TilesetMode.ISOMETRIC));

        
        ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png",
                        new Vector2D(0.5f, 1.0f),
                        true),
                new Position(IsometricTransformations.isoToCartesian(new Vector2i(0, 0))),
                new Soldier(TypeSoldier.SWORDSMAN),
                new Human(100,"Sol",10,10, TypeHuman.DEMON),
                new Builder(),
                new PathFinding(new Vector2i(0, 0)));
        
        ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png",
                        new Vector2D(0.5f, 1.0f),
                        true),
                new Position(IsometricTransformations.isoToCartesian(new Vector2i(10, 10))),
                new Human(100,"Sol",10,10, TypeHuman.BARBARIAN),
                new Builder(),
                new PathFinding(new Vector2i(10, 10)));
/*
        ECS.getInstance().createEntity(null,
            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\character.png", new Vector2D(0.5f, 1.0f),true),
            new Position(new Vector2D(500, 500)),
            new Human(100, "WORKER", 10, 10),
            new Builder()
        );

        */

        ECS.getInstance().createEntity(null,
                new Position(new Vector2D(100, 200)),
                new Text("Vamoooooos loco", Color.red));

        
        ECS.getInstance().createEntity(tilemap,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\selected_tile.png", new Vector2D(0, 0.5f), true),
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
