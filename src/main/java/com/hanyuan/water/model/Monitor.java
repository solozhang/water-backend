package com.hanyuan.water.model;

import lombok.Data;

/**
 * Created by solozhang on 2019/4/6.
 */

@Data
public class Monitor {
    private Long id;

    private Long projectId;

    private Byte meterLevel;

    private String name;

    private Integer deviceNo;

    private Long deviceId;
}
