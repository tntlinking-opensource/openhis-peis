package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppUserCoupon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (AppUserCoupon)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:07
 */
public interface AppUserCouponService extends IService<AppUserCoupon> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppUserCoupon> getPage(PageParam<AppUserCoupon> page, AppUserCoupon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppUserCoupon getInfoById(String id);

}

