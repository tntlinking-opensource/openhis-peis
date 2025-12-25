package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReportUrl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * BG科室报告目录表(MdReportUrl)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
public interface MdReportUrlMapper extends BaseMapper<MdReportUrl> {

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param MdReportUrl查询参数
     * @return 分页数据
     */
    IPage<MdReportUrl> getPage(PageParam<MdReportUrl> page, @Param("param") MdReportUrl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReportUrl getInfoById(@Param("id") String id);

}
