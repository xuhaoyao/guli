package com.scnu.ucenter.controller;

import com.google.gson.Gson;
import com.scnu.exceptions.LoginException;
import com.scnu.ucenter.entity.Member;
import com.scnu.ucenter.service.MemberService;
import com.scnu.ucenter.utils.HttpClientUtils;
import com.scnu.ucenter.utils.WechatConstant;
import com.scnu.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Api(tags = "微信登录控制器")
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("生成二维码")
    @GetMapping("/login")
    public String getQRCode(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirect_uri = WechatConstant.WX_OPEN_REDIRECT_URL;
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new LoginException("出错了...");
        }

        String url = String.format(baseUrl,
                        WechatConstant.WX_OPEN_APP_ID,
                        WechatConstant.WX_OPEN_REDIRECT_URL,
                        "scnu");
        return "redirect:" + url;
    }

    @ApiOperation("跳转登录")
    @GetMapping("/callback")
    public String callback(String code, String state){
        //1.获取code,临时票据,类似于验证码
        //2.拿着code请求微信固定的地址,得到两个值access_token和openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=%s&" +
                "secret=%s&" +
                "code=%s&" +
                "grant_type=authorization_code";
        //拼接三个参数,id,密钥和code
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                                        WechatConstant.WX_OPEN_APP_ID,
                                        WechatConstant.WX_OPEN_APP_SECRET,
                                        code);
        //请求这个拼接好的地址,得到返回值access_token和openid
        //使用Httpclient发送请求
        String accessTokenInfo;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
/*
正确的返回：
{
"access_token":"ACCESS_TOKEN",  接口调用凭证
"expires_in":7200,
"refresh_token":"REFRESH_TOKEN",
"openid":"OPENID",              授权用户唯一标识
"scope":"SCOPE"
}
*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginException("微信登录失败...请求access_token和openid时异常");
        }
        Gson gson = new Gson();
        HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = (String) accessTokenMap.get("access_token");
        String openid = (String) accessTokenMap.get("openid");

        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=%s&" +
                "openid=%s";
        //3.拿着access_token和openid再去请求固定地址,获取扫码人的信息
        String userInfoUrl = String.format(baseUserInfoUrl,accessToken,openid);
        String userInfo,nickname,headimgurl;
        try {
            userInfo = HttpClientUtils.get(userInfoUrl);
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            nickname = (String) userInfoMap.get("nickname");     //昵称
            headimgurl = (String) userInfoMap.get("headimgurl"); //头像
/*
userInfo正确的Json返回结果：
{
"openid":"OPENID",      普通用户的标识，对当前开发者帐号唯一
"nickname":"NICKNAME",
"sex":1,
"province":"PROVINCE",
"city":"CITY",
"country":"COUNTRY",
"headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
"privilege":[
"PRIVILEGE1",
"PRIVILEGE2"
],
"unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"

}
 */
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginException("微信登录失败...获取用户信息时异常");
        }

        //扫码人信息不在数据库时,给数据库添加记录
        Member member = memberService.getByOpenId(openid);
        if(member == null){
            member = new Member();
            member.setAvatar(headimgurl);
            member.setNickname(nickname);
            member.setOpenid(openid);
            memberService.save(member);
        }else{
            //若用户的微信名或者头像更换,则更新数据
            if(!member.getNickname().equals(nickname) || !member.getAvatar().equals(headimgurl)){
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.updateById(member);
            }
        }
        /*
        这里不能将扫描的用户信息放入cookie中在首页进行显示,因为cookie无法实现跨域访问(见TestController)
        解决:
            使用jwt根据member对象生成token字符串
         */
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }

}
