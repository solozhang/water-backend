package com.hanyuan.water.model;

/**
 * Created by solozhang on 2019/4/21.
 */
public enum MeterLevel {
    ONE("一级表", 1),
    TWO("二级表", 2),
    THREE("三级表", 3),
    FOUR("四级表", 4);

    private String name;
    private int index;

    MeterLevel(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (MeterLevel m : MeterLevel.values()) {
            if (m.getIndex() == index) {
                return m.name;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
