package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Education;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 教育程度(Education)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:35
 */
public interface EducationMapper extends BaseMapper<Education> {

    /**
     * 分页查询[教育程度]列表
     *
     * @param page  分页参数
     * @param param Education查询参数
     * @return 分页数据
     */
    IPage<Education> getList(PageParam<Education> page, @Param("param") Education param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Education getInfoById(@Param("id") String id);

}
