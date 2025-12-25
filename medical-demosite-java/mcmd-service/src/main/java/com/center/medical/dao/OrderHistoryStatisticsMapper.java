package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.OrderHistoryStatistics;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单历史平均折扣、变动成本率统计表(OrderHistoryStatistics)数据库访问层
 *
 * @author ay
 * @since 2023-12-07 14:05:37
 */
public interface OrderHistoryStatisticsMapper extends BaseMapper<OrderHistoryStatistics> {

    /**
     * 分页查询[订单历史平均折扣、变动成本率统计表]列表
     *
     * @param page  分页参数
     * @param param OrderHistoryStatistics查询参数
     * @return 分页数据
     */
    IPage<OrderHistoryStatistics> getPage(PageParam<OrderHistoryStatistics> page, @Param("param") OrderHistoryStatistics param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderHistoryStatistics getInfoById(@Param("id") String id);

    /**
     * 获取插入订单历史折扣成本率等
     * @param param
     * @return
     */
    List<OrderHistoryStatistics> insertOrderHistoryStatistics(@Param("param") BaseParam param);

    /**
     * 老系统sql，如果当时候还没替换就要去老系统导出excel，然后再导入新系统
     * @param param
     * @return
     */
    List<OrderHistoryStatistics> getOldSql(@Param("param") BaseParam param);
}
