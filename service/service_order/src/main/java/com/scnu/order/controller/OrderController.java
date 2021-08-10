package com.scnu.order.controller;


import com.scnu.exceptions.OrderException;
import com.scnu.order.entity.Order;
import com.scnu.order.service.OrderService;
import com.scnu.utils.JwtUtils;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-09
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单")
    @GetMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new OrderException("购买该课程请先登录");
        }
        String orderNo = orderService.createOrder(courseId,memberId);
        return Result.ok().data("orderNo",orderNo);
    }

    @ApiOperation("根据订单号查询订单")
    @GetMapping("/{orderNo}")
    public Result getOrder(@PathVariable("orderNo") String orderNo){
        Order order = orderService.getOrder(orderNo);
        return Result.ok().data("item",order);
    }

    @ApiOperation("查询用户是否购买了该课程")
    @GetMapping("/buyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId){
        return orderService.idBuyCourse(courseId,memberId);
    }

}

