 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import com.pochitoGames.Components.UI.BuildingPicker;
import com.pochitoGames.Components.GameLogic.Position;
import com.pochitoGames.Components.GameLogic.TileSelector;
import com.pochitoGames.Components.UI.MouseListener;
import com.pochitoGames.Components.UI.PanelActivator;
import com.pochitoGames.Components.UI.PanelRect;
import com.pochitoGames.Components.UI.PeopleGenerator;
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
import com.pochitoGames.Misc.Map.IsometricTransformations;
import com.pochitoGames.Misc.Map.TileMapLoader;
import com.pochitoGames.Misc.Map.TilesetMode;
import com.pochitoGames.Misc.Other.AlignmentType;
import com.pochitoGames.Misc.Other.Animation;
import com.pochitoGames.Misc.Other.ResourceType;
import com.pochitoGames.Systems.Buildings.*;
import com.pochitoGames.Systems.People.MinerSystem;
import com.pochitoGames.Systems.Visual.SpriteSystem;
import com.pochitoGames.Systems.People.WorkerSystem;
import com.pochitoGames.Systems.GameLogic.PathFindingSystem;
import com.pochitoGames.Systems.People.BuilderSystem;
import com.pochitoGames.Systems.Visual.TextSystem;
import com.pochitoGames.Systems.Visual.TileMapSystem;
import com.pochitoGames.Systems.GameLogic.TileSelectorSystem;
import com.pochitoGames.Systems.Other.BackpackSystem;
import com.pochitoGames.Systems.Other.ThinkingSystem;
import com.pochitoGames.Systems.Other.TreeSystem;
import com.pochitoGames.Systems.People.HumanSystem;
import com.pochitoGames.Systems.People.LumberJackSystem;
import com.pochitoGames.Systems.UI.BuildingPickerSystem;
import com.pochitoGames.Systems.UI.MouseListenerSystem;
import com.pochitoGames.Systems.UI.PanelPickerSystem;
import com.pochitoGames.Systems.UI.PanelRectSystem;
import com.pochitoGames.Systems.UI.PeopleGeneratorSystem;
import com.pochitoGames.Systems.UI.ResourceTextSystem;
import com.pochitoGames.Systems.UI.StoneGeneratorSystem;
import com.pochitoGames.Systems.UI.TreeGeneratorSystem;
import com.pochitoGames.Systems.UI.UIButtonSystem;
import com.pochitoGames.Systems.Visual.SeeThroughSystem;

import java.awt.Color;
import java.util.ArrayList;

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

    private static Engine instance;

    private Engine() {
    }
    
    public static Engine getInstance(){
        if(instance == null)
            instance = new Engine();
        return instance;
    }
    
    public Window getWindow(){return window;}

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
                new BuildingGeneratorSystem(), new PathFindingSystem(), new UIButtonSystem(), new PanelPickerSystem(),
                new BuildingPickerSystem(), new PeopleGeneratorSystem(), new TreeGeneratorSystem(), new StoneGeneratorSystem(),
                new BuildingSystem(), new ResourceTextSystem(), new QuarrySystem(), new RefinerySystem(), new LumberjackHutSystem(), new SawmillSystem(), new SchoolSystem(),
                new MinerSystem(),  new MouseListenerSystem(), new TreeSystem(), new GoldFoundrySystem(), new WarehouseSystem(), new BackpackSystem(), new ThinkingSystem(),
                new PanelRectSystem(), new HumanSystem(), new CanteenSystem(), new PigFarmSystem());

        GameInfoManager.getInstance().setPlayerType(TypeHuman.BARBARIAN);
        
        // Crear Mapa y selector de tile
     Entity compoundTileMap = ECS.getInstance().createEntity(null,
                new Sprite(),
                new Position(new Vector2D(0, 0)),
                TileMapLoader.LoadCompoundTileMap("src\\com\\pochitoGames\\Resources\\TileMaps\\iso_2.csv", 
                        "src\\com\\pochitoGames\\Resources\\TileMaps\\cost.csv", "src\\com\\pochitoGames\\Resources\\TileMaps\\tileSet.png", 
                        120, 120, 64, 32, TilesetMode.ISOMETRIC));
        
        ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\selected_tile.png", new Vector2D(0, 0.5f), true,1.0f),
                new Position(new Vector2D(0, 0)),
                new TileSelector(compoundTileMap.get(TileMap.class)),
                new MouseListener(0)
        );

         
        // Personajes iniciales
        PeopleManager.getInstance().createCharacter(GameInfoManager.getInstance().getPlayerType(), TypeRole.BUILDER, new Vector2i(2, 2));
        PeopleManager.getInstance().createCharacter(GameInfoManager.getInstance().getPlayerType(), TypeRole.WORKER, new Vector2i(1, 1));

        // Edificio inicial (Castillo)
        BuildingManager.getInstance().build(GameInfoManager.getInstance().getPlayerType(), TypeBuilding.CASTLE, new Vector2i(4, 4));    
        
        Camera.getInstance().setPos(IsometricTransformations.isoToCartesian(BuildingManager.getInstance().getNearestBuilding(new Vector2i(0, 0), TypeBuilding.CASTLE).getEntryCell()));

        ///////////////////////////////
        ///////    INTERFAZ     ///////
        ///////////////////////////////
/*
        Entity uiPanel = ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_panel.png", new Vector2D(0, 0), false, 1.0f),
                new Position(new Vector2D(50, window.getHeight()-200), true), 
                new MouseListener(1)
        );
        
        Entity schoolPanel = ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_panel.png", new Vector2D(0, 0), false, 1.0f),
                new Position(new Vector2D(50, window.getHeight()-200), true), 
                new MouseListener(1)
        );
        
        Entity Panel = ECS.getInstance().createEntity(null,
                new Sprite("src\\com\\pochitoGames\\Resources\\Sprites\\ui_panel.png", new Vector2D(0, 0), false, 1.0f),
                new Position(new Vector2D(50, window.getHeight()-200), true), 
                new MouseListener(1)
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
        */
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

        EntityParser.registerComponent("panelrect", PanelRect.class);
        EntityParser.registerComponent("position", Position.class);
        EntityParser.registerComponent("sprite", Sprite.class);
        EntityParser.registerComponent("text", Text.class);
        EntityParser.registerComponent("mouselistener", MouseListener.class);
        EntityParser.registerComponent("uibutton", UIButton.class);
        EntityParser.registerComponent("buildingpicker", BuildingPicker.class);
        EntityParser.registerComponent("peoplegenerator", PeopleGenerator.class);
        EntityParser.registerComponent("panelactivator", PanelActivator.class);
        EntityParser.parseFile("src\\com\\pochitoGames\\Resources\\GameInfo\\uiPanels.xml");
        ECS.getInstance().update(dt);
        UIManager.getInstance().activatePanel("MAIN");
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
            InputManager.getInstance().clearEvents();

            //Tenemos que esperar un rato (1000/30 == 30FPS) para que no se quede pillado en un bucle infinito.
            elapsed = java.lang.System.currentTimeMillis() - lastTime;

            long wait = Math.max((1000 / FPS) - elapsed, 5);
            Thread.sleep(wait);
        }
    }

    public void clear() {

    }
}
