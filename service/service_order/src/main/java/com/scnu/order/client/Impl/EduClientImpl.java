package com.scnu.order.client.Impl;

import com.scnu.order.client.EduClient;
import com.scnu.utils.Result;
import com.scnu.utils.dto.OrderCourseInfo;
import org.springframework.stereotype.Component;

@Component
public class EduClientImpl implements EduClient {
    @Override
    public OrderCourseInfo orderCourseInfo(String courseId) {
        return null;
    }

    @Override
    public Result orderBuy(String courseId) {
        return Result.error();
    }
}
