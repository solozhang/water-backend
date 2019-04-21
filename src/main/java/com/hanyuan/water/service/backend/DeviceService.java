package com.hanyuan.water.service.backend;

import com.google.gson.Gson;
import com.hanyuan.water.dao.MonitorDAO;
import com.hanyuan.water.model.Device;
import com.hanyuan.water.model.Monitor;
import com.hanyuan.water.utils.HttpClientUtil;
import com.hanyuan.water.vo.resp.Capture;
import com.hanyuan.water.vo.resp.CaptureData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by solozhang on 2019/4/14.
 */
@Slf4j
@Service
public class DeviceService {

    @Value("${ys7.url.capture}")
    private String captureUrl;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FileService fileService;

    @Autowired
    private MonitorDAO monitorDAO;

    public void capture(Device device){
        String token = tokenService.getToken();
        Map<String, String> params = new HashMap<>();
        params.put("accessToken", token);
        params.put("deviceSerial", device.getDeviceSerial());
        params.put("channelNo", device.getChannelNo());
        String response = HttpClientUtil.httpPost(captureUrl, params);
        Gson gson = new Gson();
        Capture capture = gson.fromJson(response, Capture.class);
        if (capture.getCode().equals("200")) {
            CaptureData captureData = capture.getData();
            String picUrl = captureData.getPicUrl();
            Monitor monitor = monitorDAO.getByDevice(device.getId());
            String filename = fileService.getFilename(monitor);
            String path = fileService.mkDir(monitor);
            HttpClientUtil.downFile(picUrl, path, filename);
        } else {
            log.error(response);
        }
    }
}
