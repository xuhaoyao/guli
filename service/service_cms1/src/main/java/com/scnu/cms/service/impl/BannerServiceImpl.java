package com.scnu.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.cms.entity.Banner;
import com.scnu.cms.mapper.BannerMapper;
import com.scnu.cms.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.exceptions.BannerException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-06
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    /*
    @Cacheable
        根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；
        如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
        存入redis中的键的名称是value::key
     */

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<Banner> getAllBanner() {
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified");
        wrapper.last("limit 2");
        List<Banner> banners = baseMapper.selectList(wrapper);
        return banners;
    }

    /*
    @CacheEvict
        使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上
        allEntries:是否清空所有缓存，默认为 false。如果指定为 true，则方法调用后将立即清空所有的缓存
        会清空以 banner:: 开头的全部缓存
     */

    @CacheEvict(value = "banner", allEntries=true)
    @Transactional
    @Override
    public void saveBanner(Banner banner) {
        int result = baseMapper.insert(banner);
        if(result != 1){
            throw new BannerException("新增banner失败");
        }
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Transactional
    @Override
    public void updateBanner(Banner banner) {
        int result = baseMapper.updateById(banner);
        if(result != 1){
            throw new BannerException("更新banner失败");
        }
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Transactional
    @Override
    public void deleteBanner(String id) {
        int result = baseMapper.deleteById(id);
        if(result != 1){
            throw new BannerException("删除banner失败");
        }
    }
}
