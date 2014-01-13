package com.mleekko.test.controllers;

import com.mleekko.json.JsonConverter;
import com.mleekko.test.dao.PeerMapper;
import com.mleekko.test.domain.Asset;
import com.mleekko.test.domain.Hatcher;
import com.mleekko.test.view.HatcherStats;
import com.mleekko.test.domain.JsonPackage;
import com.mleekko.test.domain.TimedHatcher;
import com.mleekko.test.view.HatchingChart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Mleekko
 */
@RequestMapping("/stats")
@Controller
public class HatcherStatsController {
    private static final Logger LOG = LoggerFactory.getLogger(HatcherStatsController.class);

    private static final int CHART_PERIOD_MINUTES = 24 * 60;
    private static final int CHART_GROUPING_SEC = 60 * 60;

    @Autowired
    private PeerMapper mapper;

    @Autowired
    private JsonConverter converter;

    @RequestMapping(value = "/{minutes}/hatch.json", method = RequestMethod.GET)
    public void getStats(HttpServletResponse response, @PathVariable String minutes ) throws IOException {

        int min = 30;

        if (StringUtils.hasText(minutes)) {
            min = Integer.valueOf(minutes);
        }


        List<Hatcher> hatchers = mapper.getHatchers(min);
        int blocks = mapper.getBlocksCount(min);

        HatcherStats stats = new HatcherStats(hatchers, blocks);

        converter.writeToResponse(response, JsonPackage.withData(stats));

    }

    @RequestMapping(value = "/charts/24hours.json", method = RequestMethod.GET)
    public void get24HoursChart(HttpServletResponse response) throws IOException {
        List<TimedHatcher> timedHatchers = mapper.getHatchersPerInterval(CHART_PERIOD_MINUTES, CHART_GROUPING_SEC);

        Map<Long, Map<String, Integer>> hatchersPerTimeBlock = new TreeMap<>();
        Set<String> hatcherIds = new TreeSet<>();


        for (TimedHatcher timedHatcher : timedHatchers) {
            long timeBlock = timedHatcher.getTimeBlock();
            Map<String, Integer> hatchers = hatchersPerTimeBlock.get(timeBlock);
            if (hatchers == null) {
                hatchers = new HashMap<>();
                hatchersPerTimeBlock.put(timeBlock, hatchers);
            }

            String hatcherId = timedHatcher.getHatcherId();
            int blocksHatched = timedHatcher.getBlocksHatched();

            hatcherIds.add(hatcherId);
            hatchers.put(hatcherId, blocksHatched);
        }

        HatchingChart hatchingChart = new HatchingChart(new ArrayList<>(hatcherIds), hatchersPerTimeBlock);
        converter.writeToResponse(response, JsonPackage.withData(hatchingChart));
    }

    @RequestMapping(value = "/charts/overall/24hours.json", method = RequestMethod.GET)
    public void getOverall24HoursChart(HttpServletResponse response) throws IOException {
        List<TimedHatcher> timedHatchers = mapper.getTotalBlocksPerInterval(CHART_PERIOD_MINUTES, CHART_GROUPING_SEC);

        Map<Long, Map<String, Integer>> hatchersPerTimeBlock = new TreeMap<>();
        Set<String> hatcherIds = new TreeSet<>();


        for (TimedHatcher timedHatcher : timedHatchers) {
            long timeBlock = timedHatcher.getTimeBlock();
            Map<String, Integer> hatchers = hatchersPerTimeBlock.get(timeBlock);
            if (hatchers == null) {
                hatchers = new HashMap<>();
                hatchersPerTimeBlock.put(timeBlock, hatchers);
            }

            String hatcherId = timedHatcher.getHatcherId();
            int blocksHatched = timedHatcher.getBlocksHatched();

            hatcherIds.add(hatcherId);
            hatchers.put(hatcherId, blocksHatched);
        }

        HatchingChart hatchingChart = new HatchingChart(new ArrayList<>(hatcherIds), hatchersPerTimeBlock);
        converter.writeToResponse(response, JsonPackage.withData(hatchingChart));
    }

    @RequestMapping(value = "/assets.json", method = RequestMethod.GET)
    public void getAssets(HttpServletResponse response) throws IOException {

        List<Asset> assets = mapper.listAssets();
        converter.writeToResponse(response, JsonPackage.withData(assets));

    }

}
