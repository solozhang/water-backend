package com.hanyuan.water.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by solozhang on 2019/4/14.
 */
@Slf4j
public class HttpClientUtil {

    public static String httpPost(String url, Map<String, String> params){
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(10000);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(10000);

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());
        HttpClient httpclient = builder.build();
        HttpPost httpPost = new HttpPost(url);

        //设置参数
        List<NameValuePair> forms = new ArrayList<>();
        for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String value = String.valueOf(params.get(name));
            forms.add(new BasicNameValuePair(name, value));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(forms));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("[request]url:{}, params:{}", url, params.toString());

        try {
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200){
                String ret = EntityUtils.toString(response.getEntity());
                log.info("[response]body:{}", ret);
                return ret;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void downFile(String url, Path path, String filename){
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(10000);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(10000);

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());
        HttpClient httpclient = builder.build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            InputStream in = entity.getContent();
            File file = new File(path.getParent() + "/" + path.getFileName() + "/" + filename);
            FileOutputStream out = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            out.flush();
            log.info("[download]url:{}, file:{}", url, filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
