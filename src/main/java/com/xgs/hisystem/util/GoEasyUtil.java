package com.xgs.hisystem.util;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/22
 */

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * web端推送
 */

@Component
public class GoEasyUtil {

    //private static String APPKEY = "78028e7e-edcc-4524-b56b-45639785a53a";
/* 
    public static String getAppKey() {
        return appKey;
    }

    @Value("${goeasy.appKey}")
    public void setAppKey(String app) {
        GoEasyUtil.appKey = app;
    } */

    private static String appKey = "78028e7e-edcc-4524-b56b-45639785a53a";


    private static String errorStr = "";

    private static final Logger log = LoggerFactory.getLogger(GoEasyUtil.class);

    /**
     * web 推送
     *
     * @param channel 频道
     * @param content 推送内容
     * @return 推送结果
     */
    public static String PushMessage(String channel, final String content) {
        GoEasy goEasy = new GoEasy(appKey);

        errorStr = "";
        goEasy.publish(channel, content, new PublishListener() {
            @Override
            public void onSuccess() {
                errorStr = "OK|消息" + content + "推送成功";
                log.info(errorStr);
            }

            @Override
            public void onFailed(GoEasyError error) {
                errorStr = "ERROR|消息" + content + "推送失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent();
                log.info(errorStr);
            }
        });

        return errorStr;
    }


/*     public static void main(String[] args) {
        PushMessage("hello", "7053fbb9b5e24e098f7d012943ebd4f9");
    } */
}
