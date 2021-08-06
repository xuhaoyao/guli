package com.scnu.cms.service;

import com.scnu.cms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-06
 */
public interface BannerService extends IService<Banner> {

    List<Banner> getAllBanner();

    void saveBanner(Banner banner);

    void updateBanner(Banner banner);

    void deleteBanner(String id);
}
