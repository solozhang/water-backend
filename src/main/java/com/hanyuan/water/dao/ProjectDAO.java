package com.hanyuan.water.dao;

import com.hanyuan.water.model.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by solozhang on 2019/4/6.
 */

@Repository
public interface ProjectDAO {
    @Select("SELECT * FROM project WHERE deleted = 0 ORDER BY id")
    List<Project> query();

    @Select("SELECT * FROM project WHERE id = #{id}")
    Project get(@Param(value = "id") Long id);
}
