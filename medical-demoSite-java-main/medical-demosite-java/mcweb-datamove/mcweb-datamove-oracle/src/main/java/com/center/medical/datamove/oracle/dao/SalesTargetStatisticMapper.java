package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SalesTargetStatistic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售目标自动统计(SalesTargetStatistic)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:30
 */
public interface SalesTargetStatisticMapper extends BaseMapper<SalesTargetStatistic> {

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param SalesTargetStatistic查询参数
     * @return 分页数据
     */
    IPage<SalesTargetStatistic> getPage(PageParam<SalesTargetStatistic> page, @Param("param") SalesTargetStatistic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SalesTargetStatistic getInfoById(@Param("id") String id);

}
