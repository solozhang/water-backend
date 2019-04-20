package com.hanyuan.water.service.backend;

import com.google.gson.Gson;
import com.hanyuan.water.dao.DeviceTokenDAO;
import com.hanyuan.water.model.DeviceToken;
import com.hanyuan.water.utils.HttpClientUtil;
import com.hanyuan.water.vo.resp.Token;
import com.hanyuan.water.vo.resp.TokenData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solozhang on 2019/4/14.
 */
@Slf4j
@Component
public class TokenService {
    @Value("${ys7.url.gettoken}")
    private String tokenUrl;

    @Autowired
    private DeviceTokenDAO deviceTokenDAO;

    public boolean tokenExpire(DeviceToken deviceToken){
        return deviceToken.getExpireAt() <= System.currentTimeMillis();
    }

    public String getToken(){
        DeviceToken deviceToken = deviceTokenDAO.get();
        if (!tokenExpire(deviceToken)) {
            return deviceToken.getAccessToken();
        }
        Map<String, String> params = new HashMap<>();
        params.put("appKey", deviceToken.getAppKey());
        params.put("appSecret", deviceToken.getSecret());
        String response = HttpClientUtil.httpPost(tokenUrl, params);
        Gson gson = new Gson();
        TokenData tokenData = gson.fromJson(response, Token.class).getData();
        String accessToken = tokenData.getAccessToken();
        Long expireTime = tokenData.getExpireTime();
        deviceTokenDAO.updateToken(accessToken, expireTime);
        return accessToken;
    }
}
