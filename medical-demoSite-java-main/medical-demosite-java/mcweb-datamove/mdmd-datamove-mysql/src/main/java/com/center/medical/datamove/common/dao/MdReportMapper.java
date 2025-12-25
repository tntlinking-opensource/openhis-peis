package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG报告主表(MdReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface MdReportMapper extends BaseMapper<MdReport> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param MdReport查询参数
     * @return 分页数据
     */
    IPage<MdReport> getPage(PageParam<MdReport> page, @Param("param") MdReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReport getInfoById(@Param("id") String id);

}
