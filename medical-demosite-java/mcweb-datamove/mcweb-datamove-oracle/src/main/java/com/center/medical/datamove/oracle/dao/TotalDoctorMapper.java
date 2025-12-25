package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.TotalDoctor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (TotalDoctor)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:34
 */
public interface TotalDoctorMapper extends BaseMapper<TotalDoctor> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param TotalDoctor查询参数
     * @return 分页数据
     */
    IPage<TotalDoctor> getPage(PageParam<TotalDoctor> page, @Param("param") TotalDoctor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TotalDoctor getInfoById(@Param("id") String id);

}
