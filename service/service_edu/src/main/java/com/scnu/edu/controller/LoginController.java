package com.scnu.edu.controller;

import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 跨域问题:
 *  当通过一个地址去访问另外一个地址,若
 *  访问协议
 *  ip地址
 *  端口号
 *  三个有一个不一样,就会出现跨域问题
 *
 *  如果加了@CrossOrigin还出现了跨域问题,可以先检查一下前端发的接口地址有没有写错,转发到一个无效的地址也会有跨域问题
 */
@Api(tags = "登录控制器")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class LoginController {

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","token");
    }

    @ApiOperation("返回用户信息")
    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","xhy").data("avatar","https://tse1-mm.cn.bing.net/th/id/R-C.d410b4622a26de587231cfc67d071ecc?rik=bW7n%2foUGy%2bULDw&riu=http%3a%2f%2fwww.giantfreakinrobot.com%2fwp-content%2fuploads%2f2009%2f07%2favatar.jpg&ehk=XVadQuJT0YhruAABPC5oVccn2r32CuQ8JQkkXWZhlzw%3d&risl=&pid=ImgRaw");
    }

}
