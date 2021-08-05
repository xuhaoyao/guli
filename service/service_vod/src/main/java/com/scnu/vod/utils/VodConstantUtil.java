package com.scnu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VodConstantUtil implements InitializingBean {


    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    public static String KEYID;

    public static String KEYSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID = this.keyId;
        KEYSECRET = this.keySecret;
    }
}
