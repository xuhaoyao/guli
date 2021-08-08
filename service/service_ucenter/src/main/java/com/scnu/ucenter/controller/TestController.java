package com.scnu.ucenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ucenter")
@CrossOrigin
public class TestController {

    @GetMapping("/testCookie")
    public String testCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("Pong","123456888");
        response.addCookie(cookie);
        return "redirect:http://localhost:8160";    //可以携带
        //return "redirect:http://localhost:3000";  //跨域 cookie无法携带
    }

}
