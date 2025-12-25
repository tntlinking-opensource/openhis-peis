package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SamplePerson;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ团检报告人员样本表(SamplePerson)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:34
 */
public interface SamplePersonMapper extends BaseMapper<SamplePerson> {

    /**
     * 分页查询[TJ团检报告人员样本表]列表
     *
     * @param page  分页参数
     * @param param SamplePerson查询参数
     * @return 分页数据
     */
    IPage<SamplePerson> getPage(PageParam<SamplePerson> page, @Param("param") SamplePerson param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SamplePerson getInfoById(@Param("id") String id);

}
