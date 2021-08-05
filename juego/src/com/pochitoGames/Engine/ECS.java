/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author PochitoMan
 */

//En ecs están todas las entidades y sistemas.
//Es donde está toda la chicha del motor y la magia negra
public class ECS {
    
    //Aquí hay muchas listas. Las importantes son entitiesToDelete, entitiesToCreate y systems. 
    
    //Al mandar destruir una entidad, pasa a esta lista para ver en qué sistemas se pueden meter
    ArrayList<Entity> entitiesToDelete;
    //Al crear una entidad, se meten en esta lista para ver de qué sistemas se tienen que "dar de baja"
    ArrayList<Entity> entitiesToCreate;
    //También podemos borrar y crear nuevos componentes durante el juego, pero no nos va a hacer falta de moemento
    ArrayList<ECContainer> componentsToDelete;
    ArrayList<ECContainer> componentsToCreate;
    
    //en esta lista están las entidades que ya existían pero que han sido modificadas (se le han quitado o metido componentes durante el juego)
    //La verdad es que de momento no va a hacer falta.
    private Set<Entity> eSystemValidate;
    
    //Una lista con todos los sistemas del juego.(Ver clase System para ver dse que va el tema)
    ArrayList<System> systems;
    
    int nextEntityID = 0;
    
    private static ECS instance;
    
    private ECS(){
        entitiesToDelete= new ArrayList<>();
        entitiesToCreate= new ArrayList<>();
        componentsToDelete= new ArrayList<>();
        componentsToCreate= new ArrayList<>();
        eSystemValidate = new HashSet<>();    
    
        systems = new ArrayList<>();
    }
    
    public static ECS getInstance(){
        if(instance == null){
            instance = new ECS();
        }
        return instance;
    }
    
    public void addSystems(System... systems){
        for(System s: systems){
                s.setECS(this);
                this.systems.add(s);
        }
    }
    
    public void addEntity(Entity e){
        entitiesToCreate.add(e);
    }
    
    //Crea una entidad con los componentes de los parámetros
    public Entity createEntity(Entity parent, Component... components){
        //Creamos la instancia
        Entity e = new Entity(parent, nextEntityID++, components);
        
        //Cada componente tiene una referencia a la entidad a la que pertenece
        for(Component c : components){
            c.setEntity(e);
        }
        
        //Lo añadimoos a la lista de entidades pendientes por meter en los sistemas
        entitiesToCreate.add(e);
        
        return e;
    }
    

    public void addComponent(Entity e, Component... components){
            componentsToCreate.add(new ECContainer(e, components));
    }

    public void removeComponent(Entity e, Component... components){
            componentsToDelete.add(new ECContainer(e, components));
    }

    public void removeEntity(Entity e){
            entitiesToDelete.add(e);
    }
       
    public void update(double dt){
        //elimina los componentes que estén pendientes (no se usa de momento)
        for(ECContainer ec: componentsToDelete){
                ec.entity.remove(ec.components);
                eSystemValidate.add(ec.entity);
        }
        componentsToDelete.clear();

        //Añade los componentes que estén pendientes (no se usa de mopmento)
        for(ECContainer ec: componentsToCreate){
                ec.entity.add(ec.components);
                for(Component c : ec.components){
                    c.setEntity(ec.entity);
                }
                eSystemValidate.add(ec.entity);
        }
        componentsToCreate.clear();
        

        //Aquí es donde se meten las entidades creadas en los sistemas correspondientes
        for(System s: systems){
                //para cada entidad que acabemos de crear, miramos si coincide con los componentes que requiere el sistema.
                //Si es el caso, se mete en el sistema. 
                //Todo eso se hace en el método consider de System
                for(Entity e: entitiesToCreate)
                        s.consider(e);
                //Esto es para entidades que han sido modificadas durante el juego (añadir o quitar componentes) 
                //No es impornante de momento
                for(Entity e: eSystemValidate)
                        s.validate(e);
                //Las entidades pendientes de eliminar ven si están en este sistema (s) y se eliminan de el.
                for(Entity e: entitiesToDelete)
                        s.remove(e);
        }
        
        for(Entity e: entitiesToDelete){
            if(e.parent != null)
                e.parent.children.remove(e);
        }
        //Limpiamos todas estas listas porque ya las hemos procesado.
        entitiesToCreate.clear();
        eSystemValidate.clear();
        componentsToDelete.clear();
        
        //updateamos los sistemas
        for(System s: systems)
                s.update(dt);
        
     }

}
