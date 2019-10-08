package com.manews.warbot.core;

public class KillerKilledEntity {

    private String killer;

    private String killed;

    public KillerKilledEntity(String killer, String killed) {
        this.killer = killer;
        this.killed = killed;
    }

    public String getKiller() {
        return killer;
    }

    public void setKiller(String killer) {
        this.killer = killer;
    }

    public String getKilled() {
        return killed;
    }

    public void setKilled(String killed) {
        this.killed = killed;
    }
}
