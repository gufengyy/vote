package com.gufengyy.vote.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    private static Log logger = LogFactory.getLog(HttpUtil.class);

    public static String requestGet(String urlWithParams) throws Exception {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        // HttpGet httpget = new HttpGet("http://www.baidu.com/");
        HttpGet httpget = new HttpGet(urlWithParams);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(500).setConnectTimeout(500)
                .setSocketTimeout(500).build();
        httpget.setConfig(requestConfig);

        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity);// , "utf-8");
        if (jsonStr.startsWith("spv( ")) {
            jsonStr = jsonStr.substring(5);
        }
        if (jsonStr.endsWith(" );")) {
            jsonStr = jsonStr.substring(0, jsonStr.length() - 3);
        }
        logger.info(jsonStr);
        httpget.releaseConnection();
        return jsonStr;
    }

}
