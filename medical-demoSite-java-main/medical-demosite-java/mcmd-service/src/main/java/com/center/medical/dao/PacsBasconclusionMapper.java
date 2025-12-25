package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsBasconclusion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * pacs总检结论词(PacsBasconclusion)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
public interface PacsBasconclusionMapper extends BaseMapper<PacsBasconclusion> {

    /**
     * 分页查询[pacs总检结论词]列表
     *
     * @param page  分页参数
     * @param param PacsBasconclusion查询参数
     * @return 分页数据
     */
    IPage<PacsBasconclusion> getList(PageParam<PacsBasconclusion> page, @Param("param") PacsBasconclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsBasconclusion getInfoById(@Param("id") String id);

}
