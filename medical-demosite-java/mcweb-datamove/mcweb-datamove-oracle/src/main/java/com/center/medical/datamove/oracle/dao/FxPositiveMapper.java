package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxPositive;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (FxPositive)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:56
 */
public interface FxPositiveMapper extends BaseMapper<FxPositive> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FxPositive查询参数
     * @return 分页数据
     */
    IPage<FxPositive> getPage(PageParam<FxPositive> page, @Param("param") FxPositive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxPositive getInfoById(@Param("id") String id);

}
