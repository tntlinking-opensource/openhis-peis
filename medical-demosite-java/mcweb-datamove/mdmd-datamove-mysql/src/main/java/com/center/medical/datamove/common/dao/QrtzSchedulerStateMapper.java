package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzSchedulerState;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 调度器状态表(QrtzSchedulerState)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
public interface QrtzSchedulerStateMapper extends BaseMapper<QrtzSchedulerState> {

    /**
     * 分页查询[调度器状态表]列表
     *
     * @param page  分页参数
     * @param param QrtzSchedulerState查询参数
     * @return 分页数据
     */
    IPage<QrtzSchedulerState> getPage(PageParam<QrtzSchedulerState> page, @Param("param") QrtzSchedulerState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzSchedulerState getInfoById(@Param("id") String id);

}
