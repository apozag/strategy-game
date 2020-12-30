/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pochitoGames.Components.People;

import com.pochitoGames.Components.Buildings.Quarry;
import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.States.MinerState;

/**
 * @author PochitoMan
 */
public class Miner extends Component {

    MinerState state = MinerState.WAIT;
    Quarry quarry;

    public MinerState getState() {
        return state;
    }

    public void setState(MinerState state) {
        this.state = state;
    }

    public void setQuarry(Quarry canteen) {
        this.quarry = canteen;
    }

    public Quarry getQuarry() {
        return quarry;
    }

}
