 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import com.pochitoGames.Components.UI.BuildingPicker;
import com.pochitoGames.Components.UI.PeopleGenerator;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.ResourceText;
import com.pochitoGames.Components.UI.StoneGenerator;
import com.pochitoGames.Components.UI.TreeGenerator;
import com.pochitoGames.Components.Visual.Sprite;
import com.pochitoGames.Components.Visual.Text;
import com.pochitoGames.Components.Visual.TileMap;
import com.pochitoGames.Components.UI.UIButton;
import com.pochitoGames.Misc.ComponentTypes.TypeBuilding;
import com.pochitoGames.Misc.ComponentTypes.TypeHuman;
import com.pochitoGames.Misc.ComponentTypes.TypeRole;
import com.pochitoGames.Misc.Managers.BuildingManager;
import com.pochitoGames.Misc.Managers.GameInfoManager;
import com.pochitoGames.Misc.Managers.PeopleManager;
import com.pochitoGames.Misc.Map.TileMapLoader;
import com.pochitoGames.Misc.Map.TilesetMode;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Misc.Other.Vector2i;
import com.pochitoGames.Systems.Buildings.QuarrySystem;
import com.pochitoGames.Systems.People.MinerSystem;
import com.pochitoGames.Systems.Visual.SpriteSystem;
import com.pochitoGames.Systems.People.WorkerSystem;
import com.pochitoGames.Systems.Buildings.BuildingGeneratorSystem;
import com.pochitoGames.Systems.Buildings.BuildingSystem;
import com.pochitoGames.Systems.Buildings.GoldFoundrySystem;
import com.pochitoGames.Systems.Buildings.LumberjackHutSystem;
import com.pochitoGames.Systems.Buildings.RefinerySystem;
import com.pochitoGames.Systems.Buildings.SawmillSystem;
import com.pochitoGames.Systems.Buildings.WarehouseSystem;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;
import com.pochitoGames.Systems.People.BuilderSystem;
import com.pochitoGames.Systems.Visual.TextSystem;
import com.pochitoGames.Systems.Visual.TileMapSystem;
import com.pochitoGames.Systems.GameLogic.TileSelectorSystem;
import com.pochitoGames.Systems.Other.TreeSystem;
import com.pochitoGames.Systems.People.LumberJackSystem;
import com.pochitoGames.Systems.UI.BuildingPickerSystem;
import com.pochitoGames.Systems.UI.MouseListenerSystem;
import com.pochitoGames.Systems.UI.PeopleGeneratorSystem;
import com.pochitoGames.Systems.UI.ResourceTextSystem;
import com.pochitoGames.Systems.UI.StoneGeneratorSystem;
import com.pochitoGames.Systems.UI.TreeGeneratorSystem;
import com.pochitoGames.Systems.UI.UIButtonSystem;
import com.pochitoGames.Systems.Visual.SeeThroughSystem;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;

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
    double dt = 5.0 / FPS;

    public Engine() {
    }

    //Creamos las entidades y les metemos los componentes a través de createEntity() de ECS
    public void init() {

        //Idioma
        LanguageManager.getInstance().loadLanguage(Language.SPANISH);
        
        //Crear ventana
        window = new Window(SCR_WIDTH, SCR_HEIGHT);
        Camera.getInstance().setScreenSize(SCR_WIDTH, SCR_WIDTH);

        // Añadir sistemas
        ECS.getInstance().addSystems(new TileMapSystem(), new SpriteSystem(), new SeeThroughSystem(), new WorkerSystem(),
                new TextSystem(), new TileSelectorSystem(), new BuilderSystem(), new LumberJackSystem(),
                new BuildingGeneratorSystem(), new PathFindingSystem(), new UIButtonSystem(),
                new BuildingPickerSystem(), new PeopleGeneratorSystem(), new TreeGeneratorSystem(), new StoneGeneratorSystem(),
                new BuildingSystem(), new ResourceTextSystem(), new QuarrySystem(), new RefinerySystem(), new LumberjackHutSystem(), new SawmillSystem(),
                new MinerSystem(),  new MouseListenerSystem(), new TreeSystem(), new GoldFoundrySystem(), new WarehouseSystem());

        GameInfoManager.getInstance().setPlayerType(TypeHuman.BARBARIAN);
        
        // Crear Mapa y selector de tile
        Entity tilemap = ECS.getInstance().createEntity(null,
                new Sprite(),
                new Position(new Vector2D(0, 0)),
                TileMapLoader.LoadTileMap("src\\com\\pochitoGames\\Resources\\TileMaps\\iso_2.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\cost.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\tileSet.png", 30, 30, 64, 32, TilesetMode.ISOMETRIC));
        ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\selected_tile.png", new Vector2D(0, 0.5f), true,1.0f),
                new Position(new Vector2D(0, 0)),
                new TileSelector(tilemap.get(TileMap.class)),
                new MouseListener(0)
        );

         
        // Personajes iniciales
        PeopleManager.getInstance().createCharacter(GameInfoManager.getInstance().getPlayerType(), TypeRole.BUILDER, new Vector2i(2, 2));
        PeopleManager.getInstance().createCharacter(GameInfoManager.getInstance().getPlayerType(), TypeRole.WORKER, new Vector2i(1, 1));

        // Edificio inicial (Castillo)
        BuildingManager.getInstance().build(GameInfoManager.getInstance().getPlayerType(), TypeBuilding.CASTLE, new Vector2i(4, 4));      

        ///////////////////////////////
        ///////    INTERFAZ     ///////
        ///////////////////////////////

        Entity uiPanel = ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_panel.png", new Vector2D(0, 0), false, 1.0f),
                new Position(new Vector2D(50, window.getHeight()-200), true), 
                new MouseListener(1)
        );

