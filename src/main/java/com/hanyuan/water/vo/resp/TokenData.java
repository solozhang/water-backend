package com.hanyuan.water.vo.resp;

import lombok.Data;

/**
 * Created by solozhang on 2019/4/14.
 */

@Data
public class TokenData {
    private String accessToken;
    private Long expireTime;
}
