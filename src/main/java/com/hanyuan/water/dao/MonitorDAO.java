package com.hanyuan.water.dao;

import com.hanyuan.water.model.Monitor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by solozhang on 2019/4/21.
 */

@Repository
public interface MonitorDAO {
    @Select("SELECT * FROM monitor WHERE device_id = #{deviceId}")
    Monitor getByDevice(@Param(value = "deviceId") Long deviceId);

    @Select("SELECT * FROM monitor WHERE project_id = #{projectId}")
    List<Monitor> getMonitors(@Param(value = "projectId") Long projectId);
}
