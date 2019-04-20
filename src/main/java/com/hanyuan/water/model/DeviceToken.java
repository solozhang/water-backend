package com.hanyuan.water.model;

import lombok.Data;

/**
 * Created by solozhang on 2019/4/6.
 */

@Data
public class DeviceToken {
    private Long id;

    private String appKey;

    private String secret;

    private String accessToken;

    private Long expireAt;
}
