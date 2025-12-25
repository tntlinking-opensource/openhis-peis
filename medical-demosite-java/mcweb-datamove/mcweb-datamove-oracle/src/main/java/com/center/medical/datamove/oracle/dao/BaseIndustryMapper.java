package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseIndustry;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:35
 */
public interface BaseIndustryMapper extends BaseMapper<BaseIndustry> {

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param BaseIndustry查询参数
     * @return 分页数据
     */
    IPage<BaseIndustry> getPage(PageParam<BaseIndustry> page, @Param("param") BaseIndustry param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseIndustry getInfoById(@Param("id") String id);

}
