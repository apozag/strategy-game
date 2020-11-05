/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import com.pochitoGames.Components.Buildings.Warehouse;
import com.pochitoGames.Components.Buildings.Building;
import com.pochitoGames.Components.UI.BuildingPicker;
import com.pochitoGames.Components.GameLogic.PathFinding;
import com.pochitoGames.Components.UI.PeopleGenerator;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Components.People.Builder;
import com.pochitoGames.Components.People.Human;
import com.pochitoGames.Components.People.Soldier;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.Visual.Text;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Components.UI.UIButton;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ComponentTypes.TypeSoldier;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.TileMapLoader;
import com.pochitoGames.Misc.Map.TilesetMode;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Systems.Visual.SpriteSystem;
import com.pochitoGames.Systems.People.WorkerSystem;
import com.pochitoGames.Systems.Buildings.BuildingGeneratorSystem;
import com.pochitoGames.Systems.Buildings.BuildingSystem;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;
import com.pochitoGames.Systems.People.BuilderSystem;

import com.pochitoGames.Systems.Visual.TextSystem;
import com.pochitoGames.Systems.Visual.TileMapSystem;
import com.pochitoGames.Systems.GameLogic.TileSelectorSystem;
import com.pochitoGames.Systems.UI.BuildingPickerSystem;
import com.pochitoGames.Systems.UI.PeopleGeneratorSystem;
import com.pochitoGames.Systems.UI.UIButtonSystem;
import java.awt.Color;
import java.util.HashMap;

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

        window = new Window(SCR_WIDTH, SCR_HEIGHT);
        Camera.getInstance().setScreenSize(SCR_WIDTH, SCR_WIDTH);

        ECS.getInstance().addSystems(new TileMapSystem(), new SpriteSystem(), new WorkerSystem(), 
                new TextSystem(), new TileSelectorSystem(), new BuilderSystem(), 
                new BuildingGeneratorSystem(), new PathFindingSystem(), new UIButtonSystem(),
                new BuildingPickerSystem(), new PeopleGeneratorSystem(),
                new BuildingSystem());

        Entity tilemap = ECS.getInstance().createEntity(null,
            new Sprite(),
            new Position(new Vector2D(0, 0)),
            TileMapLoader.LoadTileMap("src\\com\\pochitoGames\\Resources\\TileMaps\\iso_2.csv","src\\com\\pochitoGames\\Resources\\TileMaps\\cost.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\tileSet.png" , 30, 30, 64, 32, TilesetMode.ISOMETRIC));
        
        PeopleManager.getInstance().createCharacter(TypeHuman.BARBARIAN, new Vector2i(10, 10));
        PeopleManager.getInstance().createCharacter(TypeHuman.BARBARIAN, new Vector2i(1, 1));
        
        BuildingManager.getInstance().build(TypeBuilding.CASTLE, new Vector2i(4, 4));
        
        ECS.getInstance().createEntity(tilemap,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\selected_tile.png", new Vector2D(0, 0.5f), true),
                new Position(new Vector2D(0, 0)),
                new TileSelector(tilemap.get(TileMap.class))
        );

        
        ///////////////////////////////
        ///////    INTERFAZ     ///////
        ///////////////////////////////
        
        Entity uiPanel = ECS.getInstance().createEntity(null,
            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_panel.png", new Vector2D(0, 0), false),
            new Position(new Vector2D(50, 50), true)
        );
        
        Entity button1 = ECS.getInstance().createEntity(uiPanel,
            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 
                    new Animation(1, 100, 50, 50, 0, 0),
                    new Animation(1, 100, 50, 50, 50, 0)),
            new Position(new Vector2D(70, 70), true),
            new UIButton(),
            new BuildingPicker(TypeBuilding.SCHOOL)
        );
        ECS.getInstance().createEntity(button1,
            new Text("1", Color.black),
            new Position(new Vector2D(90, 100), true)
        );  
        
        Entity button2 = ECS.getInstance().createEntity(uiPanel,
            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 
                    new Animation(1, 100, 50, 50, 0, 0),
                    new Animation(1, 100, 50, 50, 50, 0)),
            new Position(new Vector2D(140, 70), true),
            new UIButton(),
            new BuildingPicker(TypeBuilding.CANTEEN)
        );
        ECS.getInstance().createEntity(button2,
            new Text("2", Color.black),
            new Position(new Vector2D(160, 100), true)
        );
        
        Entity button3 = ECS.getInstance().createEntity(uiPanel,
            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 
                    new Animation(1, 100, 50, 50, 0, 0),
                    new Animation(1, 100, 50, 50, 50, 0)),
            new Position(new Vector2D(190, 70), true),
            new UIButton(),
            new BuildingPicker(TypeBuilding.SAWMILL)
        );
        ECS.getInstance().createEntity(button3,
            new Text("3", Color.black),
            new Position(new Vector2D(210, 100), true)
        );

        Entity button4 = ECS.getInstance().createEntity(uiPanel,
           new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 
                   new Animation(1, 100, 50, 50, 0, 0),
                   new Animation(1, 100, 50, 50, 50, 0)),
           new Position(new Vector2D(250, 70), true),
           new UIButton(),
           new BuildingPicker(TypeBuilding.FLOOR)
        );
        ECS.getInstance().createEntity(button4,
            new Text("Floor", Color.black),
            new Position(new Vector2D(250, 100), true)
        );
        
        Entity buttonP = ECS.getInstance().createEntity(uiPanel,
            new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 
                    new Animation(1, 100, 50, 50, 0, 0),
                    new Animation(1, 100, 50, 50, 50, 0)),
            new Position(new Vector2D(70, 150), true),
            new UIButton(),
            new PeopleGenerator(TypeHuman.BARBARIAN)
        );
        ECS.getInstance().createEntity(buttonP,
            new Text("P", Color.black),
            new Position(new Vector2D(90, 170), true)
        );
        
        // Recursos y tal
        
        ECS.getInstance().createEntity(null,
            new Text("Gold", Color.white),
            new Position(new Vector2D(500, 50), true)
        );
        
        
    }

    public void mainLoop() throws InterruptedException {
        while (running) {
            
            //EventManager.getInstance().handleEvents();
            
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
