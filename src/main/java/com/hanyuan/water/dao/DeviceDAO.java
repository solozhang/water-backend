package com.hanyuan.water.dao;

import com.hanyuan.water.model.Device;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by solozhang on 2019/4/6.
 */

@Repository
public interface DeviceDAO {
    @Select("SELECT * FROM device ORDER BY id")
    List<Device> query();
}
