package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;

public class PigFarm extends Component {

    private long waitTimeMillis = 5000;
    private long lastTimeCreated = 0;

    public long getWaitTimeMillis() {
        return waitTimeMillis;
    }

    public long getLastTimeCreated() {
        return lastTimeCreated;
    }

    public void setWaitTimeMillis(long waitTimeMillis) {
        this.waitTimeMillis = waitTimeMillis;
    }

    public void setLastTimeCreated(long lastTimeCreated) {
        this.lastTimeCreated = lastTimeCreated;
    }
}
