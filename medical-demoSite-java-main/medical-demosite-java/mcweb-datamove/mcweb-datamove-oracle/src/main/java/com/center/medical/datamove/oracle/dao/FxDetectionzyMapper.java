package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxDetectionzy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ综合分析-检出人数（职业）(FxDetectionzy)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:49
 */
public interface FxDetectionzyMapper extends BaseMapper<FxDetectionzy> {

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param FxDetectionzy查询参数
     * @return 分页数据
     */
    IPage<FxDetectionzy> getPage(PageParam<FxDetectionzy> page, @Param("param") FxDetectionzy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxDetectionzy getInfoById(@Param("id") String id);

}