//////////////// EDIFICIOS ///////////////////////

        //Boton crear ESCUELA
        Entity button1 = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(10, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.SCHOOL),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(button1, 
                new Position(new Vector2D(20, 25), true),
                new Text("SCh", Color.BLACK, true)
            );

        // Boton crear CANTINA
        Entity button2 = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(60, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.CANTEEN),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(button2, 
                new Position(new Vector2D(25, 25), true),
                new Text("F", Color.BLACK, true)
            );

        //Boton crear ASERRADERO
        Entity button3 = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(110, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.SAWMILL),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(button3, 
                new Position(new Vector2D(25, 25), true),
                new Text("S", Color.BLACK, true)
            );

        // Botón crear CANTERA 
        Entity buttonQuarry = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(160, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.QUARRY),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonQuarry, 
                new Position(new Vector2D(25, 25), true),
                new Text("Q", Color.BLACK, true)
            );
        
        // Botón crear Refinería 
        Entity buttonRef = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(210, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.REFINERY),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonRef, 
                new Position(new Vector2D(25, 25), true),
                new Text("R", Color.BLACK, true)
            );

        //Botón crear SUELO
        Entity button4 = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(260, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.FLOOR),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(button4, 
                new Position(new Vector2D(25, 25), true),
                new Text("F", Color.BLACK, true)
            );
         //Botón crear CABAÑA LEÑADOR
        Entity buttonH = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(310, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.LUMBERJACK_HUT),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonH, 
                new Position(new Vector2D(25, 25), true),
                new Text("LH", Color.BLACK, true)
            );
        
        //Botón crear FUNDICIÓN ORO
        Entity buttonG = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(360, 10), true),
                new UIButton(),
                new BuildingPicker(TypeBuilding.GOLD_FOUNDRY),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonG, 
                new Position(new Vector2D(25, 25), true),
                new Text("G", Color.BLACK, true)
            );

///////////////// PERSONAJES //////////////////////        
        
        // Boton crear builder
        Entity buttonB = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(10, 100), true),
                new UIButton(),
                new PeopleGenerator(TypeHuman.BARBARIAN, TypeRole.BUILDER),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonB, 
                new Position(new Vector2D(25, 25), true),
                new Text("B", Color.BLACK, true)
            );

        // Boton crear Worker
        Entity buttonW = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(60, 100), true),
                new UIButton(),
                new PeopleGenerator(TypeHuman.BARBARIAN, TypeRole.WORKER),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonW, 
                new Position(new Vector2D(25, 25), true),
                new Text("W", Color.BLACK, true)
            );
        // Boton crear MINERO  
        Entity buttonMinero = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(110, 100), true),
                new UIButton(),
                new PeopleGenerator(TypeHuman.BARBARIAN,TypeRole.MINER),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonMinero, 
                new Position(new Vector2D(25, 25), true),
                new Text("M", Color.BLACK, true)
            );
        
        // Boton crear MINERO  
        Entity buttonLeñador = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false,1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(160, 100), true),
                new UIButton(),
                new PeopleGenerator(TypeHuman.BARBARIAN,TypeRole.LUMBERJACK),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonLeñador, 
                new Position(new Vector2D(25, 25), true),
                new Text("L", Color.BLACK, true)
            );
        
////////////////////////// OTROS //////////////////////
        
        // Boton crear Arbol
        Entity buttonT = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(360, 100), true),
                new UIButton(),
                new TreeGenerator(),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonT, 
                new Position(new Vector2D(25, 25), true),
                new Text("T", Color.BLACK, true)
            );
        // Boton crear Pierda
        Entity buttonP = ECS.getInstance().createEntity(uiPanel,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_button.png", new Vector2D(0, 0), false, 1.0f,
                        new Animation(1, 100, 50, 50, 0, 0),
                        new Animation(1, 100, 50, 50, 50, 0)),
                new Position(new Vector2D(410, 100), true),
                new UIButton(),
                new StoneGenerator(),
                new MouseListener(2)
        );
        ECS.getInstance().createEntity(buttonP, 
                new Position(new Vector2D(25, 25), true),
                new Text("ST", Color.BLACK, true)
            );
        
        // Recursos y tal
        ECS.getInstance().createEntity(null,
                new Text("", Color.white),
                new Position(new Vector2D(500, 50), true),
                new ResourceText(ResourceType.GOLD)
        );

        ECS.getInstance().createEntity(null,
                new Text("", Color.white),
                new Position(new Vector2D(650, 50), true),
                new ResourceText(ResourceType.STONE)
        );
        ECS.getInstance().createEntity(null,
                new Text("", Color.white),
                new Position(new Vector2D(800, 50), true),
                new ResourceText(ResourceType.WOOD)
        );


    }

    public void mainLoop() throws InterruptedException {
        long elapsed = 0;
        while (running) {
            long lastTime = java.lang.System.currentTimeMillis();

            //Tenemos una cámara (clase estática) que hay que updatear.
            Camera.getInstance().update(dt);

            //al updatear ecs, se updatean los sistemas y, por tanto, las entidades.
            ECS.getInstance().update(dt);

            //Esto hace que se pinten todas las imágenes y texto que han llamado a render() durante esta iteración (se pintan todas a la vez).
            Renderer.getInstance().repaint();

            //Al acabar la iteración, se limpian los eventos de ratón, teclado, etc, porque sólo valen para una vez.
            EventManager.getInstance().clearEvents();

            //Tenemos que esperar un rato (1000/30 == 30FPS) para que no se quede pillado en un bucle infinito.
            elapsed = java.lang.System.currentTimeMillis() - lastTime;

            long wait = Math.max((1000 / FPS) - elapsed, 5);
            Thread.sleep(wait);
        }
    }

    public void clear() {

    }
}
