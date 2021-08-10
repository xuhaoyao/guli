package com.scnu.order.client;

import com.scnu.order.client.Impl.MemberClientImpl;
import com.scnu.utils.dto.OrderUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = MemberClientImpl.class)
@Component
public interface MemberClient {

    @GetMapping("/ucenter/member/orderInfo/{memberId}")
    OrderUser orderUser(@PathVariable("memberId") String memberId);


}
