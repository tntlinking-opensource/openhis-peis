package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.LisResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * LIS接收结果(LisResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:38
 */
public interface LisResultMapper extends BaseMapper<LisResult> {

    /**
     * 分页查询[LIS接收结果]列表
     *
     * @param page  分页参数
     * @param param LisResult查询参数
     * @return 分页数据
     */
    IPage<LisResult> getPage(PageParam<LisResult> page, @Param("param") LisResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LisResult getInfoById(@Param("id") String id);

}
