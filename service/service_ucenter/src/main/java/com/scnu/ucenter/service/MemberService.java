package com.scnu.ucenter.service;

import com.scnu.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.ucenter.vo.LoginVo;
import com.scnu.ucenter.vo.MemberInfo;
import com.scnu.ucenter.vo.RegisterVo;
import com.scnu.utils.dto.CommentUser;
import com.scnu.utils.dto.OrderUser;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-07
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    MemberInfo getInfo(String id);

    Member getByOpenId(String openid);

    CommentUser getCommentUser(String id);

    OrderUser getOrderUser(String memberId);
}
