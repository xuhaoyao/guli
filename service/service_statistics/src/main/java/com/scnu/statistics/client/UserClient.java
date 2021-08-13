package com.scnu.statistics.client;

import com.scnu.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = UserClientImpl.class)
@Component
public interface UserClient {

    @GetMapping("/ucenter/member/registerNum/{day}")
    Integer registerNum(@PathVariable("day") String day);
}
