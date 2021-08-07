package com.scnu.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConstant implements InitializingBean {

    @Value("${spring.mail.username}")
    private String email;

    public static String EMAIL;

    @Override
    public void afterPropertiesSet() throws Exception {
        EMAIL = this.email;
    }
}
