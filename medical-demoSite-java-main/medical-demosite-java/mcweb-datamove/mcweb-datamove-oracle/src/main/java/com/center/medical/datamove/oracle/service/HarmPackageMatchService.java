package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.HarmPackageMatch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 接害因素、套餐匹配表(HarmPackageMatch)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:14
 */
public interface HarmPackageMatchService extends IService<HarmPackageMatch> {

    /**
     * 分页查询[接害因素、套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<HarmPackageMatch> getPage(PageParam<HarmPackageMatch> page, HarmPackageMatch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HarmPackageMatch getInfoById(String id);

}

