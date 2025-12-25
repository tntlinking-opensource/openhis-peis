package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSortexamLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 每日排检上限(MdSortexamLimit)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:45
 */
public interface MdSortexamLimitMapper extends BaseMapper<MdSortexamLimit> {

    /**
     * 分页查询[每日排检上限]列表
     *
     * @param page  分页参数
     * @param param MdSortexamLimit查询参数
     * @return 分页数据
     */
    IPage<MdSortexamLimit> getPage(PageParam<MdSortexamLimit> page, @Param("param") MdSortexamLimit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSortexamLimit getInfoById(@Param("id") String id);

}
