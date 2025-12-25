package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSalesTargetStatistic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售目标自动统计(MdSalesTargetStatistic)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:21
 */
public interface MdSalesTargetStatisticMapper extends BaseMapper<MdSalesTargetStatistic> {

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param MdSalesTargetStatistic查询参数
     * @return 分页数据
     */
    IPage<MdSalesTargetStatistic> getPage(PageParam<MdSalesTargetStatistic> page, @Param("param") MdSalesTargetStatistic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSalesTargetStatistic getInfoById(@Param("id") String id);

}
