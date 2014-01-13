package com.mleekko.test.view;

import java.util.List;

/**
 * @author Mleekko
 */
public class HatchingTable {

    private List<String> header;
    private List<List<Number>> rows;

    public HatchingTable(List<String> header, List<List<Number>> rows) {
        this.header = header;
        this.rows = rows;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<Number>> getRows() {
        return rows;
    }
}
