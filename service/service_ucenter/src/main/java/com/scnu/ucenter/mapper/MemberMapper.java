package com.scnu.ucenter.mapper;

import com.scnu.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xhy
 * @since 2021-08-07
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer getRegisterNum(String day);
}
