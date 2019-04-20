package com.hanyuan.water.model;

import lombok.Data;

/**
 * Created by solozhang on 2019/4/5.
 */

@Data
public class Project {
    private Long id;

    private String name;

    private Integer frequency;

    private Byte deleted;
}
