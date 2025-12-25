package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysJob;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务调度表(SysJob)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
public interface SysJobMapper extends BaseMapper<SysJob> {

    /**
     * 分页查询[定时任务调度表]列表
     *
     * @param page  分页参数
     * @param param SysJob查询参数
     * @return 分页数据
     */
    IPage<SysJob> getPage(PageParam<SysJob> page, @Param("param") SysJob param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键jobId
     * @return 详情信息
     */
    SysJob getInfoById(@Param("id") Long id);

}
