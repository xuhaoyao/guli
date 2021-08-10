package com.scnu.order.service;

import com.scnu.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-09
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);

    Order getOrder(String orderNo);

    boolean idBuyCourse(String courseId, String memberId);
}
