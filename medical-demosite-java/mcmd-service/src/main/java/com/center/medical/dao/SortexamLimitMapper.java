package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.SortexamLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 每日排检上限(SortexamLimit)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:19
 */
public interface SortexamLimitMapper extends BaseMapper<SortexamLimit> {

    /**
     * 分页查询[每日排检上限]列表
     *
     * @param page  分页参数
     * @param param SortexamLimit查询参数
     * @return 分页数据
     */
    IPage<SortexamLimit> getList(PageParam<SortexamLimit> page, @Param("param") SortexamLimit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SortexamLimit getInfoById(@Param("id") String id);

}
