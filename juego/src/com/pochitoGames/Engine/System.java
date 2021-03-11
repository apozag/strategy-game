/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author PochitoMan
 */

//Un sistema es una clase que se encarga de updatear todos los componentes de un tipo en concreto. Por ejemplo, SpriteSystem updatea todos los sprites
//System es una clase abstracta, todos los sistemas han de heredar de System e implementar el método update()

//Un sistema contiene referencias a las entidades que cumplen con los requisitos. 
//A su vez, las entidades pueden ser parte de varios sistemas a la vez (En casi todos los casos)
public abstract class System {
    //En muchas ocasiones, el sistema necesita que la entidad tenga varios componentes de tipos concretos para asegurarse que no va a explotar en algún momento.
    //En include están los componentes que DEBEN contener las entidades para se rparte del sistema
    private Class <? extends Component>[] include;
    //En exlude stán los componentes que NO DEBE contener la entidad para ser parte del sistema.
    private Class <? extends Component>[] exclude;
    
    //Esta es la lsita que contiene referencias a las entidades que forman parte del sistema.
    List<Entity> entities = new ArrayList<>();
    
    //Hay una refereencia al ECS también porsiaca
    ECS ecs;

    
    public System(){
        
    }
    
    //Se llaman desde la clase hijo (p.e. SpriteSystem) para indicar los componentes que se necesitan y tal
    public void include(Class <? extends Component>... include){
        this.include = include;
    }
    public void exclude(Class <? extends Component>... exclude){
        this.exclude = exclude;
    }
    
    //Si la entidad cumple lkos requisitos (include y exclude) se mete en el sistema.
    public void consider(Entity e){
        if(this.matches(e)){
            this.entities.add(e);
        }
    }
    
    //Es parecido a consider solo que para entidades que ya formaban parte del sistema y se han modificado los componentes.
    public void validate(Entity e){
        if(entities.contains(e)){
            if(!this.matches(e))
                this.remove(e);
        }
    }
    
    //Devuelve true si se cumplen los requisitos. Se usa en consider()
    private boolean matches(Entity e){
        return e.contains(include) && !e.containsAny(exclude);
    }
    
    public void remove(Entity e){
        entities.remove(e);
    }

    public List<Entity> getEntities(){
        return entities.stream()
        .filter(s -> s.isActive())
        .collect(Collectors.toList());
    }
    
    public void setECS(ECS ecs){
        this.ecs = ecs;
    }
    
    //Este es el método abstracto que implemnentarán las clases hijo. 
    //En él se define cómo se updatearán las entidades que forman parte del sistema.
    public abstract void update(double dt);
}
