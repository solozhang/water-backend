package com.hanyuan.water.service.backend;

import com.hanyuan.water.dao.MonitorDAO;
import com.hanyuan.water.dao.ProjectDAO;
import com.hanyuan.water.model.MeterLevel;
import com.hanyuan.water.model.Monitor;
import com.hanyuan.water.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by solozhang on 2019/4/21.
 */

@Component
public class FileService {

    @Autowired
    private MonitorDAO monitorDAO;

    @Value("${static.rootpath}")
    private String rootpath;

    @Autowired
    private ProjectDAO projectDAO;

    public String getFilename(Monitor monitor) {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Long projectId = monitor.getProjectId();
        Project project = projectDAO.get(projectId);
        String projectName = project.getName();
        String meterLevel = MeterLevel.getName(monitor.getMeterLevel());
        String monitorName = monitor.getName();
        return projectName + "-" + meterLevel + '-' + monitorName + '-' + f.format(now) + ".jpg";
    }

    public String mkDir(Monitor monitor){
        Long projectId = monitor.getProjectId();
        Project project = projectDAO.get(projectId);
        String projectName = project.getName();
        String meterLevel = MeterLevel.getName(monitor.getMeterLevel());
        String monitorName = monitor.getName();
        String dirName =  rootpath + projectName + "/" + meterLevel + "/" + monitorName + "/";
        File f = new File(dirName);
        if(!f.exists())
            f.mkdirs();
        return dirName;
    }
}
