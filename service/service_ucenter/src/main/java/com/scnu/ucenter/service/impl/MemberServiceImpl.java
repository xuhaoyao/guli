package com.scnu.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.exceptions.LoginException;
import com.scnu.exceptions.RegisterException;
import com.scnu.ucenter.entity.Member;
import com.scnu.ucenter.mapper.MemberMapper;
import com.scnu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.ucenter.vo.LoginVo;
import com.scnu.ucenter.vo.MemberInfo;
import com.scnu.ucenter.vo.RegisterVo;
import com.scnu.utils.JwtUtils;
import com.scnu.utils.MD5;
import com.scnu.utils.dto.CommentUser;
import com.scnu.utils.dto.OrderUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-07
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private final String AVATAR = "https://xhy-2021.oss-cn-beijing.aliyuncs.com/2021/08/02/63b73fb97439449cae408df7d12f9504file.png";

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile().trim();
        String password = loginVo.getPassword().trim();
        if(StringUtils.isEmpty(mobile)){
            throw new LoginException("手机号不能为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new LoginException("密码不能为空");
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member member = baseMapper.selectOne(wrapper);
        if(member == null){
            throw new LoginException("该手机号不存在");
        }
        if(member.getIsDisabled()){
            throw new LoginException("该手机号被禁用");
        }
        String md5_password = MD5.encrypt(password);
        if(!md5_password.equals(member.getPassword())){
            throw new LoginException("密码错误");
        }
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname().trim();
        String mobile = registerVo.getMobile().trim();
        String password = registerVo.getPassword().trim();
        String code = registerVo.getCode().trim();
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) ||
            StringUtils.isEmpty(password) || StringUtils.isEmpty(code)){
            throw new RegisterException("请将信息填写完整");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(redisCode == null){
            throw new RegisterException("验证码已过期,请重新发送...");
        }
        if(!redisCode.equals(code)){
            throw new RegisterException("验证码错误");
        }

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count != 0){
            throw new RegisterException("该邮箱已被注册");
        }

        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar(AVATAR);
        baseMapper.insert(member);
    }

    @Override
    public MemberInfo getInfo(String id) {
        Member member = baseMapper.selectById(id);
        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(member,memberInfo);
        return memberInfo;
    }

    @Override
    public Member getByOpenId(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public CommentUser getCommentUser(String id) {
        Member member = baseMapper.selectById(id);
        CommentUser commentUser = new CommentUser();
        commentUser.setNickname(member.getNickname());
        commentUser.setAvatar(member.getAvatar());
        return commentUser;
    }

    @Override
    public OrderUser getOrderUser(String memberId) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("id",memberId);
        wrapper.select("mobile","nickname");
        Member member = baseMapper.selectOne(wrapper);
        OrderUser orderUser = new OrderUser();
        orderUser.setMemberId(memberId);
        orderUser.setNickname(member.getNickname());
        orderUser.setMobile(member.getMobile());
        return orderUser;
    }
}
