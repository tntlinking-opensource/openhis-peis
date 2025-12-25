package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdHarmPackageMatch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 危害因素-套餐匹配表(MdHarmPackageMatch)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdHarmPackageMatchMapper extends BaseMapper<MdHarmPackageMatch> {

    /**
     * 分页查询[危害因素-套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param MdHarmPackageMatch查询参数
     * @return 分页数据
     */
    IPage<MdHarmPackageMatch> getPage(PageParam<MdHarmPackageMatch> page, @Param("param") MdHarmPackageMatch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHarmPackageMatch getInfoById(@Param("id") String id);

}
