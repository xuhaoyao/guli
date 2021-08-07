package com.scnu.msm.service.impl;

import com.scnu.msm.service.MsmService;
import com.scnu.msm.utils.EmailConstant;
import com.scnu.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Async
    @Override
    public void send(String email) {
        String value = RandomUtil.getSixBitRandom();
        send(email,value);
        redisTemplate.opsForValue().set(email,value,3, TimeUnit.MINUTES);
    }

    private void send(String email, String value) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("注册验证码");
        mailMessage.setText("【scnu】验证码" + value + ",3分钟内有效,请勿泄露");

        mailMessage.setTo(email);
        mailMessage.setFrom(EmailConstant.EMAIL);
        mailSender.send(mailMessage);
    }
}
