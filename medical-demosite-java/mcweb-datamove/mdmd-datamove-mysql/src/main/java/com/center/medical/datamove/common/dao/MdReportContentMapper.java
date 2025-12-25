package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReportContent;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 生成报告内容(MdReportContent)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
public interface MdReportContentMapper extends BaseMapper<MdReportContent> {

    /**
     * 分页查询[生成报告内容]列表
     *
     * @param page  分页参数
     * @param param MdReportContent查询参数
     * @return 分页数据
     */
    IPage<MdReportContent> getPage(PageParam<MdReportContent> page, @Param("param") MdReportContent param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReportContent getInfoById(@Param("id") String id);

}
