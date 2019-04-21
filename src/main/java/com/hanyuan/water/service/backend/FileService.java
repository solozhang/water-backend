package com.hanyuan.water.service.backend;

import com.hanyuan.water.dao.ProjectDAO;
import com.hanyuan.water.model.MeterLevel;
import com.hanyuan.water.model.Monitor;
import com.hanyuan.water.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by solozhang on 2019/4/21.
 */

@Service
public class FileService {

    @Value("${static.rootpath}")
    private String rootpath;

    @Autowired
    private ProjectDAO projectDAO;

    public String getFilename(Monitor monitor) {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return f.format(now) + ".jpg";
        /*
        Long projectId = monitor.getProjectId();
        Project project = projectDAO.get(projectId);
        String projectName = project.getName();
        String meterLevel = MeterLevel.getName(monitor.getMeterLevel());
        String monitorName = monitor.getName();
        return projectName + "-" + meterLevel + '-' + monitorName + '-' + f.format(now) + ".jpg";
        */
    }

    public Path mkDir(Monitor monitor){
        Long projectId = monitor.getProjectId();
        Project project = projectDAO.get(projectId);
        String projectName = project.getName();
        String meterLevel = MeterLevel.getName(monitor.getMeterLevel());
        String monitorName = monitor.getName();
        String dirName =  rootpath + projectName + "/" + meterLevel + "/" + monitorName + "/";
        try {
            String dirNameEncoding = new String(dirName.getBytes("UTF-8"));
            Path path = Files.createDirectories(Paths.get(dirNameEncoding));
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
