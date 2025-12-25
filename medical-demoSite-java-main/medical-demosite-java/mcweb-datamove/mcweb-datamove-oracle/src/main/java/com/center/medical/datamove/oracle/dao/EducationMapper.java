package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Education;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC教育程度(Education)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:33
 */
public interface EducationMapper extends BaseMapper<Education> {

    /**
     * 分页查询[JC教育程度]列表
     *
     * @param page  分页参数
     * @param param Education查询参数
     * @return 分页数据
     */
    IPage<Education> getPage(PageParam<Education> page, @Param("param") Education param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Education getInfoById(@Param("id") String id);

}
