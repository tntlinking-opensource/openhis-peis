package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.HarmPackageMatch;

/**
 * 危害因素-套餐匹配表(HarmPackageMatch)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface HarmPackageMatchService extends IService<HarmPackageMatch> {

    /**
     * 分页查询[危害因素-套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param HarmPackageMatch查询参数
     * @return 分页数据
     */
    IPage<HarmPackageMatch> getList(PageParam<HarmPackageMatch> page, HarmPackageMatch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    HarmPackageMatch getInfoById(String id);

}

