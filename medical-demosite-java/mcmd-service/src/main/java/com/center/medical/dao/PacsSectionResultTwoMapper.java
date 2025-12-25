package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsSectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-科室结果表(PacsSectionResultTwo)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
 */
public interface PacsSectionResultTwoMapper extends BaseMapper<PacsSectionResultTwo> {

    /**
     * 分页查询[PACS-科室结果表]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<PacsSectionResultTwo> getList(PageParam<PacsSectionResultTwo> page, @Param("param") PacsSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsSectionResultTwo getInfoById(@Param("id") String id);

}
