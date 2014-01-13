package com.mleekko.test.view;

import java.util.List;
import java.util.Map;

/**
 * @author Mleekko
 */
public class HatchingChart {

    /**
     * The list of ALL hatcherIds
     */
    private List<String> hatchers;

    /**
     * Long - time block id
     * String - hatcher id
     * Integer - blocks hatched in that time block
     */
    private Map<Long, Map<String, Integer>> hatchersPerTimeBlock;

    public HatchingChart(List<String> hatchers, Map<Long, Map<String, Integer>> hatchersPerTimeBlock) {
        this.hatchers = hatchers;
        this.hatchersPerTimeBlock = hatchersPerTimeBlock;
    }

    public List<String> getHatchers() {
        return hatchers;
    }

    public Map<Long, Map<String, Integer>> getHatchersPerTimeBlock() {
        return hatchersPerTimeBlock;
    }
}
