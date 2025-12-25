package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxDetection;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次体检异常结果检出统计(FxDetection)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:48
 */
public interface FxDetectionMapper extends BaseMapper<FxDetection> {

    /**
     * 分页查询[本次体检异常结果检出统计]列表
     *
     * @param page  分页参数
     * @param param FxDetection查询参数
     * @return 分页数据
     */
    IPage<FxDetection> getPage(PageParam<FxDetection> page, @Param("param") FxDetection param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxDetection getInfoById(@Param("id") String id);

}
