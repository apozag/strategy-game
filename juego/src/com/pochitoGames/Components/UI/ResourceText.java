/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.UI;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.Other.ResourceType;

/**
 *
 * @author PochitoMan
 */
public class ResourceText extends Component {

    private ResourceType type;

    public ResourceText(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }
}
