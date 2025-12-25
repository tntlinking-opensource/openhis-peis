package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SortexamLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 预约管理(SortexamLimit)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:02
 */
public interface SortexamLimitMapper extends BaseMapper<SortexamLimit> {

    /**
     * 分页查询[预约管理]列表
     *
     * @param page  分页参数
     * @param param SortexamLimit查询参数
     * @return 分页数据
     */
    IPage<SortexamLimit> getPage(PageParam<SortexamLimit> page, @Param("param") SortexamLimit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SortexamLimit getInfoById(@Param("id") String id);

}
