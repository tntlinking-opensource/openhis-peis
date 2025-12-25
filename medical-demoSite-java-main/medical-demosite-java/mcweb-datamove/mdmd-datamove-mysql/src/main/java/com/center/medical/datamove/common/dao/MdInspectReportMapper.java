package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdInspectReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 检验报告生成记录(MdInspectReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdInspectReportMapper extends BaseMapper<MdInspectReport> {

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param MdInspectReport查询参数
     * @return 分页数据
     */
    IPage<MdInspectReport> getPage(PageParam<MdInspectReport> page, @Param("param") MdInspectReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdInspectReport getInfoById(@Param("id") String id);

}
