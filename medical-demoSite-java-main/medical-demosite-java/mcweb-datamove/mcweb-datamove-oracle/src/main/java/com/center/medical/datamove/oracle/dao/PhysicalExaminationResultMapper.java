package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PhysicalExaminationResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检结果表（处理后）(PhysicalExaminationResult)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:34
 */
public interface PhysicalExaminationResultMapper extends BaseMapper<PhysicalExaminationResult> {

    /**
     * 分页查询[体检结果表（处理后）]列表
     *
     * @param page  分页参数
     * @param param PhysicalExaminationResult查询参数
     * @return 分页数据
     */
    IPage<PhysicalExaminationResult> getPage(PageParam<PhysicalExaminationResult> page, @Param("param") PhysicalExaminationResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PhysicalExaminationResult getInfoById(@Param("id") String id);

}
