package com.scnu.ucenter.controller;


import com.scnu.ucenter.service.MemberService;
import com.scnu.ucenter.vo.LoginVo;
import com.scnu.ucenter.vo.MemberInfo;
import com.scnu.ucenter.vo.RegisterVo;
import com.scnu.utils.JwtUtils;
import com.scnu.utils.Result;
import com.scnu.utils.dto.CommentUser;
import com.scnu.utils.dto.OrderUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-07
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return Result.ok().data("token",token); //登录成功返回一个token
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    @ApiOperation("根据请求头的token获取用户信息")
    @GetMapping("/getInfo")
    public Result getInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);    //从请求头中取token,得到用户的id
        MemberInfo memberInfo = memberService.getInfo(id);      //从数据库取用户信息,返回,由前端存入cookie中
        System.out.println(memberInfo);
        return Result.ok().data("item",memberInfo);
    }

    @GetMapping("/commentUser/{id}")
    public CommentUser commentUser(@PathVariable("id") String id){
        CommentUser commentUser = memberService.getCommentUser(id);
        return commentUser;
    }

    @ApiOperation("生成订单的客户信息")
    @GetMapping("/orderInfo/{memberId}")
    public OrderUser orderUser(@PathVariable("memberId") String memberId){
        return memberService.getOrderUser(memberId);
    }

    @ApiOperation("查询某一天的注册人数")
    @GetMapping("/registerNum/{day}")
    public Integer registerNum(@PathVariable("day") String day){
        return memberService.getRegisterNum(day);
    }
}

