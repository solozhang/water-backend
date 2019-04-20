package com.hanyuan.water.dao;

import com.hanyuan.water.model.DeviceToken;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by solozhang on 2019/4/14.
 */

@Repository
public interface DeviceTokenDAO {
    @Select("SELECT * FROM device_token")
    DeviceToken get();

    @Update("UPDATE device_token SET access_token=#{accessToken}, expire_at=#{expireAt} WHERE id = 1")
    void updateToken(@Param(value = "accessToken") String accessToken,
                     @Param(value = "expireAt") Long expireAt);
}
