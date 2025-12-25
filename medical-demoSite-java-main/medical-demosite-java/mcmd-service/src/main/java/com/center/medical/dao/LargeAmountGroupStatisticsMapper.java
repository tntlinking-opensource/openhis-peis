package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.LargeAmountGroupStatistics;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 年超20万额度单位统计(LargeAmountGroupStatistics)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
public interface LargeAmountGroupStatisticsMapper extends BaseMapper<LargeAmountGroupStatistics> {

    /**
     * 分页查询[年超20万额度单位统计]列表
     *
     * @param page  分页参数
     * @param param LargeAmountGroupStatistics查询参数
     * @return 分页数据
     */
    IPage<LargeAmountGroupStatistics> getList(PageParam<LargeAmountGroupStatistics> page, @Param("param") LargeAmountGroupStatistics param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    LargeAmountGroupStatistics getInfoById(@Param("id") String id);

}
