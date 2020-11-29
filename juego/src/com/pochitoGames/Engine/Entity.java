/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author PochitoMan
 */

//Una entidad es un objeto del juego. Cualquier cosa que se te ocurra del juego será una entidad, incluso un texto.
public class Entity {
    
    //EL comportsmiento de la entidad se define a base de componentes.
    //Solo se puede tener un componente de un tipoo en conncreto por entidad, por lo que los metemos en un hashmap en el que la clave es la clase del componente.
    HashMap<Class<? extends Component>, Component> components;
        
    //Las entidades pueden tener una entidad Padre. Esto significa que heredan todo el movimiento de su padre, este padre del suyo, y así sucesivamente.
    //Si no tiene padre, será null.
    Entity parent;
    
    //Igual que tiene una referencia al padre, tiene una referencia a los hijos. 
    //Una entidad puede tender varios hijos, pèro solo un padre, obviamente.
    List<Entity> children;
    
    //Un id único que de momento no sirve para nada
    public final int id;
    
                
    public Entity(Entity parent, int id, Component... c){
        this.id = id;
        components  = new HashMap<>();
        for(Component comp : c){
            components.put(comp.getClass(), comp);
        }
        children = new LinkedList<>();
        this.parent = parent;
        //Le decimos al padre que ahora somos hijo suyo
        if(parent != null) parent.addChild(this);
    }
    
    ////////////MÉTODO IMPORNANTE////////////
    //Sirve para acceder a los componentes de esta entidad. por ejemplo, si queremos coger el sprite de esta entidad desde otro lado sería:
    // Sprite s = entidadEnCuestion.get(Sprite.class);
    public <T> T get(Class<? extends Component> componentClass){
        return (T) components.get(componentClass);
    }
    
    void remove(Component... components){
        for(Component c: components)
            this.components.remove(c.getClass());
    }
	
    void add(Component... components){
        for(Component c: components)
            this.components.put(c.getClass(), c);
    }
    
    public void addChild(Entity child){
        children.add(child);
    }
    
    //A la hora de meter las entidades en los sistemas, se usa este metodo para ver si contiene alguno de los componentes que se le pasa
    //Estos componentes que se le pasa son los excluyentes, es decir que no debería tenerlos, por lo que devolvería false en caso afirmativo.
    boolean containsAny(Class<? extends Component>... componentClasses){
        Set<Class<? extends Component>> s = components.keySet();
        for (Class<? extends Component> c : componentClasses) {
            if (s.contains(c))
                return true;
        }
        return false;
    }
    //Aquí se le pasan los componentes que SI debería tener. Debe contener TODOS los componentes para devolver true.
    //Es ese caso se metería en el sistema.
    boolean contains(Class<? extends Component>... componentClasses){
        return components.keySet().containsAll(Arrays.asList(componentClasses));
    }
    public long getId(){
        return id;
    }
    
    public Entity getParent(){
        return parent;
    }
    
    public List<Entity> getChildren(){
        return Collections.unmodifiableList(children);
    }

    
}
    

