package com.mleekko.test.domain;

/**
 * @author Mleekko
 */
public class TimedHatcher {
    private String hatcherId;
    private int blocksHatched;
    private long timeBlock;

    public TimedHatcher() {
    }

    public TimedHatcher(String hatcherId, int blocksHatched, long timeBlock) {
        this.hatcherId = hatcherId;
        this.blocksHatched = blocksHatched;
        this.timeBlock = timeBlock;
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

    public long getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(long timeBlock) {
        this.timeBlock = timeBlock;
    }
}
