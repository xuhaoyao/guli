package com.scnu.edu.client;

import com.scnu.edu.client.impl.UserDegradeFeignClient;
import com.scnu.utils.dto.CommentUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = UserDegradeFeignClient.class)
public interface UserClient {
/*
    //Could not write JSON: It is illegal to call this method if the current request is not in asynchronous mode
    @GetMapping("/ucenter/member/getInfo")
    Result getInfo(HttpServletRequest request);*/

    @GetMapping("/ucenter/member/commentUser/{id}")
    CommentUser commentUser(@PathVariable("id") String id);


}
