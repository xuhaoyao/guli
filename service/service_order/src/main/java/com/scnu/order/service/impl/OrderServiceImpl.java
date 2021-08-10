package com.scnu.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.exceptions.OrderException;
import com.scnu.order.client.EduClient;
import com.scnu.order.client.MemberClient;
import com.scnu.order.entity.Order;
import com.scnu.order.mapper.OrderMapper;
import com.scnu.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.order.utils.OrderNoUtil;
import com.scnu.utils.dto.OrderCourseInfo;
import com.scnu.utils.dto.OrderUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private EduClient eduClient;

    @Transactional(rollbackFor = OrderException.class)
    @Override
    public String createOrder(String courseId, String memberId) {

        OrderUser orderUser = memberClient.orderUser(memberId);
        if(orderUser == null){
            throw new OrderException("生成订单失败->远程调用获取用户信息失败");
        }
        OrderCourseInfo orderCourseInfo = eduClient.orderCourseInfo(courseId);
        if(orderCourseInfo == null){
            throw new OrderException("生成订单失败->远程调用获取课程信息失败");
        }
        Order order = new Order();
        String orderNo = OrderNoUtil.getOrderNo();
        order.setOrderNo(orderNo);
        //保证字段一致
        BeanUtils.copyProperties(orderUser,order);
        BeanUtils.copyProperties(orderCourseInfo,order);
        order.setStatus(0);  //未支付
        order.setPayType(1); //微信支付
        int result = baseMapper.insert(order);
        if(result != 1){
            throw new OrderException("生成订单失败->数据库异常");
        }
        return orderNo;
    }

    @Override
    public Order getOrder(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean idBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}