package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppUserCouponMapper;
import com.center.medical.datamove.oracle.bean.model.AppUserCoupon;
import com.center.medical.datamove.oracle.service.AppUserCouponService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppUserCoupon)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:07
 */
@Slf4j
@Service("appUserCouponService")
@RequiredArgsConstructor
public class AppUserCouponServiceImpl extends ServiceImpl<AppUserCouponMapper, AppUserCoupon> implements AppUserCouponService {

    private final AppUserCouponMapper appUserCouponMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppUserCoupon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppUserCoupon> getPage(PageParam<AppUserCoupon> page, AppUserCoupon param) {
        return appUserCouponMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppUserCoupon getInfoById(String id) {
        return appUserCouponMapper.getInfoById(id);
    }

}


