package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.LargeAmountGroupStatistics;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 年超20万额度单位统计(LargeAmountGroupStatistics)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:33
 */
public interface LargeAmountGroupStatisticsMapper extends BaseMapper<LargeAmountGroupStatistics> {

    /**
     * 分页查询[年超20万额度单位统计]列表
     *
     * @param page  分页参数
     * @param param LargeAmountGroupStatistics查询参数
     * @return 分页数据
     */
    IPage<LargeAmountGroupStatistics> getPage(PageParam<LargeAmountGroupStatistics> page, @Param("param") LargeAmountGroupStatistics param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LargeAmountGroupStatistics getInfoById(@Param("id") String id);

}
