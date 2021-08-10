package com.scnu.utils;

public interface Code {
    Integer SUCCESS = 20000;
    Integer ERROR = 50000;
    Integer PENDING = 25000;   //支付中,前端拦截器判断依据
}
