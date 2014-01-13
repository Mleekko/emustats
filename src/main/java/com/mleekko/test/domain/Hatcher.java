package com.mleekko.test.domain;

/**
 * @author Mleekko
 */
public class Hatcher {
    private String hatcherId;
    private int blocksHatched;

    public Hatcher() {
    }

    public Hatcher(String hatcherId, int blocksHatched) {
        this.hatcherId = hatcherId;
        this.blocksHatched = blocksHatched;
    }

    public String getHatcherId() {
        return hatcherId;
    }

    public void setHatcherId(String hatcherId) {
        this.hatcherId = hatcherId;
    }

    public int getBlocksHatched() {
        return blocksHatched;
    }

    public void setBlocksHatched(int blocksHatched) {
        this.blocksHatched = blocksHatched;
    }
}