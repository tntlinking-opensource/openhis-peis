package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppCouponMapper;
import com.center.medical.datamove.oracle.bean.model.AppCoupon;
import com.center.medical.datamove.oracle.service.AppCouponService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppCoupon)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:25
 */
@Slf4j
@Service("appCouponService")
@RequiredArgsConstructor
public class AppCouponServiceImpl extends ServiceImpl<AppCouponMapper, AppCoupon> implements AppCouponService {

    private final AppCouponMapper appCouponMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppCoupon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppCoupon> getPage(PageParam<AppCoupon> page, AppCoupon param) {
        return appCouponMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppCoupon getInfoById(String id) {
        return appCouponMapper.getInfoById(id);
    }

}


