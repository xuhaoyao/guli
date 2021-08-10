package com.scnu.edu.client.impl;

import com.scnu.edu.client.OrderClient;
import org.springframework.stereotype.Component;

@Component
public class OrderDegradeFeignClient implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
