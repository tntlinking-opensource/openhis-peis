package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.HarmPackageMatch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 接害因素、套餐匹配表(HarmPackageMatch)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:13
 */
public interface HarmPackageMatchMapper extends BaseMapper<HarmPackageMatch> {

    /**
     * 分页查询[接害因素、套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param HarmPackageMatch查询参数
     * @return 分页数据
     */
    IPage<HarmPackageMatch> getPage(PageParam<HarmPackageMatch> page, @Param("param") HarmPackageMatch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HarmPackageMatch getInfoById(@Param("id") String id);

}
