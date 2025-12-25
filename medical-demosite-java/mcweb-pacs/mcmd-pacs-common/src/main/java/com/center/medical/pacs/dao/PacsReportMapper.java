package com.center.medical.pacs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-数据(PacsResult)表数据库访问层
 *
 * @author ay
 * @since 2023-05-19 15:22:03
 */
public interface PacsReportMapper extends BaseMapper<PacsResult> {

    /**
     * 分页查询[PACS-数据]列表
     *
     * @param page  分页参数
     * @param param PacsResult查询参数
     * @return 分页数据
     */
    IPage<PacsResult> getList(PageParam<PacsResult> page, @Param("param") PacsResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsResult getInfoById(@Param("id") String id);

}
