package com.hanyuan.water.service.backend;

import com.hanyuan.water.dao.DeviceDAO;
import com.hanyuan.water.dao.ProjectDAO;
import com.hanyuan.water.model.Device;
import com.hanyuan.water.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by solozhang on 2019/4/6.
 */

@Slf4j
@Service
public class ProjectSchedule {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FileService fileService;

    private static final ExecutorService projectExecutorService = new ThreadPoolExecutor(3, 20, 30, TimeUnit.SECONDS, new
            ArrayBlockingQueue<Runnable>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "projectThread");
        }
    });

    private static final ExecutorService deviceExecutorService = new ThreadPoolExecutor(10, 100, 30, TimeUnit.SECONDS, new
            ArrayBlockingQueue<Runnable>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "deviceThread");
        }
    });

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Scheduled(cron = "*/2 * * * * ?")
    public void init() {
        log.info("crontab project task...");
        List<Project> projectList = projectDAO.query();
        for (final Project project : projectList) {
            projectExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    handleProject(project);
                }
            });
        }
    }

    private void handleProject(Project project){
        if(isExecution(project)){
            List<Device> deviceList = deviceDAO.query();
            for (final Device device : deviceList) {
                if (device.getId().equals((project.getId())))
                    deviceExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            deviceService.capture(device);
                        }
                    });
            }
        }
    }

    private boolean isExecution(Project project){
        return (System.currentTimeMillis() + 1000) / (project.getFrequency() * 1000) - 1 ==
                (System.currentTimeMillis() - 1000) / (project.getFrequency() * 1000);
    }
}