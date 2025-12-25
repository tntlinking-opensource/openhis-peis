package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.HarmPackageMatch;
import org.apache.ibatis.annotations.Param;

/**
 * 危害因素-套餐匹配表(HarmPackageMatch)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
public interface HarmPackageMatchMapper extends BaseMapper<HarmPackageMatch> {

    /**
     * 分页查询[危害因素-套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param HarmPackageMatch查询参数
     * @return 分页数据
     */
    IPage<HarmPackageMatch> getList(PageParam<HarmPackageMatch> page, @Param("param") HarmPackageMatch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    HarmPackageMatch getInfoById(@Param("id") String id);

}
