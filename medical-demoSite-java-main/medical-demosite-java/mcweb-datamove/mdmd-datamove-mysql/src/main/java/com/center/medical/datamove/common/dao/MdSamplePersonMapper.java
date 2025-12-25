package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSamplePerson;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ团检报告人员样本表(MdSamplePerson)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:26
 */
public interface MdSamplePersonMapper extends BaseMapper<MdSamplePerson> {

    /**
     * 分页查询[TJ团检报告人员样本表]列表
     *
     * @param page  分页参数
     * @param param MdSamplePerson查询参数
     * @return 分页数据
     */
    IPage<MdSamplePerson> getPage(PageParam<MdSamplePerson> page, @Param("param") MdSamplePerson param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSamplePerson getInfoById(@Param("id") String id);

}
