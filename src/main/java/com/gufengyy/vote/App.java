package com.gufengyy.vote;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.gufengyy.vote.http.HttpUtil;

public class App {
    private static Log logger = LogFactory.getLog(App.class);

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        int id = Integer.parseInt(args[0]);
        while (true) {
            try {
                String result = HttpUtil
                        .requestGet("http://123.196.118.77/v6/spv/spv.jsp?id="
                                + id + "&df=16&rt=0&bv=20&time="
                                + new Date().getTime());
                Map<String, String> map = (Map<String, String>) JSON
                        .parse(result);
                if ("N".equals(map.get("pv_1000"))) {
                    logger.info("投票成功！当前票数：" + map.get("pv_131"));
                } else if ("C".equals(map.get("pv_1000"))) {
                    logger.info("感谢您的关注！投票已经结束了！当前票数：" + map.get("pv_131"));
                    break;
                } else {
                    logger.info("感谢您的关注！您已经投过票了！当前票数：" + map.get("pv_131"));
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }finally{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
