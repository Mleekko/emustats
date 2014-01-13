package com.mleekko.test.domain;

import java.util.List;

/**
 * @author Mleekko
 */
public class HatcherStats {

    private List<Hatcher> hatchers;
    private int totalBlocks;

    public HatcherStats(List<Hatcher> hatchers, int totalBlocks) {
        this.hatchers = hatchers;
        this.totalBlocks = totalBlocks;
    }

    public List<Hatcher> getHatchers() {
        return hatchers;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }
}
