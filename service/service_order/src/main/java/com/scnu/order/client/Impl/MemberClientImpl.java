package com.scnu.order.client.Impl;

import com.scnu.order.client.MemberClient;
import com.scnu.utils.dto.OrderUser;
import org.springframework.stereotype.Component;

@Component
public class MemberClientImpl implements MemberClient {
    @Override
    public OrderUser orderUser(String memberId) {
        return null;
    }
}
