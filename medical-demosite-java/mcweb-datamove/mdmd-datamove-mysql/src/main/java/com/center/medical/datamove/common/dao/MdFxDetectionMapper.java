package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxDetection;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 综合分析-检出统计、团体小结（健康）(MdFxDetection)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxDetectionMapper extends BaseMapper<MdFxDetection> {

    /**
     * 分页查询[综合分析-检出统计、团体小结（健康）]列表
     *
     * @param page  分页参数
     * @param param MdFxDetection查询参数
     * @return 分页数据
     */
    IPage<MdFxDetection> getPage(PageParam<MdFxDetection> page, @Param("param") MdFxDetection param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxDetection getInfoById(@Param("id") String id);

}
