package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SalesTargetStatistic;
import com.center.medical.bean.param.SelectMonthListParam;
import com.center.medical.bean.param.SelectSellListParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 销售目标自动统计(SalesTargetStatistic)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
public interface SalesTargetStatisticMapper extends BaseMapper<SalesTargetStatistic> {

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param SalesTargetStatistic查询参数
     * @return 分页数据
     */
    IPage<SalesTargetStatistic> getList(PageParam<SalesTargetStatistic> page, @Param("param") SalesTargetStatistic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SalesTargetStatistic getInfoById(@Param("id") String id);

    /**
     * 销售目标自动统计年度计划
     * @param yearStart
     * @param yearEnd
     * @return
     */
    List<SalesTargetStatistic> selectLeaderList(@Param("yearStart") Date yearStart, @Param("yearEnd") Date yearEnd);

    /**
     * 销售目标自动统计季度计划
     * @param param
     * @return
     */
    List<SalesTargetStatistic> selectSellList(@Param("param") SelectSellListParam param);

    /**
     * 销售目标自动统计月度计划
     * @param param
     * @return
     */
    List<SalesTargetStatistic> selectMonthList(@Param("param") SelectMonthListParam param);
}
