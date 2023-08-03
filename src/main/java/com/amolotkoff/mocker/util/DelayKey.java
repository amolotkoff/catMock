package com.amolotkoff.mocker.util;

public class DelayKey {
    public long Value;
    private final long startValue;

    public DelayKey(long value) {
        this.Value = value;
        this.startValue = value;
    }

    public long getStartValue() {
        return startValue;
    }
}