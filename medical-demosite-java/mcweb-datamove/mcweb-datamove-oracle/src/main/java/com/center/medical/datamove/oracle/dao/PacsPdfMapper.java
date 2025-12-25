package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PacsPdf;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS PDF  海康医院使用(PacsPdf)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:47
 */
public interface PacsPdfMapper extends BaseMapper<PacsPdf> {

    /**
     * 分页查询[PACS PDF  海康医院使用]列表
     *
     * @param page  分页参数
     * @param param PacsPdf查询参数
     * @return 分页数据
     */
    IPage<PacsPdf> getPage(PageParam<PacsPdf> page, @Param("param") PacsPdf param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsPdf getInfoById(@Param("id") String id);

}
