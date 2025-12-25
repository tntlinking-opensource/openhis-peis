package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysJobLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务调度日志表(SysJobLog)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

    /**
     * 分页查询[定时任务调度日志表]列表
     *
     * @param page  分页参数
     * @param param SysJobLog查询参数
     * @return 分页数据
     */
    IPage<SysJobLog> getPage(PageParam<SysJobLog> page, @Param("param") SysJobLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键jobLogId
     * @return 详情信息
     */
    SysJobLog getInfoById(@Param("id") Long id);

}
