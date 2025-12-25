package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdZyVsSummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病处理意见(MdZyVsSummary)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZyVsSummaryMapper extends BaseMapper<MdZyVsSummary> {

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param MdZyVsSummary查询参数
     * @return 分页数据
     */
    IPage<MdZyVsSummary> getPage(PageParam<MdZyVsSummary> page, @Param("param") MdZyVsSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyVsSummary getInfoById(@Param("id") String id);

}
