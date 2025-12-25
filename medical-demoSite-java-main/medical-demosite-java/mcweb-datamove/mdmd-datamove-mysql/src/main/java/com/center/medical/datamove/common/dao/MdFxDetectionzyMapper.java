package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxDetectionzy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ综合分析-检出人数（职业）(MdFxDetectionzy)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxDetectionzyMapper extends BaseMapper<MdFxDetectionzy> {

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param MdFxDetectionzy查询参数
     * @return 分页数据
     */
    IPage<MdFxDetectionzy> getPage(PageParam<MdFxDetectionzy> page, @Param("param") MdFxDetectionzy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxDetectionzy getInfoById(@Param("id") String id);

}
