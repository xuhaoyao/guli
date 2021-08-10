package com.scnu.edu.client.impl;

import com.scnu.edu.client.UserClient;
import com.scnu.utils.dto.CommentUser;
import org.springframework.stereotype.Component;


@Component
public class UserDegradeFeignClient implements UserClient {
    @Override
    public CommentUser commentUser(String id) {
        return null;
    }
}
