package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ZyVsSummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病处理意见(ZyVsSummary)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:31:03
 */
public interface ZyVsSummaryMapper extends BaseMapper<ZyVsSummary> {

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param ZyVsSummary查询参数
     * @return 分页数据
     */
    IPage<ZyVsSummary> getPage(PageParam<ZyVsSummary> page, @Param("param") ZyVsSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyVsSummary getInfoById(@Param("id") String id);

}
