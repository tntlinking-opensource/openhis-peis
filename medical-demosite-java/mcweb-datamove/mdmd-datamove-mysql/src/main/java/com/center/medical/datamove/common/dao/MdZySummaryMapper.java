package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdZySummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病检查结论(MdZySummary)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZySummaryMapper extends BaseMapper<MdZySummary> {

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param MdZySummary查询参数
     * @return 分页数据
     */
    IPage<MdZySummary> getPage(PageParam<MdZySummary> page, @Param("param") MdZySummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZySummary getInfoById(@Param("id") String id);

}
