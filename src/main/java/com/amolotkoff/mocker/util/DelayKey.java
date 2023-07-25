package com.amolotkoff.mocker.util;

public class DelayKey {
    public DelayViewType viewType;
    public long Value;
    private final long startValue;

    public DelayKey(DelayViewType viewType, long value) {
        this.viewType = viewType;
        this.Value = value;
        this.startValue = value;
    }

    public long getStartValue() {
        return startValue;
    }
}