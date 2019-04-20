package com.hanyuan.water.model;

import lombok.Data;

/**
 * Created by solozhang on 2019/4/6.
 */

@Data
public class Device {
    private Long id;

    private String deviceSerial;

    private String channelNo;

    private enum status {
        //正常
        NORMAL,
        //异常
        ABNORMAL
    }
}
