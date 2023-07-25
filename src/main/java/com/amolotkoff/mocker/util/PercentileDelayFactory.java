package com.amolotkoff.mocker.util;

public class PercentileDelayFactory implements IDelayFactory {

    private final DelayKey percentile, percentileMin, percentileMax, delay;

    public PercentileDelayFactory(DelayKey percentile,
                                  DelayKey percentileMin,
                                  DelayKey percentileMax,
                                  DelayKey delay) {
        this.percentile = percentile;
        this.percentileMin = percentileMin;
        this.percentileMax = percentileMax;
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "percentile";
    }

    @Override
    public long get() {
        long percentileSize = percentile.Value;

        if (percentileSize > 100)
            percentileSize = 100;

        long currentPercentileValue = generateNumber(0, 100);

        if (currentPercentileValue <= percentileSize) {
            final long max = percentileMax.Value;
            final long min = percentileMin.Value;

            return generateNumber(min, max);
        }

        return delay.Value;
    }

    private long generateNumber(long min, long max) {
        return (long)((Math.random() * (max - min)) + min);
    }
}