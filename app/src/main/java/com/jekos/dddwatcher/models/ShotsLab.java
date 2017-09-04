package com.jekos.dddwatcher.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by жекос on 04.09.2017.
 */

public class ShotsLab {

    private static ShotsLab shotsLab;
    private List<Shot> shots;

    private ShotsLab() {
        shots = new ArrayList<Shot>();
    }

    public boolean isEmpty() {
        return (shots.isEmpty());
    }

    public List<Shot> getShots() {
        return shots;
    }

    public static ShotsLab getShotsLab() {
        if (shotsLab == null)
            shotsLab = new ShotsLab();
        return shotsLab;
    }

    public void addShots(List<Shot> shots) {
        for (Shot shot : shots) {
            this.shots.add(shot);
        }
    }
}
