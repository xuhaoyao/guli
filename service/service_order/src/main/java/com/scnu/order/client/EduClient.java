package com.scnu.order.client;

import com.scnu.order.client.Impl.EduClientImpl;
import com.scnu.utils.Result;
import com.scnu.utils.dto.OrderCourseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "service-edu",fallback = EduClientImpl.class)
@Component
public interface EduClient {

    @GetMapping("/eduservice/coursefront/orderInfo/{courseId}")
    OrderCourseInfo orderCourseInfo(@PathVariable("courseId") String courseId);

    @PutMapping("/eduservice/coursefront/orderbuy/{courseId}")
    Result orderBuy(@PathVariable("courseId") String courseId);
}
